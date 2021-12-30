package com.atinbo.security.service;


import com.atinbo.cache.redis.RedisCacheOps;
import com.atinbo.core.spring.SpringContextHolder;
import com.atinbo.core.utils.MessageSourceUtil;
import com.atinbo.core.utils.StringUtil;
import com.atinbo.security.consts.SecurityConstants;
import com.atinbo.security.model.LoginEvent;
import com.atinbo.security.model.LoginUser;
import com.atinbo.webmvc.exception.CaptchaException;
import com.atinbo.webmvc.exception.CaptchaExpireException;
import com.atinbo.webmvc.exception.UserException;
import com.atinbo.webmvc.exception.UserPasswordNotMatchException;
import com.lujia.exception.ParamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * 登录校验方法
 *
 * @author ruoyi
 */
@Component
public class LoginService {
    @Autowired
    private UserTokenService userTokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCacheOps redisOpsCache;

    @Value("${login.failed.count:3}")
    private Integer loginFailedCount;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param captcha  验证码
     * @return 结果
     */
    public String login(String username, String password, String captcha) {
        if (StringUtil.isBlank(username) || StringUtil.isBlank(password)) {
            throw new ParamException(MessageSourceUtil.message("user.nonempty", null));
        }

        if (validFailedCount(username) > loginFailedCount) {
            if (StringUtil.isBlank(captcha)) {
                throw new UserException("user.login.failed", new Object[]{loginFailedCount});
            }

            String verifyKey = SecurityConstants.CAPTCHA_CODE_KEY + username;
            String code = redisOpsCache.getCacheObject(verifyKey);
            redisOpsCache.deleteObject(verifyKey);
            if (code == null) {
                SpringContextHolder.publishEvent(new LoginEvent(new LoginEvent.LoginLog(username, SecurityConstants.LOGIN_FAIL, MessageSourceUtil.message("user.jcaptcha.error"))));
                throw new CaptchaExpireException();
            }
            if (!code.equalsIgnoreCase(captcha)) {
                SpringContextHolder.publishEvent(new LoginEvent(new LoginEvent.LoginLog(username, SecurityConstants.LOGIN_FAIL, MessageSourceUtil.message("user.jcaptcha.expire"))));
                throw new CaptchaException();
            }
        }

        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (UsernameNotFoundException ex) {
            throw ex;
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                addFailedCount(username);

                SpringContextHolder.publishEvent(new LoginEvent(new LoginEvent.LoginLog(username, SecurityConstants.LOGIN_FAIL, MessageSourceUtil.message("user.password.not.match"))));
                throw new UserPasswordNotMatchException();
            } else {
                addFailedCount(username);

                SpringContextHolder.publishEvent(new LoginEvent(new LoginEvent.LoginLog(username, SecurityConstants.LOGIN_FAIL, e.getMessage())));
                throw new RuntimeException(e.getMessage());
            }
        }
        SpringContextHolder.publishEvent(new LoginEvent(new LoginEvent.LoginLog(username, SecurityConstants.LOGIN_SUCCESS, MessageSourceUtil.message("user.login.success"))));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        return userTokenService.createToken(loginUser);
    }

    private void addFailedCount(String username) {
        redisOpsCache.setCacheObject(SecurityConstants.LOGIN_FAILED_KEY + username, Integer.valueOf(1));
    }

    private Integer validFailedCount(String username) {
        Integer count = redisOpsCache.getCacheObject(SecurityConstants.LOGIN_FAILED_KEY + username);
        return (count == null) ? Integer.valueOf(0) : count;
    }
}

//package com.love.wife.security;
//import com.atinbo.security.model.LoginUser;
//import com.atinbo.security.service.UserTokenService;
//import com.atinbo.webmvc.utils.WebUtil;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
//import java.util.Objects;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//
///**
// * 自定义权限实现，se取自SpringSecurity首字母
// *
// * @author breggor
// */
//
//@Service("perm")
//public class PermissionService {
//
//    /**
//     * 所有权限标识
//     */
//
//    private static final String ALL_PERMISSION = "*:*:*";
//
//
//    /**
//     * 管理员角色权限标识
//     */
//
//    private static final String SUPER_ADMIN = "admin";
//
//    private static final String ROLE_DELIMETER = ",";
//
//    private static final String PERMISSION_DELIMETER = ",";
//
//    @Autowired
//    private UserTokenService userTokenService;
//
//
//
//    /**
//     * 验证用户是否具备某权限
//     *
//     * @param permission 权限字符串
//     * @return 用户是否具备某权限
//     */
//
//    public boolean hasPerm(String permission) {
//        if (StringUtils.isEmpty(permission)) {
//            return false;
//        }
//        LoginUser loginUser = userTokenService.getLoginUser(WebUtil.getRequest());
//        if (Objects.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getAuthorities())) {
//            return false;
//        }
//        return hasPermissions(loginUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()), permission);
//    }
//
//
//    /**
//     * 验证用户是否不具备某权限，与 hasPermi逻辑相反
//     *
//     * @param permission 权限字符串
//     * @return 用户是否不具备某权限
//     */
//
//    public boolean lacksPerm(String permission) {
//        return hasPerm(permission) != true;
//    }
//
//
//    /**
//     * 验证用户是否具有以下任意一个权限
//     *
//     * @param permissions 以 PERMISSION_NAMES_DELIMETER 为分隔符的权限列表
//     * @return 用户是否具有以下任意一个权限
//     */
//
//    public boolean hasAnyPerm(String permissions) {
//        if (StringUtils.isEmpty(permissions)) {
//            return false;
//        }
//        LoginUser loginUser = userTokenService.getLoginUser(WebUtil.getRequest());
//        if (Objects.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getAuthorities())) {
//            return false;
//        }
//        Set<String> authorities = loginUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
//        for (String permission : permissions.split(PERMISSION_DELIMETER)) {
//            if (permission != null && hasPermissions(authorities, permission)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//    /**
//     * 判断是否包含权限
//     *
//     * @param permissions 权限列表
//     * @param permission  权限字符串
//     * @return 用户是否具备某权限
//     */
//
//    private boolean hasPermissions(Set<String> permissions, String permission) {
//        return permissions.contains(ALL_PERMISSION) || permissions.contains(StringUtils.trim(permission));
//    }
//}
//

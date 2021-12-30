package com.love.wife.web;

import com.love.wife.entity.User;
import com.lujia.model.Outcome;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.love.wife.entity.Wife;
import com.love.wife.service.LoveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/8/8 0:27
 */
@Api(tags = "爱的管理")
@ApiSort(value = 2)
@RequestMapping(value = "/love-manager")
@RestController
public class LoveController {
    @Resource
    private LoveService loveService;

    @ApiOperation(value = "查询对象资料")
    @ApiOperationSupport(order = 10)
    @GetMapping
    public Outcome<List<Wife>> findLoveWifeInfo() {
        return loveService.findLoveWifeInfo();
    }

    @ApiOperation(value = "查询用户资料2")
    @ApiOperationSupport(order = 20)
    @GetMapping(value = "find-all")
    public Outcome<List<User>> findAllWifeInfos() {
        return loveService.findUserInfo();
    }

    @ApiOperation(value = "查询对象资料3")
    @ApiOperationSupport(order = 30)
    @PostMapping
    public Outcome<List<Wife>> addAllWifeInfo() {
        return loveService.findLoveWifeInfo();
    }

    @ApiOperation(value = "查询对象资料4")
    @ApiOperationSupport(order = 40)
    @PutMapping
    public Outcome<List<Wife>> updateLoveWifeInfo() {
        return loveService.findLoveWifeInfo();
    }
}

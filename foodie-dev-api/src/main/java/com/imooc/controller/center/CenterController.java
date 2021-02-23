package com.imooc.controller.center;

import com.imooc.pojo.Users;
import com.imooc.service.CenterUserService;
import com.imooc.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gengbin
 * @date 2021/2/12
 */
@Api(value = "用户中心", tags = {"用户中心展示的相关接口"})
@RestController
@RequestMapping("center")
public class CenterController {
    @Autowired
    private CenterUserService centerUserService;

    @ApiParam(name = "userId", value = "用户id", required = true)
    @GetMapping("getUserInfo")
    public CommonResult getUserInfo(@RequestParam String userId) {
        Users user = centerUserService.getUserInfo(userId);
        return CommonResult.ok(user);
    }
}

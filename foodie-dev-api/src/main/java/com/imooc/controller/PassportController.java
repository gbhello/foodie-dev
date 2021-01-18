package com.imooc.controller;

import com.imooc.consts.BusinessConsts;
import com.imooc.consts.ValidationErrorCode;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.UserBO;
import com.imooc.service.UserService;
import com.imooc.utils.CommonResult;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gengbin
 * @date 2021/1/14
 */
@Api(value = "注册登录", tags = {"注册登录的相关接口"})
@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public CommonResult usernameIsExist(@RequestParam String username) {
        // 1.校验用户名不能为空
        if (StringUtils.isEmpty(username)) {
            return CommonResult.errorMsg(ValidationErrorCode.PARAM_EMPTY);
        }
        // 2.查找注册的用户名是否存在
        boolean isExist = userService.findUsernameIsExist(username);
        if (isExist) {
            return CommonResult.errorMsg(ValidationErrorCode.INSTANCE_EXIST);
        }
        // 3.请求成功，用户名没有重复
        return CommonResult.ok();
    }

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public CommonResult regist(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response) {
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();

        // 1.判断用户名和密码必须不为空
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(confirmPassword)) {
            return CommonResult.errorMsg(ValidationErrorCode.PARAM_EMPTY);
        }
        // 2.查询用户名是否存在
        boolean usernameIsExist = userService.findUsernameIsExist(username);
        if (usernameIsExist) {
            return CommonResult.errorMsg(ValidationErrorCode.INSTANCE_EXIST);
        }

        // 3.密码长度不能少于6位
        if (password.length() < BusinessConsts.PASSWORD_LENGTH) {
            return CommonResult.errorMsg(ValidationErrorCode.PASSWORD_LENGTH_ILLEGAL);
        }
        // 4.判断两次密码是否一致
        if (!password.equals(confirmPassword)) {
            return CommonResult.errorMsg(ValidationErrorCode.PASSWORD_DIFFERENT);
        }

        // 5.实现注册
        Users userResult = userService.createUser(userBO);
        userResult = this.setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);

        // TODO 生成用户token，存入redis会话
        // TODO 同步购物车数据
        return CommonResult.ok();
    }

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public CommonResult login(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = userBO.getUsername();
        String password = userBO.getPassword();

        // 1.校验用户名和密码不能为空
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return CommonResult.errorMsg(ValidationErrorCode.PARAM_EMPTY);
        }
        // 2.实现登录
        Users userResult = userService.getUserForLogin(username, password);
        if (userResult == null) {
            return CommonResult.errorMsg(ValidationErrorCode.USERNAME_PASSWORD_DISMATCH);
        }
        userResult = this.setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);

        // TODO 生成用户token，存入redis会话
        // TODO 同步购物车数据

        return CommonResult.ok(userResult);
    }

    @ApiOperation(value = "退出登录", notes = "退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public CommonResult logout(@RequestParam String userId, HttpServletRequest request, HttpServletResponse response) {
        // 清楚用户的相关信息的cookie
        CookieUtils.deleteCookie(request, response, "user");

        // TODO 用户退出登录，需要清空购物车
        // TODO 分布式会话中需要清楚用户数据
        return CommonResult.ok();
    }

    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }
}

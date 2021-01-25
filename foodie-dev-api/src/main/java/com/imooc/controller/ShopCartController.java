package com.imooc.controller;

import com.imooc.consts.ValidationErrorCode;
import com.imooc.pojo.bo.ShopCartBO;
import com.imooc.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gengbin
 * @date 2021/1/25
 */
@Api(value = "购物车接口", tags = "购物车接口相关api")
@RequestMapping("shopcart")
@RestController
public class ShopCartController {
    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("/addShopCart")
    public CommonResult addShopCart(@RequestParam String userId,
                                    @RequestBody ShopCartBO shopCartBO,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        Assert.hasText(userId, ValidationErrorCode.PARAM_EMPTY);
        //TODO 前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车到redis缓存

        return CommonResult.ok();
    }

    @ApiOperation(value = "从购物车中删除商品", notes = "从购物车中删除商品", httpMethod = "POST")
    @PostMapping("/deleteShopCart")
    public CommonResult deleteShopCart(@RequestParam String userId,
                                       @RequestParam String itemSpecId,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {
        Assert.hasText(userId, ValidationErrorCode.PARAM_EMPTY);
        Assert.hasText(itemSpecId, ValidationErrorCode.PARAM_EMPTY);

        //TODO 用户在页面删除购物车中的商品数据，如果此时用户已经登录，则需要同步删除购物车中的数据
        return CommonResult.ok();
    }
}

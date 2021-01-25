package com.imooc.controller;

import com.imooc.consts.ValidationErrorCode;
import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;
import com.imooc.service.AddressService;
import com.imooc.utils.CommonResult;
import com.imooc.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author gengbin
 * @date 2021/1/25
 */
@Api(value = "地址相关", tags = {"地址相关的api接口"})
@RequestMapping("address")
@RestController
public class AddressController {
    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "根据用户id查询收货地址列表", notes = "根据用户id查询收货地址列表", httpMethod = "GET")
    @GetMapping("/getAddressList")
    public CommonResult getAddressList(@RequestParam String userId) {
        List<UserAddress> addressList = addressService.getAddressList(userId);
        return CommonResult.ok(addressList);
    }

    @ApiOperation(value = "用户新增地址", notes = "用户新增地址", httpMethod = "POST")
    @PostMapping("/addAddress")
    public CommonResult addAddress(@RequestBody AddressBO addressBO) {
        CommonResult checkResult = checkAddress(addressBO);
        if (checkResult.getStatus() != 200) {
            return checkResult;
        }
        addressService.addUserAddress(addressBO);
        return CommonResult.ok();
    }

    private CommonResult checkAddress(AddressBO addressBO) {
        String receiver = addressBO.getReceiver();
        Assert.hasText(receiver, ValidationErrorCode.Address.RECEIVER_EMPTY);
        Assert.isTrue(receiver.length() > 12, ValidationErrorCode.Address.RECEIVER_TOO_LONG);

        String mobile = addressBO.getMobile();
        Assert.hasText(mobile, ValidationErrorCode.Mobile.MOBILE_EMPTY);
        Assert.isTrue(MobileEmailUtils.checkMobileIsOk(mobile), ValidationErrorCode.Mobile.MOBILE_ILLEGAL);

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();

        Assert.isTrue(StringUtils.isEmpty(province) || StringUtils.isEmpty(city) || StringUtils.isEmpty(district) || StringUtils.isEmpty(detail), ValidationErrorCode.Address.ADDRESS_EMPTY);
        return CommonResult.ok();
    }
}

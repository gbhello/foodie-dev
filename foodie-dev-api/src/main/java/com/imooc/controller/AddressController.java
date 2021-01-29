package com.imooc.controller;

import com.imooc.consts.BusinessConsts;
import com.imooc.consts.ValidationErrorCode;
import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;
import com.imooc.service.AddressService;
import com.imooc.utils.CommonResult;
import com.imooc.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @ApiOperation(value = "修改地址", notes = "修改地址", httpMethod = "POST")
    @PostMapping("/updateAddress")
    public CommonResult updateAddress(@RequestBody AddressBO addressBO) {
        if (addressBO == null || org.apache.commons.lang3.StringUtils.isBlank(addressBO.getAddressId())) {
            return CommonResult.errorMsg(ValidationErrorCode.PARAM_EMPTY);
        }
        CommonResult checkResult = checkAddress(addressBO);
        if (checkResult.getStatus() != 200) {
            return checkResult;
        }
        addressService.updateUserAddress(addressBO);
        return CommonResult.ok();
    }

    @ApiOperation(value = "删除地址", notes = "删除地址", httpMethod = "POST")
    @PostMapping("/deleteAddress")
    public CommonResult deleteAddress(@RequestParam String addressId) {
        if (StringUtils.isEmpty(addressId)) {
            return CommonResult.errorMsg(ValidationErrorCode.PARAM_EMPTY);
        }
        addressService.deleteAddress(addressId);
        return CommonResult.ok();
    }

    private CommonResult checkAddress(AddressBO addressBO) {
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return CommonResult.errorMsg(ValidationErrorCode.Address.RECEIVER_EMPTY);
        }
        if (receiver.length() > BusinessConsts.MAX_RECEIVER_LENGTH) {
            return CommonResult.errorMsg(ValidationErrorCode.Address.RECEIVER_TOO_LONG);
        }

        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return CommonResult.errorMsg(ValidationErrorCode.Mobile.MOBILE_EMPTY);
        }
        if (!MobileEmailUtils.checkMobileIsOk(mobile)) {
            return CommonResult.errorMsg(ValidationErrorCode.Mobile.MOBILE_ILLEGAL);
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();

        if (StringUtils.isEmpty(province) || StringUtils.isEmpty(city) || StringUtils.isEmpty(district) || StringUtils.isEmpty(detail)) {
            return CommonResult.errorMsg(ValidationErrorCode.Address.ADDRESS_EMPTY);
        }
        return CommonResult.ok();
    }
}

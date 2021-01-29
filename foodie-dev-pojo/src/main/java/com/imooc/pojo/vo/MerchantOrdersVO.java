package com.imooc.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author gengbin
 * @date 2021/1/27
 */
@Data
@ApiModel(value = "商户订单VO")
public class MerchantOrdersVO {
    @ApiModelProperty(value = "商户订单id")
    private String merchantOrderId;
    @ApiModelProperty(value = "商户id")
    private String merchantUserId;
    @ApiModelProperty(value = "实际支付总金额（包含商户所支付的订单非邮费总额）")
    private Integer amount;
    @ApiModelProperty(value = "支付方式 1：微信 2：支付宝")
    private Integer payMethod;
    @ApiModelProperty(value = "支付成功后的回调地址")
    private String returnUrl;
}

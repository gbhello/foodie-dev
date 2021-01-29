package com.imooc.controller;

import com.imooc.consts.BusinessConsts;
import com.imooc.consts.ValidationErrorCode;
import com.imooc.enums.PayMethod;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.pojo.vo.OrderVO;
import com.imooc.service.OrderService;
import com.imooc.utils.CommonResult;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gengbin
 * @date 2021/1/27
 */
@Api(value = "订单相关", tags = {"订单相关接口"})
@RequestMapping("orders")
@RestController
public class OrdersController {
    final static Logger logger = LoggerFactory.getLogger(OrdersController.class);

    @Autowired
    private OrderService orderService;

    public CommonResult createOrder(@RequestBody SubmitOrderBO submitOrderBO, HttpServletRequest request, HttpServletResponse response) {
        if (submitOrderBO == null) {
            return CommonResult.errorMsg(ValidationErrorCode.PARAM_EMPTY);
        }
        if (submitOrderBO.getPayMethod().equals(PayMethod.WEIXIN.type) && submitOrderBO.getPayMethod().equals(PayMethod.ALIPAY.type)) {
            return CommonResult.errorMsg(BusinessConsts.Order.UNSUPPORT_PAY_TYPE);
        }
        OrderVO orderVO = orderService.createOrder(submitOrderBO);
        return CommonResult.ok();
    }
}

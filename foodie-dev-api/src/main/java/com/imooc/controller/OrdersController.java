package com.imooc.controller;

import com.imooc.consts.BusinessConsts;
import com.imooc.consts.StatusCodeConsts;
import com.imooc.consts.ValidationErrorCode;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayMethod;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.pojo.vo.MerchantOrdersVO;
import com.imooc.pojo.vo.OrderVO;
import com.imooc.service.OrderService;
import com.imooc.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation(value = "创建订单", notes = "创建订单", httpMethod = "POST")
    @PostMapping("/createOrder")
    public CommonResult createOrder(@RequestBody SubmitOrderBO submitOrderBO, HttpServletRequest request, HttpServletResponse response) {
        if (submitOrderBO == null) {
            return CommonResult.errorMsg(ValidationErrorCode.PARAM_EMPTY);
        }
        if (!submitOrderBO.getPayMethod().equals(PayMethod.WEIXIN.type) && !submitOrderBO.getPayMethod().equals(PayMethod.ALIPAY.type)) {
            return CommonResult.errorMsg(BusinessConsts.Order.UNSUPPORT_PAY_TYPE);
        }
        //1.创建订单
        OrderVO orderVO = orderService.createOrder(submitOrderBO);
        String orderId = orderVO.getOrderId();
        //2.创建订单后，移除购物车中已结算（已提交）的商品
        //TODO 整合redis后，完善购物车中的已结算商品清除，并且同步到前端的cookie中
        //3.向支付中心发送当前订单，用于保存支付中心的订单数据
        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(BusinessConsts.PAY_RETURN_URL);

        //为了方便测试购买，所以所有的支付金额都统一修改为1分钱
        merchantOrdersVO.setAmount(1);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("imoocUserId", "imooc");
        headers.add("password", "imooc");

        HttpEntity<MerchantOrdersVO> entity = new HttpEntity<>(merchantOrdersVO, headers);
        ResponseEntity<CommonResult> responseEntity = restTemplate.postForEntity(BusinessConsts.PAYMENT_URL, entity, CommonResult.class);
        CommonResult paymentResult = responseEntity.getBody();
        if (paymentResult.getStatus() != StatusCodeConsts.SUCCESS) {
            logger.error("发送错误：{}", paymentResult.getMsg());
            return CommonResult.errorMsg("支付中心订单创建失败，请联系管理员！");
        }

        return CommonResult.ok(orderId);
    }

    @PostMapping("notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(@RequestParam String merchantOrderId) {
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }

    @PostMapping("getPaidOrderInfo")
    public CommonResult getPaidOrderInfo(String orderId) {
        OrderStatus orderStatus = orderService.getOrderStatusInfo(orderId);
        return CommonResult.ok(orderStatus);
    }
}

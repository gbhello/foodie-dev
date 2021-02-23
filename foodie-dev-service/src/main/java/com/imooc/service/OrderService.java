package com.imooc.service;

import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.Orders;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.pojo.vo.OrderVO;

/**
 * @author gengbin
 * @date 2021/1/27
 */
public interface OrderService {
    /**
     * 创建订单
     *
     * @param submitOrderBO
     * @return
     */
    OrderVO createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 更新订单状态
     *
     * @param merchantOrderId
     * @param type
     */
    void updateOrderStatus(String merchantOrderId, Integer type);

    /**
     * 获取订单信息
     *
     * @param orderId
     * @return
     */
    OrderStatus getOrderStatusInfo(String orderId);

    /**
     * 关闭超时订单
     */
    void closeOrder();

    /**
     * 获取我的订单信息
     *
     * @param userId
     * @param orderId
     * @return
     */
    Orders getMyOrder(String userId, String orderId);
}

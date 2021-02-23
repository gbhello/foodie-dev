package com.imooc.service;

import com.imooc.pojo.vo.OrderStatusCountsVO;
import com.imooc.utils.PagedGridResult;

/**
 * @author gengbin
 * @date 2021/2/14
 */
public interface MyOrdersService {
    /**
     * 获取订单状态数量
     *
     * @param userId
     * @return
     */
    OrderStatusCountsVO getOrderStatusCounts(String userId);

    /**
     * 查询我的订单
     *
     * @param userId
     * @param orderStatus
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize);

    /**
     * 更新订单状态为已发货
     *
     * @param orderId
     */
    void updateDeliverOrderStatus(String orderId);

    /**
     * 更新订单状态为已收货
     *
     * @param orderId
     * @return
     */
    boolean updateReceiveOrderStatus(String orderId);

    /**
     * 删除订单
     *
     * @param userId
     * @param orderId
     * @return
     */
    boolean deleteOrder(String userId, String orderId);

    /**
     * 获取分页的订单动向
     *
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult getOrdersTrend(String userId, Integer page, Integer pageSize);
}

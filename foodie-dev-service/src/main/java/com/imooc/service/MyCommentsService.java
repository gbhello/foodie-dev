package com.imooc.service;

import com.imooc.pojo.OrderItems;
import com.imooc.pojo.bo.center.OrderItemsCommentBO;
import com.imooc.utils.PagedGridResult;

import java.util.List;

/**
 * @author gengbin
 * @date 2021/2/13
 */
public interface MyCommentsService {
    /**
     * 获取订单信息
     *
     * @param orderId
     * @return
     */
    List<OrderItems> getOrderItems(String orderId);

    /**
     * 保存评论
     *
     * @param orderId
     * @param userId
     * @param commentBOList
     */
    void saveCommentList(String orderId, String userId, List<OrderItemsCommentBO> commentBOList);

    /**
     * 获取我的评论
     *
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult getMyComments(String userId, Integer page, Integer pageSize);
}

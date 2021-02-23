package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.consts.YesOrNo;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.mapper.OrderStatusMapper;
import com.imooc.mapper.OrdersMapper;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.Orders;
import com.imooc.pojo.vo.MyOrdersVO;
import com.imooc.pojo.vo.OrderStatusCountsVO;
import com.imooc.service.MyOrdersService;
import com.imooc.utils.PagedGridResult;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gengbin
 * @date 2021/2/14
 */
@Service
public class MyOrdersServiceImpl implements MyOrdersService {
    @Reference
    private OrdersMapper ordersMapper;
    @Reference
    private OrderStatusMapper orderStatusMapper;

    @Override
    public OrderStatusCountsVO getOrderStatusCounts(String userId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        map.put("orderStatus", OrderStatusEnum.WAIT_PAY.type);
        int waitPayCounts = ordersMapper.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);
        int waitDeliverCounts = ordersMapper.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);
        int waitReceiveCounts = ordersMapper.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.SUCCESS.type);
        map.put("isComment", YesOrNo.NO.type);
        int waitCommentCounts = ordersMapper.getMyOrderStatusCounts(map);

        OrderStatusCountsVO countsVO = new OrderStatusCountsVO(waitPayCounts, waitDeliverCounts, waitReceiveCounts, waitCommentCounts);
        return countsVO;
    }

    @Override
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("orderStatus", orderStatus);

        PageHelper.startPage(page, pageSize);
        List<MyOrdersVO> ordersVOList = ordersMapper.getMyOrder(paramMap);
        return this.setterPagedGrid(ordersVOList, page);
    }

    @Override
    public void updateDeliverOrderStatus(String orderId) {
        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.type);
        updateOrder.setDeliverTime(new Date());

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);

        orderStatusMapper.updateByExampleSelective(updateOrder, example);
    }

    @Override
    public boolean updateReceiveOrderStatus(String orderId) {
        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderStatus(OrderStatusEnum.SUCCESS.type);
        updateOrder.setSuccessTime(new Date());

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);

        int result = orderStatusMapper.updateByExampleSelective(updateOrder, example);

        return result == 1;
    }

    @Override
    public boolean deleteOrder(String userId, String orderId) {
        Orders updateOrder = new Orders();
        updateOrder.setIsDelete(YesOrNo.YES.type);
        updateOrder.setUpdatedTime(new Date());

        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", orderId);
        criteria.andEqualTo("userId", userId);

        int result = ordersMapper.updateByExampleSelective(updateOrder, example);

        return result == 1;
    }

    /**
     * 获取分页的订单动向
     *
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PagedGridResult getOrdersTrend(String userId, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        PageHelper.startPage(page, pageSize);
        List<OrderStatus> list = ordersMapper.getMyOrderTrend(map);

        return setterPagedGrid(list, page);
    }

    private PagedGridResult setterPagedGrid(List<?> ordersVOList, Integer page) {
        PageInfo<?> pageList = new PageInfo<>(ordersVOList);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(ordersVOList);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }
}

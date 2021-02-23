package com.imooc.mapper;

import com.imooc.mapper.base.MyMapper;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.Orders;
import com.imooc.pojo.vo.MyOrdersVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gengbin
 * @date 2021/1/12
 */
@Mapper
public interface OrdersMapper extends MyMapper<Orders> {
    /**
     * 获取待支付订单数量
     *
     * @param map
     * @return
     */
    int getMyOrderStatusCounts(@Param("map") HashMap<String, Object> map);

    /**
     * 获取我的订单
     *
     * @param paramMap
     * @return
     */
    List<MyOrdersVO> getMyOrder(HashMap<String, Object> paramMap);

    /**
     * 获取订单动向
     *
     * @param map
     * @return
     */
    List<OrderStatus> getMyOrderTrend(Map<String, Object> map);
}
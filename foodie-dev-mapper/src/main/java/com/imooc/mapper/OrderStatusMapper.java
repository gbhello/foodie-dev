package com.imooc.mapper;

import com.imooc.mapper.base.MyMapper;
import com.imooc.pojo.OrderStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * @author gengbin
 * @date 2021/1/12
 */
@Mapper
public interface OrderStatusMapper extends MyMapper<OrderStatus> {
    /**
     * 更新订单状态为超时
     *
     * @param orderIdList
     */
    void updateTimeoutOrderStatus(@Param("orderIdList") ArrayList<String> orderIdList);
}
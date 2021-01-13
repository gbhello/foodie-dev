package com.imooc.mapper;

import com.imooc.mapper.base.MyMapper;
import com.imooc.pojo.OrderItems;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author gengbin
 * @date 2021/1/12
 */
@Mapper
public interface OrderItemsMapper extends MyMapper<OrderItems> {
}
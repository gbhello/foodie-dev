package com.imooc.mapper;

import com.imooc.mapper.base.MyMapper;
import com.imooc.pojo.ItemsSpec;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author gengbin
 * @date 2021/1/12
 */
@Mapper
public interface ItemsSpecMapper extends MyMapper<ItemsSpec> {
    /**
     * 根据商品详情id获取商品详情
     *
     * @param itemSpecIdList
     * @return
     */
    List<ItemsSpec> getItemSpecBySpecIdList(List<String> itemSpecIdList);
}
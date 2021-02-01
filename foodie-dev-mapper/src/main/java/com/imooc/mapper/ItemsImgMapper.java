package com.imooc.mapper;

import com.imooc.mapper.base.MyMapper;
import com.imooc.pojo.ItemsImg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gengbin
 * @date 2021/1/12
 */
@Mapper
public interface ItemsImgMapper extends MyMapper<ItemsImg> {
    /**
     * 获取商品主图片列表
     *
     * @param itemIdList
     * @return
     */
    List<String> selectMainImgUrlByItemSpecIdList(@Param("itemIdList") List<String> itemIdList);
}
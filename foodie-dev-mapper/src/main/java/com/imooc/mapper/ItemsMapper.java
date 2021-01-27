package com.imooc.mapper;

import com.imooc.mapper.base.MyMapper;
import com.imooc.pojo.Items;
import com.imooc.pojo.vo.ItemCommentVO;
import com.imooc.pojo.vo.SearchItemsVO;
import com.imooc.pojo.vo.ShopCartVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author gengbin
 * @date 2021/1/12
 */
@Mapper
public interface ItemsMapper extends MyMapper<Items> {
    /**
     * 获取商品评论
     *
     * @param paramMap
     * @return
     */
    List<ItemCommentVO> getItemComments(@Param("paramMap") HashMap<String, Object> paramMap);

    /**
     * 搜索商品
     *
     * @param paramMap
     * @return
     */
    List<SearchItemsVO> searchItems(@Param("paramMap") HashMap<String, Object> paramMap);

    /**
     * 通过三级分类id搜索商品
     *
     * @param paramMap
     * @return
     */
    List<SearchItemsVO> searchItemsByThirdCatId(@Param("paramMap") HashMap<String, Object> paramMap);

    /**
     * 根据规格id查询商品
     *
     * @param specIdList
     * @return
     */
    List<ShopCartVO> getItemsBySpecIdList(@Param("specIdList") ArrayList<String> specIdList);
}
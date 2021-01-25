package com.imooc.service;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.vo.CommentLevelCountsVO;

import java.util.List;

/**
 * @author gengbin
 * @date 2021/1/24
 */
public interface ItemService {
    /**
     * 获取商品信息
     *
     * @param itemId
     * @return
     */
    Items getItemById(String itemId);

    /**
     * 获取商品图片列表
     *
     * @param itemId
     * @return
     */
    List<ItemsImg> getItemImgList(String itemId);

    /**
     * 获取商品详情列表
     *
     * @param itemId
     * @return
     */
    List<ItemsSpec> getItemSpecList(String itemId);

    /**
     * 获取商品参数
     *
     * @param itemId
     * @return
     */
    ItemsParam getItemParam(String itemId);

    /**
     * 获取评论等级
     *
     * @param itemId
     * @return
     */
    CommentLevelCountsVO getCommentLevel(String itemId);
}

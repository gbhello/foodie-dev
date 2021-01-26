package com.imooc.service;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.vo.CommentLevelCountsVO;
import com.imooc.pojo.vo.ShopCartVO;
import com.imooc.utils.PagedGridResult;

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

    /**
     * 获取分页评论
     *
     * @param itemId
     * @param level
     * @param pageNum
     * @param pageSize
     * @return
     */
    PagedGridResult getPagedComments(String itemId, Integer level, Integer pageNum, Integer pageSize);

    /**
     * 搜索商品
     *
     * @param keywords
     * @param sort
     * @param pageNum
     * @param pageSize
     * @return
     */
    PagedGridResult searchItems(String keywords, String sort, Integer pageNum, Integer pageSize);

    /**
     * 根据分类id搜索商品列表
     *
     * @param catId
     * @param sort
     * @param pageNum
     * @param pageSize
     * @return
     */
    PagedGridResult getItemByCatId(Integer catId, Integer sort, Integer pageNum, Integer pageSize);

    /**
     * 根据规格id获取商品
     *
     * @param itemSpecIds
     * @return
     */
    List<ShopCartVO> getItemBySpecIds(String itemSpecIds);
}

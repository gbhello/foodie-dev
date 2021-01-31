package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.enums.CommentLevel;
import com.imooc.mapper.*;
import com.imooc.pojo.*;
import com.imooc.pojo.vo.*;
import com.imooc.service.ItemService;
import com.imooc.utils.DesensitizationUtil;
import com.imooc.utils.PagedGridResult;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author gengbin
 * @date 2021/1/24
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Resource
    private ItemsMapper itemsMapper;
    @Resource
    private ItemsImgMapper itemsImgMapper;
    @Resource
    private ItemsSpecMapper itemsSpecMapper;
    @Resource
    private ItemsParamMapper itemsParamMapper;
    @Resource
    private ItemsCommentsMapper itemsCommentsMapper;

    @Override
    public Items getItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public List<ItemsImg> getItemImgList(String itemId) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);

        return itemsImgMapper.selectByExample(example);
    }

    @Override
    public List<ItemsSpec> getItemSpecList(String itemId) {
        Example example = new Example(ItemsSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);

        return itemsSpecMapper.selectByExample(example);
    }

    @Override
    public ItemsParam getItemParam(String itemId) {
        Example example = new Example(ItemsParam.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);

        return itemsParamMapper.selectOneByExample(example);
    }

    @Override
    public CommentLevelCountsVO getCommentLevel(String itemId) {
        Integer goodCounts = getCommentCounts(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = getCommentCounts(itemId, CommentLevel.NORMAL.type);
        Integer badCounts = getCommentCounts(itemId, CommentLevel.BAD.type);

        Integer totalCommoentCounts = goodCounts + normalCounts + badCounts;

        CommentLevelCountsVO commentLevelCountsVO = new CommentLevelCountsVO();
        commentLevelCountsVO.setGoodCounts(goodCounts);
        commentLevelCountsVO.setNormalCounts(normalCounts);
        commentLevelCountsVO.setBadCounts(badCounts);
        commentLevelCountsVO.setTotalCounts(totalCommoentCounts);

        return commentLevelCountsVO;
    }

    @Override
    public PagedGridResult getPagedComments(String itemId, Integer level, Integer pageNum, Integer pageSize) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("itemId", itemId);
        paramMap.put("level", level);

        PageHelper.startPage(pageNum, pageSize);
        List<ItemCommentVO> itemCommentVOList = itemsMapper.getItemComments(paramMap);

        PageInfo<ItemCommentVO> itemCommentVOPageInfo = new PageInfo<>(itemCommentVOList);

        //对用户名进行脱敏
        for (ItemCommentVO vo : itemCommentVOList) {
            vo.setNickname(DesensitizationUtil.commonDisplay(vo.getNickname()));
        }

        return setterPagedGrid(itemCommentVOList, pageNum);
    }

    @Override
    public PagedGridResult searchItems(String keywords, String sort, Integer pageNum, Integer pageSize) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("keywords", keywords);
        paramMap.put("sort", sort);

        PageHelper.startPage(pageNum, pageSize);
        List<SearchItemsVO> searchItemsVOList = itemsMapper.searchItems(paramMap);
        return setterPagedGrid(searchItemsVOList, pageNum);
    }

    @Override
    public PagedGridResult getItemByCatId(Integer catId, String sort, Integer pageNum, Integer pageSize) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("catId", catId);
        paramMap.put("sort", sort);

        PageHelper.startPage(pageNum, pageSize);
        List<SearchItemsVO> searchItemsVOList = itemsMapper.searchItemsByThirdCatId(paramMap);
        return setterPagedGrid(searchItemsVOList, pageNum);
    }

    @Override
    public List<ShopCartVO> getItemBySpecIds(String itemSpecIds) {
        String[] idArray = itemSpecIds.split(",");
        ArrayList<String> specIdList = new ArrayList<>();
        Collections.addAll(specIdList, idArray);
        return itemsMapper.getItemsBySpecIdList(specIdList);
    }

    @Override
    public List<ItemsSpec> getItemSpecBySpecIdList(List<String> itemSpecIdList) {
        return itemsSpecMapper.getItemSpecBySpecIdList(itemSpecIdList);
    }

    @Override
    public List<Items> getItemByIdList(List<String> itemIdList) {
        return itemsMapper.selectItemByIdList(itemIdList);
    }

    @Override
    public List<String> getItemMainImgByItemIdList(List<String> itemIdList) {
        return itemsImgMapper.selectMainImgUrlByItemIdList(itemIdList);
    }

    @Override
    public void updateItemSpecStock(HashMap<String, Integer> itemSpecIdStockMap, Integer updateType) {
        if (updateType == 1) {
            itemsMapper.increaseItemSpecStock(itemSpecIdStockMap);
        } else {
            itemsMapper.decreaseItemSpecStock(itemSpecIdStockMap);
        }
    }

    @Override
    public ItemSpecItemNameVO getItemSpecItemNameVO(List<String> itemSpecIdList) {
        return itemsMapper.selectItemSpecItemName(itemSpecIdList);
    }


    private PagedGridResult setterPagedGrid(List<?> list, Integer pageNum) {
        PageInfo<?> itemCommentVoPageInfo = new PageInfo<>(list);
        PagedGridResult pagedGridResult = new PagedGridResult();
        pagedGridResult.setPage(pageNum);
        pagedGridResult.setRows(list);
        pagedGridResult.setTotal(itemCommentVoPageInfo.getPages());
        pagedGridResult.setRecords(itemCommentVoPageInfo.getTotal());

        return pagedGridResult;
    }

    private Integer getCommentCounts(String itemId, Integer level) {
        ItemsComments itemsComments = new ItemsComments();
        itemsComments.setItemId(itemId);
        if (level != null) {
            itemsComments.setCommentLevel(level);
        }
        return itemsCommentsMapper.selectCount(itemsComments);
    }

}

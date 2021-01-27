package com.imooc.controller;

import com.imooc.consts.BusinessConsts;
import com.imooc.consts.ValidationErrorCode;
import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.vo.CommentLevelCountsVO;
import com.imooc.pojo.vo.ItemInfoVO;
import com.imooc.pojo.vo.ShopCartVO;
import com.imooc.service.ItemService;
import com.imooc.utils.CommonResult;
import com.imooc.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author gengbin
 * @date 2021/1/24
 */
@Api(value = "商品接口", tags = "商品信息展示的相关接口")
@RestController
@RequestMapping("items")
public class ItemsController {

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("/getItemDetail/{itemId}")
    public CommonResult getItemDetail(@ApiParam(name = "itemId", value = "商品id", required = true)
                                      @PathVariable String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return CommonResult.errorMsg(ValidationErrorCode.PARAM_EMPTY);
        }
        Items item = itemService.getItemById(itemId);
        List<ItemsImg> itemsImgList = itemService.getItemImgList(itemId);
        List<ItemsSpec> itemsSpecList = itemService.getItemSpecList(itemId);
        ItemsParam itemParam = itemService.getItemParam(itemId);

        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(item);
        itemInfoVO.setItemsImgList(itemsImgList);
        itemInfoVO.setItemsSpecList(itemsSpecList);
        itemInfoVO.setItemsParam(itemParam);

        return CommonResult.ok(itemInfoVO);
    }

    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级", httpMethod = "GET")
    @GetMapping("/getCommentLevel")
    public CommonResult getCommentLevel(@ApiParam(name = "itemId", value = "查询商品评价等级", required = true) @RequestParam String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return CommonResult.errorMsg(ValidationErrorCode.PARAM_EMPTY);
        }
        CommentLevelCountsVO commentLevelCountsVO = itemService.getCommentLevel(itemId);
        return CommonResult.ok(commentLevelCountsVO);
    }

    @ApiOperation(value = "获取商品评论", notes = "获取商品评论", httpMethod = "GET")
    @RequestMapping("/getComment")
    public CommonResult getComment(@ApiParam(name = "itemId", value = "商品id", required = true) @RequestParam String itemId,
                                   @ApiParam(name = "level", value = "评价等级", required = false) @RequestParam Integer level,
                                   @ApiParam(name = "pageNum", value = "商品id", required = true) @RequestParam Integer pageNum,
                                   @ApiParam(name = "pageSize", value = "商品id", required = true) @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(itemId)) {
            return CommonResult.errorMsg(ValidationErrorCode.PARAM_EMPTY);
        }
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = BusinessConsts.DEFAULT_PAGE_SIZE;
        }
        PagedGridResult result = itemService.getPagedComments(itemId, level, pageNum, pageSize);
        return CommonResult.ok(result);
    }

    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表", httpMethod = "GET")
    @GetMapping("/search")
    public CommonResult search(@ApiParam(name = "keywords", value = "关键字", required = true) @RequestParam String keywords,
                               @ApiParam(name = "sort", value = "排序", required = false) @RequestParam String sort,
                               @ApiParam(name = "pageNum", value = "页数", required = false) @RequestParam Integer pageNum,
                               @ApiParam(name = "pageSize", value = "页码", required = false) @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(keywords)) {
            return CommonResult.errorMsg(ValidationErrorCode.PARAM_EMPTY);
        }
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = BusinessConsts.DEFAULT_PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = itemService.searchItems(keywords, sort, pageNum, pageSize);
        return CommonResult.ok(pagedGridResult);
    }

    @ApiOperation(value = "通过分类id搜索商品列表", notes = "通过分类id搜索商品列表", httpMethod = "GET")
    @GetMapping("/getItem")
    public CommonResult getItem(@ApiParam(name = "catId", value = "三级分类id", required = true) @RequestParam Integer catId,
                                @ApiParam(name = "sort", value = "排序", required = false) @RequestParam String sort,
                                @ApiParam(name = "pageNum", value = "页码", required = false) @RequestParam Integer pageNum,
                                @ApiParam(name = "pageSize", value = "页数", required = false) @RequestParam Integer pageSize) {
        if (catId == null) {
            return CommonResult.errorMsg(ValidationErrorCode.PARAM_EMPTY);
        }
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = BusinessConsts.DEFAULT_PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = itemService.getItemByCatId(catId, sort, pageNum, pageSize);
        return CommonResult.ok(pagedGridResult);
    }

    @ApiOperation(value = "根据商品规格ids查找最新的商品数据", notes = "根据商品规格ids查找最新的商品数据", httpMethod = "GET")
    @GetMapping("/refresh")
    public CommonResult refresh(@ApiParam(name = "itemSpecIds", value = "拼接的规id", required = true, example = "1001,1002") @RequestParam String itemSpecIds) {
        if (StringUtils.isBlank(itemSpecIds)) {
            return CommonResult.errorMsg(ValidationErrorCode.PARAM_EMPTY);
        }
        List<ShopCartVO> shopCartVOList = itemService.getItemBySpecIds(itemSpecIds);
        return CommonResult.ok(shopCartVOList);
    }
}

package com.imooc.controller;

import com.imooc.consts.ValidationErrorCode;
import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.vo.CommentLevelCountsVO;
import com.imooc.pojo.vo.ItemInfoVO;
import com.imooc.service.ItemService;
import com.imooc.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
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
        Assert.hasText(itemId, ValidationErrorCode.PARAM_EMPTY);
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

    @ApiOperation(value = "查询商品伶伦等级", notes = "查询商品评价等级", httpMethod = "GET")
    @GetMapping("/getCommentLevel")
    public CommonResult getCommentLevel(@ApiParam(name = "itemId", value = "查询商品评价等级", required = true)
                                        @RequestParam String itemId) {
        Assert.hasText(itemId, ValidationErrorCode.PARAM_EMPTY);
        CommentLevelCountsVO commentLevelCountsVO = itemService.getCommentLevel(itemId);
        return CommonResult.ok(commentLevelCountsVO);
    }

    @ApiOperation(value = "获取商品评论", notes = "获取商品评论", httpMethod = "GET")
    @RequestMapping("/getComment")
    public CommonResult getComment(@ApiParam(name = "itemId", value = "商品id", required = true)
                                   @RequestParam("itemId") String itemId,
                                   @ApiParam(name = "level", value = "评价等级", required = false)
                                   @RequestParam("level") Integer level,
                                   @ApiParam(name = "pageNo", value = "商品id", required = true)
                                   @RequestParam("pageNo") Integer pageNo,
                                   @ApiParam(name = "pageSize", value = "商品id", required = true)
                                   @RequestParam("pageSize") Integer pageSize) {
        Assert.hasText(itemId, ValidationErrorCode.PARAM_EMPTY);
        return CommonResult.ok();

    }
}

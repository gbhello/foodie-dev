package com.imooc.controller.center;

import com.imooc.consts.BusinessConsts;
import com.imooc.consts.YesOrNo;
import com.imooc.pojo.OrderItems;
import com.imooc.pojo.Orders;
import com.imooc.pojo.bo.center.OrderItemsCommentBO;
import com.imooc.service.MyCommentsService;
import com.imooc.service.OrderService;
import com.imooc.utils.CommonResult;
import com.imooc.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author gengbin
 * @date 2021/2/13
 */
@Api(tags = "用户中心评价模块")
@RestController
@RequestMapping("mycomments")
public class MyCommentsController {
    @Autowired
    private MyCommentsService myCommentsService;
    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    @PostMapping("getOrderList")
    public CommonResult getOrderList(@RequestParam String userId, @RequestParam String orderId) {
        // 判断用户和订单是否关联
        Orders order = orderService.getMyOrder(userId, orderId);
        if (order == null) {
            return CommonResult.errorMsg("订单不存在！");
        }
        // 判断该笔订单是否已经评价，评价过了就不再继续
        if (order.getIsComment().equals(YesOrNo.YES.type)) {
            return CommonResult.errorMsg("该笔订单已经评价");
        }
        List<OrderItems> orderItemsList = myCommentsService.getOrderItems(orderId);
        return CommonResult.ok(orderItemsList);
    }

    @ApiOperation(value = "保存评论列表", httpMethod = "POST")
    @PostMapping("saveCommentList")
    public CommonResult saveCommentList(@RequestParam String userId, @RequestParam String orderId, @RequestBody List<OrderItemsCommentBO> commentBOList) {
        Orders myOrder = orderService.getMyOrder(userId, orderId);
        if (myOrder == null) {
            return CommonResult.errorMsg("订单不存在！");
        }
        if (CollectionUtils.isEmpty(commentBOList)) {
            return CommonResult.errorMsg("评论内容不能为空！");
        }
        myCommentsService.saveCommentList(orderId, userId, commentBOList);
        return CommonResult.ok();
    }

    @ApiOperation(value = "获取我的评论", httpMethod = "GET")
    @GetMapping("getMyComments")
    public CommonResult getMyComments(@RequestParam String userId, @RequestParam Integer page, @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return CommonResult.ok(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = BusinessConsts.DEFAULT_PAGE_SIZE;
        }
        PagedGridResult result = myCommentsService.getMyComments(userId, page, pageSize);
        return CommonResult.ok(result);
    }

}

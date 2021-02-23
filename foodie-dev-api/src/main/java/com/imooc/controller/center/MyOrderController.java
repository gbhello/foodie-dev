package com.imooc.controller.center;

import com.imooc.consts.BusinessConsts;
import com.imooc.pojo.Orders;
import com.imooc.pojo.vo.OrderStatusCountsVO;
import com.imooc.service.MyOrdersService;
import com.imooc.service.OrderService;
import com.imooc.utils.CommonResult;
import com.imooc.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author gengbin
 * @date 2021/2/14
 */
@Api(tags = "用户中心我的订单接口")
@RestController
@RequestMapping("myOrders")
public class MyOrderController {
    @Autowired
    private MyOrdersService myOrdersService;
    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "获得订单状态数概况", notes = "获得订单状态数概况", httpMethod = "POST")
    @PostMapping("/statusCounts")
    public CommonResult statusCounts(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId) {

        if (StringUtils.isBlank(userId)) {
            return CommonResult.errorMsg(null);
        }

        OrderStatusCountsVO result = myOrdersService.getOrderStatusCounts(userId);

        return CommonResult.ok(result);
    }

    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    @PostMapping("/query")
    public CommonResult query(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderStatus", value = "订单状态", required = false)
            @RequestParam Integer orderStatus,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize) {

        if (StringUtils.isBlank(userId)) {
            return CommonResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = BusinessConsts.DEFAULT_PAGE_SIZE;
        }

        PagedGridResult grid = myOrdersService.queryMyOrders(userId,
                orderStatus,
                page,
                pageSize);

        return CommonResult.ok(grid);
    }


    // 商家发货没有后端，所以这个接口仅仅只是用于模拟
    @ApiOperation(value = "商家发货", notes = "商家发货", httpMethod = "GET")
    @GetMapping("/deliver")
    public CommonResult deliver(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId) throws Exception {

        if (StringUtils.isBlank(orderId)) {
            return CommonResult.errorMsg("订单ID不能为空");
        }
        myOrdersService.updateDeliverOrderStatus(orderId);

        return CommonResult.ok();
    }


    @ApiOperation(value = "用户确认收货", notes = "用户确认收货", httpMethod = "POST")
    @PostMapping("/confirmReceive")
    public CommonResult confirmReceive(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId) throws Exception {

        Orders myOrder = orderService.getMyOrder(userId, orderId);
        if (myOrder == null) {
            return CommonResult.errorMsg("订单不存在");
        }

        boolean res = myOrdersService.updateReceiveOrderStatus(orderId);
        if (!res) {
            return CommonResult.errorMsg("订单确认收货失败！");
        }

        return CommonResult.ok();
    }

    @ApiOperation(value = "用户删除订单", notes = "用户删除订单", httpMethod = "POST")
    @PostMapping("/delete")
    public CommonResult delete(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId) throws Exception {

        Orders myOrder = orderService.getMyOrder(userId, orderId);
        if (myOrder == null) {
            return CommonResult.errorMsg("订单不存在");
        }

        boolean res = myOrdersService.deleteOrder(userId, orderId);
        if (!res) {
            return CommonResult.errorMsg("订单删除失败！");
        }

        return CommonResult.ok();
    }

    @ApiOperation(value = "查询订单动向", notes = "查询订单动向", httpMethod = "POST")
    @PostMapping("/trend")
    public CommonResult trend(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize) {

        if (StringUtils.isBlank(userId)) {
            return CommonResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = BusinessConsts.DEFAULT_PAGE_SIZE;
        }

        PagedGridResult grid = myOrdersService.getOrdersTrend(userId,
                page,
                pageSize);

        return CommonResult.ok(grid);
    }
}

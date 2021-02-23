package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.consts.YesOrNo;
import com.imooc.mapper.ItemsCommentsMapper;
import com.imooc.mapper.OrderItemsMapper;
import com.imooc.mapper.OrderStatusMapper;
import com.imooc.mapper.OrdersMapper;
import com.imooc.pojo.OrderItems;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.Orders;
import com.imooc.pojo.bo.center.OrderItemsCommentBO;
import com.imooc.pojo.vo.MyCommentVO;
import com.imooc.service.MyCommentsService;
import com.imooc.utils.PagedGridResult;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author gengbin
 * @date 2021/2/13
 */
@Service
public class MyCommentsServiceImpl implements MyCommentsService {
    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private OrderItemsMapper orderItemsMapper;
    @Autowired
    private Sid sid;
    @Resource
    private ItemsCommentsMapper itemsCommentsMapper;
    @Resource
    private OrderStatusMapper orderStatusMapper;


    @Override
    public List<OrderItems> getOrderItems(String orderId) {
        OrderItems orderItem = new OrderItems();
        orderItem.setOrderId(orderId);
        return orderItemsMapper.select(orderItem);
    }

    @Override
    public void saveCommentList(String orderId, String userId, List<OrderItemsCommentBO> commentBOList) {
        for (OrderItemsCommentBO bo : commentBOList) {
            bo.setCommentId(sid.next());
        }
        //1.保存评价
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("commentList", commentBOList);
        itemsCommentsMapper.saveComments(map);

        //2.修改订单表 已评价
        Orders order = new Orders();
        order.setId(orderId);
        order.setIsComment(YesOrNo.YES.type);
        ordersMapper.updateByPrimaryKeySelective(order);

        //3.修改订单状态表的留言时间
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }

    @Override
    public PagedGridResult getMyComments(String userId, Integer page, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        PageHelper.startPage(page, pageSize);
        List<MyCommentVO> list = itemsCommentsMapper.getMyComments(map);
        return this.setterPagedGrid(list, page);
    }

    public PagedGridResult setterPagedGrid(List<?> list, Integer page) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }
}

package com.imooc.service.impl;

import com.imooc.consts.BusinessConsts;
import com.imooc.consts.YesOrNo;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.mapper.OrderItemsMapper;
import com.imooc.mapper.OrderStatusMapper;
import com.imooc.mapper.OrdersMapper;
import com.imooc.pojo.*;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.pojo.vo.ItemSpecIdItemNameVO;
import com.imooc.pojo.vo.MerchantOrdersVO;
import com.imooc.pojo.vo.OrderVO;
import com.imooc.service.AddressService;
import com.imooc.service.ItemService;
import com.imooc.service.OrderService;
import com.imooc.utils.DateUtil;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author gengbin
 * @date 2021/1/27
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private Sid sid;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ItemService itemService;
    @Resource
    private OrderItemsMapper orderItemsMapper;
    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private OrderStatusMapper orderStatusMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public OrderVO createOrder(SubmitOrderBO submitOrderBO) {
        String userId = submitOrderBO.getUserId();
        String addressId = submitOrderBO.getAddressId();
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        Integer payMethod = submitOrderBO.getPayMethod();
        // 包邮费用设置为0
        Integer postAmount = 0;

        String orderId = sid.next();

        UserAddress userAddress = addressService.getUserAddress(addressId);

        //1.新订单数据保存
        Orders newOrder = createNewOrder(submitOrderBO, userAddress);

        //2.循环根据itemSpecIds保存订单商品信息表
        String[] itemSpecIdArr = itemSpecIds.split(",");
        List<String> itemSpecIdList = Arrays.asList(itemSpecIdArr);
        // 商品原价累计
        Integer totalAmount = 0;
        //优惠后的实际支付价格累计
        Integer realPayAmount = 0;
        //TODO 整合redis后，商品的数量重新从redis中获取
        int buyCounts = 1;


        List<ItemsSpec> itemsSpecList = itemService.getItemSpecBySpecIdList(itemSpecIdList);
        for (ItemsSpec itemsSpec : itemsSpecList) {
            totalAmount += itemsSpec.getPriceNormal() * buyCounts;
            realPayAmount += itemsSpec.getPriceDiscount() * buyCounts;
        }

        List<String> itemIdList = itemsSpecList.stream().map(ItemsSpec::getItemId).collect(Collectors.toList());

        List<ItemSpecIdItemNameVO> itemSpecIdItemNameVOList = itemService.getItemSpecItemNameVO(itemSpecIdList);
        List<String> mainImgUrlList = itemService.getItemMainImgByItemSpecIdList(itemSpecIdList);

        ArrayList<OrderItems> orderItemList = new ArrayList<>();
        for (int i = 0; i < itemsSpecList.size(); i++) {
            OrderItems subOrderItem = new OrderItems();
            String subOrderId = sid.next();

            subOrderItem.setId(subOrderId);
            subOrderItem.setOrderId(orderId);
            subOrderItem.setItemId(itemIdList.get(i));
            subOrderItem.setItemName(itemSpecIdItemNameVOList.get(i).getItemName());
            subOrderItem.setItemImg(mainImgUrlList.get(i));
            subOrderItem.setBuyCounts(buyCounts);
            subOrderItem.setItemSpecId(itemsSpecList.get(i).getItemId());
            subOrderItem.setItemSpecName(itemsSpecList.get(i).getName());
            subOrderItem.setPrice(itemsSpecList.get(i).getPriceDiscount());

            orderItemList.add(subOrderItem);
        }
        orderItemsMapper.insertList(orderItemList);
        //用户提交订单后，规格表中需要刷新库存
        HashMap<String, Integer> itemSpecIdStockMap = new HashMap<>();
        for (String itemSpecId : itemSpecIdArr) {
            itemSpecIdStockMap.put(itemSpecId, 1);
        }
        itemService.updateItemSpecStock(itemSpecIdStockMap, BusinessConsts.Order.DECREASE_ITEM_SPEC_STOCK);

        newOrder.setTotalAmount(totalAmount);
        newOrder.setRealPayAmount(realPayAmount);
        ordersMapper.insert(newOrder);

        //3.保存订单状态
        OrderStatus waitPayOrderStatus = new OrderStatus();
        waitPayOrderStatus.setOrderId(orderId);
        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        waitPayOrderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(waitPayOrderStatus);

        //4.构建商户订单，用于传给支付中心
        MerchantOrdersVO merchantOrdersVO = new MerchantOrdersVO();
        merchantOrdersVO.setMerchantOrderId(orderId);
        merchantOrdersVO.setMerchantUserId(userId);
        merchantOrdersVO.setAmount(realPayAmount + postAmount);
        merchantOrdersVO.setPayMethod(payMethod);

        //5.构建自定义订单VO
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(orderId);
        orderVO.setMerchantOrdersVO(merchantOrdersVO);

        return orderVO;
    }

    @Override
    public void updateOrderStatus(String merchantOrderId, Integer orderStatus) {
        OrderStatus paidStatus = new OrderStatus();
        paidStatus.setOrderId(merchantOrderId);
        paidStatus.setOrderStatus(orderStatus);
        paidStatus.setPayTime(new Date());

        orderStatusMapper.updateByPrimaryKeySelective(paidStatus);
    }

    @Override
    public OrderStatus getOrderStatusInfo(String orderId) {
        return orderStatusMapper.selectByPrimaryKey(orderId);
    }

    private Orders createNewOrder(SubmitOrderBO submitOrderBO, UserAddress userAddress) {
        String orderId = sid.next();
        Integer postAmount = 0;

        Orders newOrder = new Orders();
        newOrder.setId(orderId);
        newOrder.setUserId(submitOrderBO.getUserId());
        newOrder.setReceiverName(userAddress.getReceiver());
        newOrder.setReceiverMobile(userAddress.getMobile());
        newOrder.setReceiverAddress(userAddress.getProvince() + " " + userAddress.getCity() + " " + userAddress.getDistrict() + " " + userAddress.getDetail());
        newOrder.setPostAmount(postAmount);
        newOrder.setPayMethod(submitOrderBO.getPayMethod());
        newOrder.setLeftMsg(submitOrderBO.getLeftMsg());
        newOrder.setIsComment(YesOrNo.NO.type);
        newOrder.setIsDelete(YesOrNo.NO.type);
        newOrder.setCreatedTime(new Date());
        newOrder.setUpdatedTime(new Date());

        return newOrder;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void closeOrder() {
        //查询所有未付款订单，判断时间是否超过1天，超时则关闭
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        List<OrderStatus> orderStatusList = orderStatusMapper.select(orderStatus);
        ArrayList<String> orderIdList = new ArrayList<>();

        for (OrderStatus status : orderStatusList) {
            Date createdTime = status.getCreatedTime();
            int days = DateUtil.daysBetween(createdTime, new Date());
            if (days >= 1) {
                orderIdList.add(status.getOrderId());
            }
        }
        //超市1天，关闭订单
        doCloseOrder(orderIdList);
    }

    private void doCloseOrder(ArrayList<String> orderIdList) {
        orderStatusMapper.updateTimeoutOrderStatus(orderIdList);
    }
}

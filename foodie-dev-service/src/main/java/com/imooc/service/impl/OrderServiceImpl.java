package com.imooc.service.impl;

import com.imooc.consts.YesOrNo;
import com.imooc.mapper.OrderItemsMapper;
import com.imooc.pojo.*;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.pojo.vo.ItemSpecItemNameVO;
import com.imooc.pojo.vo.OrderVO;
import com.imooc.service.AddressService;
import com.imooc.service.ItemService;
import com.imooc.service.OrderService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public OrderVO createOrder(SubmitOrderBO submitOrderBO) {
        String userId = submitOrderBO.getUserId();
        String addressId = submitOrderBO.getAddressId();
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        Integer payMethod = submitOrderBO.getPayMethod();
        String leftMsg = submitOrderBO.getLeftMsg();
        // 包邮费用设置为0
        Integer postAmount = 0;

        String orderId = sid.next();

        UserAddress userAddress = addressService.getUserAddress(addressId);

        //1.新订单数据保存
        Orders newOrder = createNewOrder(submitOrderBO, userAddress);

        //2.循环根据itemSpecIds保存订单商品信息表
        String[] itemSpecIdArr = itemSpecIds.split(",");
        // 商品原价累计
        Integer totalAmount = 0;
        //优惠后的实际支付价格累计
        Integer realPayAmount = 0;
        //TODO 整合redis后，商品的数量重新从redis中获取
        int buyCounts = 1;


        List<ItemsSpec> itemsSpecList = itemService.getItemSpecBySpecIdList(Arrays.asList(itemSpecIdArr));
        for (ItemsSpec itemsSpec : itemsSpecList) {
            totalAmount += itemsSpec.getPriceNormal() * buyCounts;
            realPayAmount += itemsSpec.getPriceDiscount() * buyCounts;
        }

        List<String> itemIdList = itemsSpecList.stream().map(ItemsSpec::getItemId).collect(Collectors.toList());

        List<Items> itemList = itemService.getItemByIdList(itemIdList);
        ItemSpecItemNameVO itemSpecItemNameVO = itemService.getItemSpecItemNameVO(Arrays.asList(itemSpecIdArr));
        List<String> mainImgUrlList = itemService.getItemMainImgByItemIdList(itemIdList);

        ArrayList<OrderItems> orderItemList = new ArrayList<>();
        for (int i = 0; i < itemsSpecList.size(); i++) {
            OrderItems subOrderItem = new OrderItems();
            String subOrderId = sid.next();

            subOrderItem.setId(subOrderId);
            subOrderItem.setOrderId(orderId);
            subOrderItem.setItemId(itemIdList.get(i));
            subOrderItem.setItemName(itemList.get(i).getItemName());
            subOrderItem.setItemImg(mainImgUrlList.get(i));
            subOrderItem.setBuyCounts(buyCounts);
            subOrderItem.setItemSpecId(itemsSpecList.get(i).getItemId());
            subOrderItem.setItemSpecName(itemsSpecList.get(i).getName());
            subOrderItem.setPrice(itemsSpecList.get(i).getPriceDiscount());

            orderItemList.add(subOrderItem);
        }
//        orderItemsMapper.insertList(orderItemList);
        //用户提交订单后，规格表中需要刷新库存
        HashMap<String, Integer> itemSpecIdStockMap = new HashMap<>();
        for(String itemSpecId:itemSpecIdArr){
            itemSpecIdStockMap.put(itemSpecId,1);
        }
//        itemService.updateItemSpecStock(itemSpecIdStockMap, BusinessConsts.Order.DECREASE_ITEM_SPEC_STOCK);


        for (String itemSpecId : itemSpecIdArr) {
            //根据规格id，查询规格的具体信息，主要获取价格


        }
        return null;
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
}

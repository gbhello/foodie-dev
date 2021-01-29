package com.imooc.service.impl;

import com.imooc.consts.YesOrNo;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.Orders;
import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.pojo.vo.OrderVO;
import com.imooc.service.AddressService;
import com.imooc.service.ItemService;
import com.imooc.service.OrderService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    @Override
    public OrderVO createOrder(SubmitOrderBO submitOrderBO) {
        String userId = submitOrderBO.getUserId();
        String addressId = submitOrderBO.getAddressId();
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        Integer payMethod = submitOrderBO.getPayMethod();
        String leftMsg = submitOrderBO.getLeftMsg();
        // 包邮费用设置为0
        Integer postAmount = 0;

        UserAddress userAddress = addressService.getUserAddress(addressId);

        //1.新订单数据保存
        Orders newOrder = createNewOrder(submitOrderBO, userAddress);

        //2.循环根据itemSpecIds保存订单商品信息表
        String[] itemSpecIdArr = itemSpecIds.split(",");
        // 商品原价累计
        Integer totalAmount = 0;
        //优惠后的实际支付价格累计
        Integer realPayAmount = 0;

        List<ItemsSpec> itemsSpecList = itemService.getItemSpecBySpecIdList(Arrays.asList(itemSpecIdArr));
        for (String itemSpecId : itemSpecIdArr) {
            //TODO 整合redis后，商品购买的数量重新从redis的购物车中获取
            int buyCounts = 1;
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

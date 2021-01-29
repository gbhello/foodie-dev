package com.imooc.service;

import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;

import java.util.List;

/**
 * @author gengbin
 * @date 2021/1/25
 */
public interface AddressService {
    /**
     * 获取全部地址列表
     *
     * @param userId
     * @return
     */
    List<UserAddress> getAddressList(String userId);

    /**
     * 添加地址
     *
     * @param addressBO
     */
    void addUserAddress(AddressBO addressBO);

    /**
     * 更新地址
     *
     * @param addressBO
     */
    void updateUserAddress(AddressBO addressBO);

    /**
     * 删除地址
     *
     * @param userId
     */
    void deleteAddress(String userId);

    /**
     * 根据addressId获取地址信息
     *
     * @param addressId
     * @return
     */
    UserAddress getUserAddress(String addressId);
}

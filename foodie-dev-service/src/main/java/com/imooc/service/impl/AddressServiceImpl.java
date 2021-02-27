package com.imooc.service.impl;

import com.imooc.consts.YesOrNo;
import com.imooc.mapper.UserAddressMapper;
import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;
import com.imooc.service.AddressService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author gengbin
 * @date 2021/1/25
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Resource
    private UserAddressMapper userAddressMapper;
    @Autowired
    private Sid sid;

    @Override
    public List<UserAddress> getAddressList(String userId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);

        return userAddressMapper.select(userAddress);
    }

    @Override
    public void addUserAddress(AddressBO addressBO) {
        //判断当前用户是否存在地址，如果没有，则新增为”默认地址“
        Integer isDefault = 0;
        List<UserAddress> addressList = this.getAddressList(addressBO.getUserId());
        if (CollectionUtils.isEmpty(addressList)) {
            isDefault = 1;
        }
        String addressId = sid.next();

        //保存地址到数据库
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, userAddress);

        userAddress.setId(addressId);
        userAddress.setIsDefault(isDefault);
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());

        userAddressMapper.insert(userAddress);
    }

    @Override
    public void updateUserAddress(AddressBO addressBO) {
        String addressId = addressBO.getAddressId();
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, userAddress);

        userAddress.setId(addressId);
        userAddress.setUpdatedTime(new Date());

        userAddressMapper.updateByPrimaryKeySelective(userAddress);
    }

    @Override
    public void deleteAddress(String addressId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setId(addressId);
        userAddressMapper.deleteByPrimaryKey(userAddress);
    }

    @Override
    public UserAddress getUserAddress(String addressId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setId(addressId);

        return userAddressMapper.selectByPrimaryKey(addressId);
    }

    /**
     * 更新地址为默认地址
     *
     * @param userId
     * @param addressId
     */
    @Override
    public void updateUserAddressToBeDefault(String userId, String addressId) {
        //1.查找默认地址，设置为不默认
        UserAddress queryAddress = new UserAddress();
        queryAddress.setUserId(userId);
        queryAddress.setIsDefault(YesOrNo.YES.type);
        UserAddress userAddress = userAddressMapper.selectOne(queryAddress);
        userAddress.setIsDefault(YesOrNo.NO.type);
        userAddressMapper.updateByPrimaryKeySelective(userAddress);

        //2.根据地址id设置为默认的地址
        UserAddress defaultAddress = new UserAddress();
        defaultAddress.setId(addressId);
        defaultAddress.setUserId(userId);
        defaultAddress.setIsDefault(YesOrNo.YES.type);
        userAddressMapper.updateByPrimaryKeySelective(defaultAddress);
    }
}

package com.imooc.service.impl;

import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.CenterUserBO;
import com.imooc.service.CenterUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author gengbin
 * @date 2021/2/12
 */
@Service
public class CenterUserServiceImpl implements CenterUserService {
    @Resource
    private UsersMapper usersMapper;

    @Override
    public Users getUserInfo(String userId) {
        Users user = usersMapper.selectByPrimaryKey(userId);
        user.setPassword(null);
        return user;
    }

    @Override
    public Users uploadUserFace(String userId, String finalUserFaceUrl) {
        Users user = new Users();
        user.setId(userId);
        user.setFace(finalUserFaceUrl);
        user.setUpdatedTime(new Date());
        usersMapper.updateByPrimaryKeySelective(user);
        return getUserInfo(userId);
    }

    @Override
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO) {
        Users user = new Users();
        BeanUtils.copyProperties(centerUserBO, user);
        user.setId(userId);
        user.setUpdatedTime(new Date());
        usersMapper.updateByPrimaryKeySelective(user);
        return getUserInfo(userId);
    }

}

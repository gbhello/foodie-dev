package com.imooc.service.impl;

import com.imooc.consts.BusinessConsts;
import com.imooc.enums.Sex;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.UserBO;
import com.imooc.service.UserService;
import com.imooc.utils.DateUtil;
import com.imooc.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author gengbin
 * @date 2021/1/14
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UsersMapper usersMapper;
    @Resource
    private Sid sid;

    @Override
    public boolean findUsernameIsExist(String username) {
        // Example userExample = new Example(Users.class);
        // Example.Criteria userExampleCriteria = userExample.createCriteria();
        // usersMapper.selectOneByExample()
        Users user = new Users();
        user.setUsername(username);
        Users resultUser = usersMapper.selectOne(user);

        return resultUser != null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(UserBO userBO) {
        Users user = convertUser(userBO);
        usersMapper.insert(user);
        return user;
    }

    @Override
    public Users getUserForLogin(String username, String password) throws Exception {
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(MD5Utils.getMD5Str(password));

        return user;
    }

    private Users convertUser(UserBO userBO) {
        String userId = sid.next();
        Users user = new Users();
        user.setId(userId);
        user.setUsername(userBO.getUsername());
        try {
            user.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 默认用户昵称同用户名
        user.setNickname(userBO.getUsername());
        // 默认头像
        user.setFace(BusinessConsts.DEFAULT_USER_FACE);
        // 默认生日
        user.setBirthday(DateUtil.stringToDate(BusinessConsts.DEFAULT_BIRTHDAY));
        // 默认性别为保密
        user.setSex(Sex.secret.type);

        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
        return user;
    }
}

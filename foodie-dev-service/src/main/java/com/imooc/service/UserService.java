package com.imooc.service;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.UserBO;

/**
 * @author gengbin
 * @date 2021/1/14
 */
public interface UserService {
    /**
     * 查询用户名是否存在
     * @param username
     * @return
     */
    boolean findUsernameIsExist(String username);

    /**
     * 添加用户
     * @param userBO
     * @return
     */
    Users createUser(UserBO userBO);

    /**
     * 查询用户
     * @param username
     * @param password
     * @return
     */
    Users getUserForLogin(String username, String password) throws Exception;
}

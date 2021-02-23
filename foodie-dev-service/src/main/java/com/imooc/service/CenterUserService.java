package com.imooc.service;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.CenterUserBO;

/**
 * @author gengbin
 * @date 2021/2/12
 */
public interface CenterUserService {
    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    Users getUserInfo(String userId);

    /**
     * 更新用户头像
     *
     * @param userId
     * @param finalUserFaceUrl
     * @return
     */
    Users uploadUserFace(String userId, String finalUserFaceUrl);

    /**
     * 更新用户信息
     *
     * @param userId
     * @param centerUserBO
     * @return
     */
    Users updateUserInfo(String userId, CenterUserBO centerUserBO);
}

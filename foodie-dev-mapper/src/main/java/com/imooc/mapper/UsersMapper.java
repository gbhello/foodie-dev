package com.imooc.mapper;

import com.imooc.mapper.base.MyMapper;
import com.imooc.pojo.Users;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author gengbin
 * @date 2021/1/12
 */
@Mapper
public interface UsersMapper extends MyMapper<Users> {
}
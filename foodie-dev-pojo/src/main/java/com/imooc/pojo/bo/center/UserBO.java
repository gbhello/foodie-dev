package com.imooc.pojo.bo.center;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author gengbin
 * @date 2021/1/14
 */
@ApiModel(value = "用户对象BO")
@Data
public class UserBO {
    @ApiModelProperty(value = "用户名",name = "username",example = "imooc",required = true)
    private String username;
    @ApiModelProperty(value = "密码",name = "password",example = "123456",required = true)
    private String password;
    @ApiModelProperty(value = "确认密码",name = "confirmPassword",example = "123456",required = false)
    private String confirmPassword;
}

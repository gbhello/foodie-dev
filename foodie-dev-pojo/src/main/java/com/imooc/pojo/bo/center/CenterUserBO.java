package com.imooc.pojo.bo.center;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author gengbin
 * @date 2021/2/13
 */
@Data
public class CenterUserBO {
    private String username;
    private String password;
    private String confirmPassword;
    @NotBlank(message = "用户昵称不能为空")
    @Length(max = 12, message = "用户昵称不能超过12位")
    private String nickname;
    @Length(max = 12, message = "用户真实姓名不能超过12位")
    private String realname;
    @Pattern(regexp = "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\\d{8})$", message = "手机号格式不正确")
    private String mobile;
    @Email
    private String email;
    @Min(value = 0, message = "性别选择不正确")
    @ApiModelProperty(value = "性别", name = "sex", example = "0:女 1:男 2:保密", required = false)
    private Integer sex;
    @ApiModelProperty(value = "生日", name = "birthday", example = "1900-01-01", required = false)
    private Date birthday;

}

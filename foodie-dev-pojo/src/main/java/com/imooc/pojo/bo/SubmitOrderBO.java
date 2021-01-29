package com.imooc.pojo.bo;

import lombok.Data;

/**
 * @author gengbin
 * @date 2021/1/27
 */
@Data
public class SubmitOrderBO {
    private String userId;
    private String itemSpecIds;
    private String addressId;
    private Integer payMethod;
    private String leftMsg;
}

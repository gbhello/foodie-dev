package com.imooc.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author gengbin
 * @date 2021/2/16
 */
@Data
public class MyOrdersVO {
    private String orderId;
    private Date createdTime;
    private Integer payMethod;
    private Integer realPayAmount;
    private Integer postAmount;
    private Integer isComment;
    private Integer orderStatus;
}

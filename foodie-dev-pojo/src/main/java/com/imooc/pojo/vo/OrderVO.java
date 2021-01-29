package com.imooc.pojo.vo;

import lombok.Data;

/**
 * @author gengbin
 * @date 2021/1/27
 */
@Data
public class OrderVO {
    private String orderId;
    private MerchantOrdersVO merchantOrdersVO;
}

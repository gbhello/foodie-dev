package com.imooc.pojo.vo;

import lombok.Data;

/**
 * @author gengbin
 * @date 2021/1/26
 */
@Data
public class ShopCartVO {
    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId;
    private String specName;
    private String priceDiscount;
    private String priceNormal;
}

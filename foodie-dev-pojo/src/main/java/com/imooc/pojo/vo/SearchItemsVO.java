package com.imooc.pojo.vo;

import lombok.Data;

/**
 * @author gengbin
 * @date 2021/1/26
 */
@Data
public class SearchItemsVO {
    private String itemId;
    private String itemName;
    private Integer sellCounts;
    private String imgUrl;
    private Integer price;
}

package com.imooc.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * @author gengbin
 * @date 2021/1/23
 */
@Data
public class NewItemsVO {
    private Integer rootCatId;
    private String rootCatName;
    private String slogan;
    private String catImage;
    private String bgColor;

    private List<SimpleItemVO> simpleItemList;
}

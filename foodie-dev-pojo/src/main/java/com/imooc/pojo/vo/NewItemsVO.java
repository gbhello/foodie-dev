package com.imooc.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * @author gengbin
 * @date 2021/1/23
 */
@Data
public class NewItemsVO {
    private Integer rootCategoryId;
    private String rootCategoryName;
    private String slogan;
    private String categoryImage;
    private String backgroundColor;

    private List<SimpleItemVO> simpleItemVOList;
}

package com.imooc.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * @author gengbin
 * @date 2021/1/23
 */
@Data
public class CategoryVO {
    private Integer id;
    private String name;
    private String type;
    private Integer fatherId;

    private List<SubCategoryVO> subCategoryList;
}

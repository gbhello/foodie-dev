package com.imooc.pojo.vo;

import lombok.Data;

/**
 * @author gengbin
 * @date 2021/1/23
 */
@Data
public class SubCategoryVO {
    private Integer subId;
    private String subName;
    private String subType;
    private Integer subFatherId;
}

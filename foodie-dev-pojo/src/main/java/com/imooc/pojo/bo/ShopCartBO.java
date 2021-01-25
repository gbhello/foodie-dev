package com.imooc.pojo.bo;

import com.imooc.pojo.Stu;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author gengbin
 * @date 2021/1/25
 */
@Data
@ToString
public class ShopCartBO {
    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId;
    private String specName;
    private Integer buyCounts;
    private String priceDiscount;
    private String priceNormal;
    List<Stu> stuList;
}

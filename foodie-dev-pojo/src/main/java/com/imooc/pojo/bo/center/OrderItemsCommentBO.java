package com.imooc.pojo.bo.center;

import lombok.Data;

/**
 * @author gengbin
 * @date 2021/2/13
 */
@Data
public class OrderItemsCommentBO {
    private String CommentId;
    private String itemId;
    private String itemName;
    private String itemSpecId;
    private String itemSpecName;
    private Integer commentLevel;
    private String content;
}

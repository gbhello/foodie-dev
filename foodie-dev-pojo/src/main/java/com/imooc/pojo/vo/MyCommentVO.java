package com.imooc.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author gengbin
 * @date 2021/2/13
 */
@Data
public class MyCommentVO {
    private String CommentId;
    private String content;
    private Date createdTime;
    private String itemId;
    private String itemName;
    private String specName;
    private String itemImg;
}

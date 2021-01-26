package com.imooc.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author gengbin
 * @date 2021/1/26
 */
@Data
public class ItemCommentVO {
    private Integer commentLevel;
    private String content;
    private String specName;
    private Date createdTime;
    private String userFace;
    private String nickname;
}

package com.imooc.pojo.vo;

import lombok.Data;

/**
 * @author gengbin
 * @date 2021/1/24
 */
@Data
public class CommentLevelCountsVO {
private Integer totalCounts;
private Integer goodCounts;
private Integer normalCounts;
private Integer badCounts;
}

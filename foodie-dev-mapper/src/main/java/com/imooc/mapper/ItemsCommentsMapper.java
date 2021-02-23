package com.imooc.mapper;

import com.imooc.mapper.base.MyMapper;
import com.imooc.pojo.ItemsComments;
import com.imooc.pojo.vo.MyCommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @author gengbin
 * @date 2021/1/12
 */
@Mapper
public interface ItemsCommentsMapper extends MyMapper<ItemsComments> {
    /**
     * 保存评论
     *
     * @param map
     */
    void saveComments(@Param("map") HashMap<String, Object> map);

    /**
     * 查询评论
     *
     * @param map
     * @return
     */
    List<MyCommentVO> getMyComments(HashMap<String, Object> map);
}
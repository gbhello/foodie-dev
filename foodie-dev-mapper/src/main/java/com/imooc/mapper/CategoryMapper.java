package com.imooc.mapper;

import com.imooc.mapper.base.MyMapper;
import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVO;
import com.imooc.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author gengbin
 * @date 2021/1/12
 */
@Mapper
public interface CategoryMapper extends MyMapper<Category> {
    /**
     * 获取商品一级分类
     * @param rootCategoryId
     * @return
     */
    List<CategoryVO> getSubCategoryList(Integer rootCategoryId);

    /**
     * 获取每个一级分类下的最新6条商品数据
     * @param rootCategoryId
     * @return
     */
    List<NewItemsVO> getSixNewItems(Integer rootCategoryId);
}
package com.imooc.service;

import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVO;
import com.imooc.pojo.vo.NewItemsVO;

import java.util.List;

/**
 * @author gengbin
 * @date 2021/1/19
 */
public interface CategoryService {
    /**
     * 获取根目录分类列表
     *
     * @return
     */
    List<Category> getAllRootLevelCategory();

    /**
     * 获取商品一级分类
     *
     * @param rootCategoryId
     * @return
     */
    List<CategoryVO> getSubCategoryList(Integer rootCategoryId);

    /**
     * 获取每个一级分类下的最新6条商品数据
     *
     * @param rootCategoryId
     * @return
     */
    List<NewItemsVO> getSixNewItems(Integer rootCategoryId);
}

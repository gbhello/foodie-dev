package com.imooc.service;

import com.imooc.pojo.Category;

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

}

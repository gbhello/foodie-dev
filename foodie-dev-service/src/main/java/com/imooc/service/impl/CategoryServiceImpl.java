package com.imooc.service.impl;

import com.imooc.mapper.CategoryMapper;
import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVO;
import com.imooc.pojo.vo.NewItemsVO;
import com.imooc.service.CategoryService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author gengbin
 * @date 2021/1/19
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getAllRootLevelCategory() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", 1);
        List<Category> categoryList = categoryMapper.selectByExample(example);

        return categoryList;
    }

    @Override
    public List<CategoryVO> getSubCategoryList(Integer rootCategoryId) {
        List<CategoryVO> categoryVOList = categoryMapper.getSubCategoryList(rootCategoryId);
        return categoryVOList;
    }

    @Override
    public List<NewItemsVO> getSixNewItems(Integer rootCategoryId) {
        List<NewItemsVO> newItemsVOList = categoryMapper.getSixNewItems(rootCategoryId);
        return newItemsVOList;
    }
}

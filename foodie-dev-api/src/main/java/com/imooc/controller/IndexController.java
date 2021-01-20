package com.imooc.controller;

import com.imooc.consts.YesOrNo;
import com.imooc.pojo.Carousel;
import com.imooc.pojo.Category;
import com.imooc.service.CategoryService;
import com.imooc.service.impl.CarouselService;
import com.imooc.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 首页
 *
 * @author gengbin
 * @date 2021/1/19
 */
@Api(value = "首页", tags = "首页展示的相关接口")
@RestController
public class IndexController {
    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取首页轮播图列表", notes = "获取首页轮播图列表", httpMethod = "GET")
    @GetMapping("/getCarouselList")
    public CommonResult getCarouselList() {
        List<Carousel> carouselList = carouselService.queryAll(YesOrNo.YES.type);
        return CommonResult.ok(carouselList);
    }

    /**
     * 首页分类展示需求：
     * 1.第一次刷新主页查询大分类，渲染展示到首页
     * 2.如果鼠标上移到大分类，则加载其子分类的内容，如果已经存在子分类，则不需要加载
     *
     * @return
     */
    @ApiOperation(value = "获取商品分类（一级分类）", notes = "获取商品分类（一级分类）", httpMethod = "GET")
    @GetMapping("/getCategoryList")
    public CommonResult getCategoryList() {
        List<Category> categoryList = categoryService.getAllRootLevelCategory();
        return CommonResult.ok(categoryList);
    }
}

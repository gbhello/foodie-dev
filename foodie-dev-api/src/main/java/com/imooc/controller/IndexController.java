package com.imooc.controller;

import com.imooc.consts.YesOrNo;
import com.imooc.pojo.Carousel;
import com.imooc.service.CategoryService;
import com.imooc.service.impl.CarouselService;
import com.imooc.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    // public CommonResult getCategoryList(){
    //     categoryService.getAllRootLevelCategory();
    // }


    public static void main(String[] args) {

        Integer[] arrA = {2, 3, 5};
        Integer[] arrB = {1, 2, 3, 4};
        List<Integer> listA = Arrays.asList(arrA);
        List<Integer> listB = Arrays.asList(arrB);

        List<Integer> collect = listA.stream().filter(item -> !listB.contains(item)).collect(Collectors.toList());
        System.out.println(collect);
    }
}

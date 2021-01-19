package com.imooc.service.impl;

import com.imooc.pojo.Carousel;

import java.util.List;

/**
 * @author gengbin
 * @date 2021/1/19
 */
public interface CarouselService {
    /**
     * 查询所有轮播图
     *
     * @param isShow
     * @return
     */
    List<Carousel> queryAll(Integer isShow);
}

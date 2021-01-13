package com.imooc.controller;

import com.imooc.mapper.StuMapper;
import com.imooc.pojo.Stu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author gengbin
 * @date 2021/1/12
 */
@RestController
public class HelloController {
    @Autowired
    private StuMapper stuMapper;

    @RequestMapping("/getStuList")
    public List<Stu> hello() {
        List<Stu> stus = stuMapper.selectAll();
        return stus;
    }
}

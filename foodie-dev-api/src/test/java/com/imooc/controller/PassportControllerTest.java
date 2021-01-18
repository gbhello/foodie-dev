package com.imooc.controller;

import com.imooc.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author gengbin
 * @date 2021/1/14
 */
@SpringBootTest(classes = Application.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class PassportControllerTest {

    @Autowired
    private PassportController passportController;

    @Test
    public void usernameIsExist() {
        String username = "admin";
        passportController.usernameIsExist(username);
    }
}
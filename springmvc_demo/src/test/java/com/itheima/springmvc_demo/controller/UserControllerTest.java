package com.itheima.springmvc_demo.controller;

import com.itheima.springmvc_demo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @Description
 * @Author 付志东
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/5/22
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    public void testGet() {
        System.out.println("before mock:" + userService.getType());

    }

}

package com.fuvidy.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description
 * @Author 付志东
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/7/17
 */
@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello() {
        return "hello";
    }
}

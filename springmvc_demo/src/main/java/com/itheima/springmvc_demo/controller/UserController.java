package com.itheima.springmvc_demo.controller;

import com.itheima.springmvc_demo.entity.vo.UserVo;
import com.itheima.springmvc_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fuzhidong
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    /**
     * @Description
     * @Return
     * @Throw
     * @Since 2021/5/22
     */
    @GetMapping("")
    public List<UserVo> list() {
        //查询列表
        ArrayList<UserVo> result = new ArrayList<>();
        result.add(new UserVo().setId(1).setName("老王"));
        result.add(new UserVo().setId(1).setName("老李"));
        result.add(new UserVo().setId(1).setName("老付"));
        return result;
    }
    /**
     * @Description 获取指定用户编号的用户
     * @Return
     * @Since 2021/5/22
     */

    @GetMapping("{/id}")
    public UserVo getUser(@PathVariable("id") Integer id ){
        return new UserVo().setId(id).setName("username"+id);
    }
    @PostMapping("")
    public Integer addUser(UserVo user){
        return 1;
    }

    @GetMapping("/v2/{id}")
    public String get(){
        return userService.getType();
    }









}

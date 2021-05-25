package com.itheima.springmvc_demo.entity.vo;

/**
 * @Description
 * @Author 付志东
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/5/22
 */
public class UserVo {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public UserVo setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserVo setName(String name) {
        this.name = name;
        return this;

    }

    public UserVo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserVo() {
    }
}

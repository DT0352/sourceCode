package com.fuvidy.mallachieve.tiny.mbg.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author 付志东
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/7/11
 */
@Configuration
@MapperScan("com.fuvidy.mallachieve.tiny.mbg.mapper")
public class MyBatisConfig {
}

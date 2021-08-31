package com.fuvidy.rabbitmqdemo.meggage;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author 付志东
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/8/31
 */
@Data
public class DemoMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String QUEUE = "queue01";
    public static final String EXCHANGE = "EXCHANGE01";
    public static final String ROUTING_KEY = "ROUTING_KEY01";

    private Integer id;


}

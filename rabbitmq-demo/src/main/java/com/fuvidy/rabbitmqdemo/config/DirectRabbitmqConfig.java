package com.fuvidy.rabbitmqdemo.config;

import com.fuvidy.rabbitmqdemo.meggage.DemoMessage;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Description
 * @Author 付志东
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/8/31
 */
@Configuration
public class DirectRabbitmqConfig {

    @Bean
    public Queue demo01Queue() {
        return new Queue(DemoMessage.QUEUE,
                // Queue 名字
                true,
                // durable: 是否持久化
                false,
                // exclusive: 是否排它
                false);
        // autoDelete: 是否自动删除
    }

    @Bean
    public DirectExchange demo01Exchange() {
        return new DirectExchange(DemoMessage.EXCHANGE,
                true,
                // durable: 是否持久化
                false);
                // exclusive: 是否排它
    }
    @Bean
    public Binding demo01Binding() {
        return BindingBuilder.bind(demo01Queue()).to(demo01Exchange()).with(DemoMessage.ROUTING_KEY);
    }


}

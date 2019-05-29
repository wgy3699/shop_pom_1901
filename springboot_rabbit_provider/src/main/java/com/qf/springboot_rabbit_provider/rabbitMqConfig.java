package com.qf.springboot_rabbit_provider;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @user lao王
 * @date 2019/5/24 21:33
 */
@Configuration
public class rabbitMqConfig {

    @Bean
    public Queue queue(){
        return  new Queue("sb_queue");
    }
}

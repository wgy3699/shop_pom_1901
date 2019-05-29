package com.qf.springboot_rabbit_consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @user lao王
 * @date 2019/5/24 21:50
 */
@Component
public class myRabbitListener {

    @RabbitListener(queues = "sb_queue")
    public void myHandler(String msg){
        System.out.println("监听到发送的对象为:"+ msg);
    }
}

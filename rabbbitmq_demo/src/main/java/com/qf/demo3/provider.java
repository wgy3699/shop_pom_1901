package com.qf.demo3;

import com.qf.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @version 1.0
 * @user lao王
 * @date 2019/5/24 17:21
 */
public class provider {

    public static void main(String[] args) throws IOException {

        //连接rabbitmq
        Connection connection = ConnectionUtil.getConnection();

        //通过连接获得通道对象，
        Channel channel = connection.createChannel();

        //创建一个交换机(参数1：交换机名称;  参数2:交换机类型)
        channel.exchangeDeclare("myexchange","fanout");

        //给队列中发送消息
        String str = "Hello RabbitMQ Fanout";
        channel.basicPublish("myexchange","",null,str.getBytes("utf-8"));

        connection.close();

        System.out.println("提供者已经完成~~~~");
    }
}

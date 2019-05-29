package com.qf.demo5;

import com.qf.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @version 1.0
 * @user lao王
 * @date 2019/5/24 20:59
 */
public class provider {

    public static void main(String[] args) throws IOException {

        //连接rabbitmq
        Connection connection = ConnectionUtil.getConnection();

        //通过连接拿到通道
        Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare("my_topic","topic");

        //声明队列
        channel.queueDeclare("myqueue1",false,false,false,null);
        channel.queueDeclare("myqueue2",false,false,false,null);

        //绑定队列和交换机
        channel.queueBind("myqueue1","my_topic","a.*");


        channel.queueBind("myqueue2","my_topic","a.#");

        //发布消息
        channel.basicPublish("my_topic","a.aaa.a",null,"hello world".getBytes("utf-8"));

        connection.close();
    }
}

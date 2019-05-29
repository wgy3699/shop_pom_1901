package com.qf.demo2;

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

        //通过通道创建一个队列
        channel.queueDeclare("myqueue",false,false,false,null);

        //给队列中发送消息
        for(int i = 0;i<10;i++){
            String msg = "hello,LW"+i;
            channel.basicPublish("","myqueue",null,msg.getBytes("utf-8"));
        }

        connection.close();

        System.out.println("提供者已经完成~~~~");
    }
}

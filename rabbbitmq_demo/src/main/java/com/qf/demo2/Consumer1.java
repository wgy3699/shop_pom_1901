package com.qf.demo2;

import com.qf.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @version 1.0
 * @user lao王
 * @date 2019/5/24 17:22
 */
public class Consumer1 {

    public static void main(String[] args) throws IOException {

        //连接rabbitmq
        Connection connection = ConnectionUtil.getConnection();

        //获得通道
        Channel channel = connection.createChannel();

        //通过通道创建一个队列
        channel.queueDeclare("myqueue",false,false,false,null);

        //监听队列
        channel.basicConsume("myqueue",true,new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                    //如果队列中有消息，就回调此方法
                    String str = new String(body,"utf-8");
                    System.out.println("接收到的队列消息为:"+str);
            }
        });

    }
}

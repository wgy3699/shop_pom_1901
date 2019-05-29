package com.qf.demo1;

import com.qf.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @version 1.0
 * @user lao王
 * @date 2019/5/24 17:22
 */
public class Consumer {

    public static void main(String[] args) throws IOException {

        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);

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

                //调用线程池处理任务
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            //如果队列中有消息，就回调此方法
                            String str = new String(body,"utf-8");
                            System.out.println("接收到的队列消息为:"+str);
                            Thread.sleep(5000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });

    }
}

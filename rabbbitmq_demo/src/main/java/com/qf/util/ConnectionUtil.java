package com.qf.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @version 1.0
 * @user lao王
 * @date 2019/5/24 17:23
 */
public class ConnectionUtil {

    private static ConnectionFactory connectionFactory;

    static{
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("47.112.140.174");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPort(5672);
    }

    public static Connection getConnection(){
        //获得rabbit连接
        Connection connection = null;
        try {
            connection = connectionFactory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        return connection;
    }
}

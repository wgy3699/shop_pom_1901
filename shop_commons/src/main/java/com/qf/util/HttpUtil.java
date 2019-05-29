package com.qf.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @version 1.0
 * @user lao王
 * @date 2019/5/23 20:49
 */
public class HttpUtil {

    /**
     * 模拟浏览器发送一个get请求
     * @param urlStr
     * @return
     */
    public static String sendGet(String urlStr){
        try {
            URL url = new URL(urlStr);

            //打开一个连接
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //进行连接的设置
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(1000 * 5);
            urlConnection.setReadTimeout(1000 * 5);

            //开始连接服务器
            urlConnection.connect();

            //获得服务响应
            InputStream inputStream = urlConnection.getInputStream();
            String result = null;

            byte[] bytes = new byte[1024 * 10];
            int len = 0;

            //准备一个内存输出流
            ByteArrayOutputStream ops = new ByteArrayOutputStream();

            while ((len = inputStream.read(bytes)) != -1){
                ops.write(bytes,0,len);
            }

            //将内存中的数据转换成String字符串
            result = new String(ops.toByteArray());

            //关流
            inputStream.close();
            ops.close();

            //返回字符串结果
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

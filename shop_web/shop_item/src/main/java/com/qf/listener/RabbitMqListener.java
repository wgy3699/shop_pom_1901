package com.qf.listener;

import com.qf.controller.ItemController;
import com.qf.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @user lao王
 * @date 2019/5/25 11:14
 */
@Component
public class RabbitMqListener {

    @Autowired
    private Configuration configuration;

    @RabbitListener(queues = "item_queue")
    public void goodsMsgHander(Goods goods){
        //静态页面的输出路径--（输出的页面必须能让外界访问）
        String path = ItemController.class.getResource("/static/html/").getPath() + goods.getId() + ".html";

        try(
                Writer out = new FileWriter(path)
        ) {
            //获得商品详情页的模板
            Template template = configuration.getTemplate("goods.ftl");

            //准备一个map集合装商品的属性值
            Map map = new HashMap();

            //获得商品的各个属性,并装入集合中
            String[] images = goods.getGimages().split("\\|");

            map.put("goods",goods);
            map.put("images",images);

            //生成静态页
            template.process(map,out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

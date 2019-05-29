package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @user lao王
 * @date 2019/5/23 17:12
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private Configuration configuration;

    @Reference
    private IGoodsService goodsService;

    /**
     * 将指定的商品详情生成一个静态页面
     * @param gid    商品id
     * @return
     */
    @RequestMapping("/createHtml")
    public String createHtml(Integer gid){

        //静态页面的输出路径--（输出的页面必须能让外界访问）
        String path = ItemController.class.getResource("/static/html").getPath() + gid + ".html";

        try(
                Writer out = new FileWriter(path);
        ) {
            //获得商品详情页的模板
            Template template = configuration.getTemplate("goods.ftl");

            //获得商品的对应数据
            Goods goods = goodsService.queryById(gid);

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
        return "success";
    }

}

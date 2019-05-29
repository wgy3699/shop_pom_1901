package com.qf.listener;

import com.qf.entity.Goods;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @version 1.0
 * @user lao王
 * @date 2019/5/25 11:08
 */
@Component
public class rabbitMqListener {

    @Autowired
    private SolrClient solrClient;

    @RabbitListener(queues = "goods_queue")
    public void goodsHander(Goods goods){
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id",goods.getId() + "");
        document.addField("gname",goods.getGname());
        document.addField("ginfo",goods.getGinfo());
        document.addField("gprice",goods.getGprice().floatValue());
        document.addField("gsave",goods.getGsave());
        document.addField("gimages",goods.getGimages());

        try {
            solrClient.add(document);

            //提交
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

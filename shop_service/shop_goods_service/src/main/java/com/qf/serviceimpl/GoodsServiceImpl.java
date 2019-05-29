package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.GoodsMapper;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import com.qf.service.ISearchService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @version 1.0
 * @user lao王
 * @date 2019/5/20 20:21
 */
@Service
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Reference
    private ISearchService searchService;

    @Override
    public List<Goods> queryList() {
        List<Goods> goods = goodsMapper.selectList(null);
        return goods;
    }

    @Override
    public int addGoods(Goods goods) {

        //保存商品到数据库
        goodsMapper.insert(goods);

        //将商品信息放入队列中，进行异步化处理
        rabbitTemplate.convertAndSend("goods_exchange","",goods);



//        //将商品到索引库中
//        searchService.addGoods(goods);
//
//        //通过http通知详情工程生成静态页面
//        HttpUtil.sendGet("47.112.140.174:8083/item/createHtml?gid="+goods.getId());

        return 1;
    }

    @Override
    public Goods queryById(Integer id) {

        return goodsMapper.selectById(id);
    }

    @Override
    public int update(Goods goods) {

        return goodsMapper.updateById(goods);
    }

    @Override
    public int delete(Integer id) {

        return goodsMapper.deleteById(id);
    }
}

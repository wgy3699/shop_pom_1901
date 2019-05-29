package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;

public interface IGoodsService {

    List<Goods> queryList();

    int addGoods(Goods goods);

    Goods queryById( Integer id);

    int update(Goods goods);

    int delete(Integer id);
}

package com.qf.service;

import com.qf.entity.Goods;
import com.qf.entity.Page;

public interface ISearchService {

    Page<Goods> queryByKeyWord(Page<Goods> page,String keyWord);

    int addGoods(Goods goods);
}

package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.entity.Page;
import com.qf.service.ISearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @version 1.0
 * @user lao王
 * @date 2019/5/21 20:35
 */
@Controller
@RequestMapping("/search")
public class SearchController {

    @Reference
    private ISearchService searchService;

    @Value("${img.server}")
    private String imgPath;

    @RequestMapping("/searchByKeyWord")
    public String searchByKey(Page<Goods> page,String keyWord, Model model){

        //通过关键字进行搜索
        Page<Goods> goodsPage = searchService.queryByKeyWord(page, keyWord);
        goodsPage.setUrl("/search/searchByKey?");

        model.addAttribute("goodsPage",goodsPage);
        model.addAttribute("imgPath",imgPath);

        return "searchList";
    }
}

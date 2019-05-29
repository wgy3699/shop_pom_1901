package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @version 1.0
 * @user lao王
 * @date 2019/5/20 20:43
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private IGoodsService goodsService;

    @Value("${img.server}")
    private String imgPath;

    @RequestMapping("/list")
    public String goodsList(Model model){
        List<Goods> goods = goodsService.queryList();

        model.addAttribute("goods",goods);

        //将图片地址传回前端界面
        model.addAttribute("imgPath",imgPath);

        return "goodsList";
    }


    /**
     * 添加商品
     * @param goods
     * @return
     */
    @RequestMapping("/add")
    public String goodsAdd(Goods goods,String[] imagesList){

        String gimages = "";
        for (String s : imagesList) {
            if(gimages.length() > 0){
                gimages += "|";
            }
            gimages += s ;
        }
        goods.setGimages(gimages);

        //调用服务层添加商品
        goodsService.addGoods(goods);

        return "redirect:/goods/list";
    }

    /**
     * 根据商品id查询商品信息
     * @param id
     * @return
     */
    @RequestMapping("/queryById/{id}")
    public String queryById(@PathVariable("id") Integer id,Model model){
        Goods goods = goodsService.queryById(id);

        String[] split = goods.getGimages().split("\\|");

        model.addAttribute("goods",goods);

        model.addAttribute("imgPath",imgPath);

        model.addAttribute("split",split);

        return "updateGoods";
    }


    /**
     * 修改商品信息
     * @param goods
     * @param imagesList
     * @return
     */
    @RequestMapping("/update")
    public String update(Goods goods,String[] imagesList){
        String gimages = "";
        for (String s : imagesList) {
            if(gimages.length() > 0){
                gimages += "|";
            }
            gimages += s ;
        }
        goods.setGimages(gimages);

        //调用服务修改商品信息
       goodsService.update(goods);

       return "redirect:/goods/list";
    }

    /**
     * 删除商品信息
     * @param id
     * @return
     */
    @RequestMapping("/deleteById/{id}")
    public String deleteById(@PathVariable("id") Integer id){
        goodsService.delete(id);
        return "redirect:../list";
    }

}

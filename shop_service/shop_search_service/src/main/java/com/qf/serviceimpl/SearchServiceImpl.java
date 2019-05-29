package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.entity.Goods;
import com.qf.entity.Page;
import com.qf.service.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @user lao王
 * @date 2019/5/22 16:27
 */
@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private SolrClient solrClient;


    /**5
     * 根据关键字进行搜索，并跳转到搜索结果展示界面
     * @param keyWord
     * @return
     */
    @Override
    public Page<Goods> queryByKeyWord(Page<Goods> page,String keyWord) {

        SolrQuery solrQuery;

        //判断查询的关键字，并组合查询的条件
        if(keyWord == null || keyWord.trim().equals("")){
            solrQuery = new SolrQuery("*:*");
        }else{
            solrQuery = new SolrQuery("gname:" + keyWord + "|| ginfo:" + keyWord);
        }


        //设置高亮
        solrQuery.setHighlight(true);
        solrQuery.setHighlightSimplePre("<font color='red'>");        //设置前缀
        solrQuery.setHighlightSimplePost("</font>");     //设置后缀
        solrQuery.addHighlightField("gname");   //设置高亮的字段

        //获取分页参数
        int pageSize = page.getPageSize();
        int currentPage = page.getCurrentPage();
        int totalCount = 0;
        int totalPage = 0;

        //设置分页
        solrQuery.setStart((currentPage-1)*pageSize);  //limit分页的第一个参数
        solrQuery.setRows(pageSize);


        //创建集合存储传出来的对象
        List<Goods> goodsList = new ArrayList<>();


        try {
            QueryResponse query = solrClient.query(solrQuery);

            //通过QueryResponse对象获取普通的搜索结果
            SolrDocumentList results = query.getResults();

            //通过QueryResponse对象获取高亮的搜索结果
            Map<String, Map<String, List<String>>> highlighting = query.getHighlighting();

            //通过结果集获得总条数
            totalCount = (int) results.getNumFound();

            //总页数
            if(totalCount % pageSize == 0 ){
                totalPage = (int) (totalCount / pageSize);
            }else{
                totalPage = (int)((totalPage / pageSize)+1);
            }

            //遍历结果集得到对象
            for (SolrDocument result : results) {
                Goods goods = new Goods();

                //得到对象的各个属性
                goods.setId(Integer.parseInt(result.get("id") + ""));
                goods.setGname(result.get("gname") + "");
                goods.setGimages(result.get("gimages") + "");
                goods.setGsave(Integer.parseInt(result.get("gsave") + ""));
                BigDecimal decimal = BigDecimal.valueOf((float) result.get("gprice"));
                goods.setGprice(decimal);

                //判断当前的商品是否高亮
                if(highlighting.containsKey(goods.getId() + "")){
                    //当前商品存在高亮
                    Map<String, List<String>> stringListMap = highlighting.get(goods.getId() + "");

                    //获得高亮的字段
                    List<String> gname = stringListMap.get("gname");
                    goods.setGname(gname.get(0));
                }

                goodsList.add(goods);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //返回page对象
        page.setTotalCount(totalCount);
        page.setTotalPage(totalPage);
        page.setList(goodsList);
        return page;
    }


    /**
     * 将商品数据同步到索引库中
     * @param goods
     * @return
     */
    @Override
    public int addGoods(Goods goods) {
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

            return 1;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

package com.tang.jddemo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tang.jddemo.model.JDItem;
import com.tang.jddemo.service.JDItemService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Jsoup解析工具类
 */
@Component
public class JsoupUtils {

    @Autowired
    private JDItemService jdItemService;

    @Autowired
    private HttpClientUtils httpClientUtils;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public void parse(String html) {
        //解析页面
        Document dom = Jsoup.parse(html);

        //获取spu信息
        Elements spuElements = dom.select("div#J_goodsList > ul > li");
        for (Element parent : spuElements) {
            //获取spu
            long spu = Long.parseLong(spuElements.attr("data-spu"));

            //获取sku信息
            Elements skuElements = spuElements.select("li.ps-item");
            for (Element son : skuElements) {
                //获取sku
                long sku = Long.parseLong(skuElements.select("[data-sku]").attr("data-sku"));

                //根据sku查询商品数据
                JDItem jdItem = new JDItem();
                jdItem.setSku(sku);
                List<JDItem> jdItems = jdItemService.selectBy(jdItem);

                if (jdItems.size() > 0) {
                    //如果商品存在，就进行下一个循环，改商品不保存，因为已经存在
                    continue;
                }

                //设置商品的spu
                jdItem.setSpu(spu);

                //获取商品的详情的url
                String itemUrl = "https://item.jd.com/" + sku + ".html";
                jdItem.setUrl(itemUrl);

                //保存图片
                String imgUrl = "https:" + skuElements.select("img[data-sku]").first().attr("data-lazy-img");
                imgUrl = imgUrl.replace("/n9/", "/n1/");
                //下载图片
                String imgName = httpClientUtils.downloadImg(imgUrl);
                jdItem.setPic(imgName);

                //保存价格
                String priceJson = httpClientUtils.doGetHtml("https://p.3.cn/prices/mgets?skuIds=" + sku);
                try {
                    long price = MAPPER.readTree(priceJson).get(0).get("p").asLong();
                    jdItem.setPrice(price);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                //设置日期
                jdItem.setCreated(new Date());

                //设置更新时间
                jdItem.setUpdated(jdItem.getCreated());

                //设置商品的标题
                String itemInfo = httpClientUtils.doGetHtml(jdItem.getUrl());
                String title = Jsoup.parse(itemInfo).select("div.sku-name").text();
                jdItem.setTitle(title);

                //保存商品数据到数据量中
                int i = jdItemService.addItem(jdItem);
                if (i > 0) {
                    System.out.println("保存成功");
                }
            }
        }
    }
}

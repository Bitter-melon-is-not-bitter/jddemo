package com.tang.jddemo.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tang.jddemo.model.JDItem;
import com.tang.jddemo.service.JDItemService;
import com.tang.jddemo.util.HttpClientUtils;
import com.tang.jddemo.util.JsoupUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 任务执行类
 */
@Component
public class JDItemTask {

    @Autowired
    private HttpClientUtils httpClientUtils;

    @Autowired
    private JDItemService jdItemService;

    @Autowired
    private JsoupUtils jsoupUtils;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    //当下载任务完成后，间隔一秒进行下一次的任务
    @Scheduled(fixedDelay = 1000)
    public void downDataTask() {
        //什么初始化方法
        String url = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=" +
                "utf-8&qrst=1&rt=1&stop=1&vt=2&cid2=653&cid3=655&s=56&click=0&page=";

        for (int i = 1; i < 100; i = i + 2) {
            String html = httpClientUtils.doGetHtml(url + i);
            //解析页面
            jsoupUtils.parse(html);
        }
    }
}

package com.tang.jddemo.service;

import com.tang.jddemo.model.JDItem;

import java.util.List;

/**
 * 爬虫接口类
 */
public interface JDItemService {
    /**
     * 数据爬取后增加到数据库
     *
     * @param jdItem
     * @return
     */
    int addItem(JDItem jdItem);

    /**
     * 查询sku在数据库是否存在
     *
     * @param jdItem
     * @return
     */
    List<JDItem> selectBy(JDItem jdItem);

}

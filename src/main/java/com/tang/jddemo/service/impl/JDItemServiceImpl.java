package com.tang.jddemo.service.impl;

import com.tang.jddemo.mapper.JDItemMapper;
import com.tang.jddemo.model.JDItem;
import com.tang.jddemo.model.JDItemExample;
import com.tang.jddemo.service.JDItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JDItemServiceImpl implements JDItemService {

    @Autowired
    private JDItemMapper jdItemMapper;

    @Override
    public int addItem(JDItem jdItem) {
        return jdItemMapper.insertSelective(jdItem);
    }

    @Override
    public List<JDItem> selectBy(JDItem jdItem) {
        JDItemExample jdItemExample = new JDItemExample();
        JDItemExample.Criteria criteria = jdItemExample.createCriteria();
        criteria.andSkuEqualTo(jdItem.getSku());
        return jdItemMapper.selectByExample(jdItemExample);
    }
}

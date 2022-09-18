package org.example.settings.service.impl;

import org.example.settings.domain.DictionaryValue;
import org.example.settings.mapper.DictionaryValueMapper;
import org.example.settings.service.DictionaryValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dictionaryValueService")
public class DictionaryValueServiceImpl implements DictionaryValueService {

    @Autowired
    private DictionaryValueMapper dictionaryValueMapper;

    //根据TypeCode查询数据字典值
    @Override
    public List<DictionaryValue> queryDicValueByTypeCode(String typeCode) {

        return dictionaryValueMapper.selectDicValueByTypeCode(typeCode);
    }
}

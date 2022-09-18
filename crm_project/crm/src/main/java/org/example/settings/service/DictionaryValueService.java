package org.example.settings.service;

import org.example.settings.domain.DictionaryValue;

import java.util.List;

public interface DictionaryValueService {

    //根据TypeCode查询数据字典值
    List<DictionaryValue> queryDicValueByTypeCode(String typeCode);
}

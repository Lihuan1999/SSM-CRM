package org.example.workbench.service;

import org.example.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueService {

    //保存创建的线索
    int saveClue(Clue clue);
    //根据条件分页查询
    List<Clue> queryClueByConditionForPage(Map<String,Object> map);
    //查询总条数
    int queryCountOfClueByConditionForPage(Map<String,Object> map);
    //根据id查询线索明细
    Clue queryClueForDetailById(String id);

    //线索转换所有处理的方法
    void saveConvertClue(Map<String,Object> map);
}

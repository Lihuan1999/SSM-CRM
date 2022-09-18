package org.example.workbench.service;

import org.example.workbench.domain.ClueActivityRelation;

import java.util.List;
import java.util.Map;

public interface ClueActivityRelationService {

    /**
     * 批量保存创建的线索和市场活动的关联关系
     */
    int saveClueActivityRelationByList(List<ClueActivityRelation> list);
    /**
     * 解除线索关联的市场活动
     */
    int removeClueActivityRelationByActivityIdAndClueId(ClueActivityRelation clueActivityRelation);
}

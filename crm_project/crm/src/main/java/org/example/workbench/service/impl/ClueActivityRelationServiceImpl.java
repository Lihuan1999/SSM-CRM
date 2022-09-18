package org.example.workbench.service.impl;

import org.example.workbench.domain.ClueActivityRelation;
import org.example.workbench.mapper.ClueActivityRelationMapper;
import org.example.workbench.service.ClueActivityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("clueActivityRelationService")
public class ClueActivityRelationServiceImpl implements ClueActivityRelationService {

    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;

    @Override
    public int saveClueActivityRelationByList(List<ClueActivityRelation> list) {

        return clueActivityRelationMapper.insertClueActivityRelationByList(list);
    }

    @Override
    public int removeClueActivityRelationByActivityIdAndClueId(ClueActivityRelation clueActivityRelation) {

        return clueActivityRelationMapper.deleteClueActivityRelationByActivityIdAndClueId(clueActivityRelation);
    }
}

package org.example.workbench.service.impl;

import org.example.workbench.domain.ClueRemark;
import org.example.workbench.mapper.ClueRemarkMapper;
import org.example.workbench.service.ClueRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("clueRemarkService")
public class ClueRemarkServiceImpl implements ClueRemarkService {

    @Autowired
    private ClueRemarkMapper clueRemarkMapper;

    @Override
    public List<ClueRemark> queryClueRemarkForDetailById(String clueId) {

        return clueRemarkMapper.selectClueRemarkForDetailById(clueId);
    }
}

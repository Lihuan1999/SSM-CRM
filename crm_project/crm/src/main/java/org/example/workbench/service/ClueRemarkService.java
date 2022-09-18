package org.example.workbench.service;

import org.example.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkService {

    //根据clueId查询线索明细备注
    List<ClueRemark> queryClueRemarkForDetailById(String clueId);

}

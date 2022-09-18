package org.example.workbench.service.impl;

import org.example.workbench.domain.ActivityRemark;
import org.example.workbench.mapper.ActivityRemarkMapper;
import org.example.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("activityRemarkService")
public class ActivityRemarkServiceImpl implements ActivityRemarkService{

    @Autowired
    private ActivityRemarkMapper activityRemarkMapper;

    //根据activityId，查询该市场活动下的所有备注信息
    @Override
    public List<ActivityRemark> queryActivityRemarkForDetailByActivityId(String activityId) {

        return activityRemarkMapper.selectActivityRemarkForDetailByActivityId(activityId);
    }

    @Override
    public int saveCreateActivityRemark(ActivityRemark activityRemark) {

        return activityRemarkMapper.insertActivityRemark(activityRemark);
    }
    //根据id删除市场活动备注信息

    @Override
    public int deleteActivityRemarkById(String id) {

        return activityRemarkMapper.deleteActivityRemarkById(id);
    }
    //保存修改的市场活动备注

    @Override
    public int saveEditActivityRemark(ActivityRemark activityRemark) {

        return activityRemarkMapper.updateActivityRemark(activityRemark);
    }
}

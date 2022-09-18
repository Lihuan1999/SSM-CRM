package org.example.workbench.service;

import org.example.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkService {

    //根据activityId，查询该市场活动下的所有备注信息
    List<ActivityRemark> queryActivityRemarkForDetailByActivityId(String activityId);
    //保存添加市场活动备注信息
    int saveCreateActivityRemark(ActivityRemark activityRemark);
    //根据id删除市场活动备注信息
    int deleteActivityRemarkById(String id);
    //保存修改的市场活动备注
    int saveEditActivityRemark(ActivityRemark activityRemark);
}

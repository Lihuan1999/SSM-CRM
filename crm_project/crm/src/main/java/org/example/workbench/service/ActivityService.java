package org.example.workbench.service;

import org.example.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {

    //保存创建的市场活动
    int saveCreateActivity(Activity activity);
    //根据条件分页查询市场活动
    List<Activity> queryActivityByConditionForPage(Map<String,Object> map);
    //根据条件查询市场活动的总条数
    int queryCountOfActivityByCondition(Map<String,Object> map);
    //根据id 删除市场活动
    int deleteActivityByIds(String[] ids);
    //根据id查询市场活动
    Activity queryActivityById(String id);
    //更新数据
    int saveEditActivity(Activity activity);
    //查询所有市场活动，文件下载
    List<Activity> queryAllActivities();
    //根据id，选择导出市场活动
    List<Activity> queryOptionActivityById(String[] ids);
    //导入市场活动
    int saveCreateActivityByList(List<Activity> activityList);
    //根据id查询市场活动明细
    Activity queryActivityForDetailById(String id);
    //根据clueId查询关联的市场活动明细
    List<Activity> queryActivityForDetailByClueId(String clueId);
    //根据clueId和activityName查询市场活动 //关联市场活动页面
    List<Activity> queryActivityForDetailNyActivityNameAndClueId(Map<String,Object> map);

    //根据clueId和activityId查询市场活动   刷新线索关联市场活动后的部分    ids=activityId+clueId
    List<Activity> queryActivityForDetailByIds(String[] ids);

    //根据clueId和activityName查询市场活动
    //关联市场活动页面
    //只显示已经关联过的
    List<Activity> queryActivityForConvertNyActivityNameAndClueId(Map<String,Object> map);
}

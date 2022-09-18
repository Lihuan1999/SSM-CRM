package org.example.workbench.service.impl;

import org.example.workbench.domain.Activity;
import org.example.workbench.mapper.ActivityMapper;
import org.example.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;
    //保存创建的市场活动
    @Override
    public int saveCreateActivity(Activity activity) {

        return activityMapper.insertActivity(activity);
    }
    @Override
    public List<Activity> queryActivityByConditionForPage(Map<String, Object> map) {

        return activityMapper.selectActivityByConditionForPage(map);
    }

    @Override
    public int queryCountOfActivityByCondition(Map<String, Object> map) {

        return activityMapper.selectCountOfActivityByCondition(map);
    }
    //根据id删除市场活动
    @Override
    public int deleteActivityByIds(String[] ids) {

        return activityMapper.deleteActivityByIds(ids);
    }
    //根据id查询市场活动

    @Override
    public Activity queryActivityById(String id) {

        return activityMapper.selectActivityById(id);
    }
    //更新数据

    @Override
    public int saveEditActivity(Activity activity) {

        return activityMapper.updateActivity(activity);
    }
    //查询所有市场活动，文件下载
    @Override
    public List<Activity> queryAllActivities() {

        return activityMapper.selectAllActivities();
    }
    //选择导出市场活动
    @Override
    public List<Activity> queryOptionActivityById(String[] ids) {

        return activityMapper.selectOptionActivityById(ids);
    }
    //导入市场活动
    @Override
    public int saveCreateActivityByList(List<Activity> activityList) {

        return activityMapper.insertActivityByList(activityList);
    }
    //根据id查询市场活动明细
    @Override
    public Activity queryActivityForDetailById(String id) {

        return activityMapper.selectActivityForDetailById(id);
    }
    // 根据clueId查询关联的市场活动明细

    @Override
    public List<Activity> queryActivityForDetailByClueId(String clueId) {

        return activityMapper.selectActivityForDetailByClueId(clueId);
    }
    //根据clueId和activityName查询市场活动 //关联市场活动页面

    @Override
    public List<Activity> queryActivityForDetailNyActivityNameAndClueId(Map<String, Object> map) {

        return activityMapper.selectActivityForDetailNyActivityNameAndClueId(map);
    }
    /**
     * 根据clueId和activityId查询市场活动
     * 刷新线索关联市场活动后的部分
     * ids=activityId+clueId
     */
    @Override
    public List<Activity> queryActivityForDetailByIds(String[] ids) {

        return activityMapper.selectActivityForDetailByIds(ids);
    }
    /**
     *  根据clueId和activityName查询市场活动
     *  关联市场活动页面
     *  只显示已经关联过的
     */
    @Override
    public List<Activity> queryActivityForConvertNyActivityNameAndClueId(Map<String, Object> map) {

        return activityMapper.selectActivityForConvertNyActivityNameAndClueId(map);
    }
}

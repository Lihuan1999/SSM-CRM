package org.example.workbench.mapper;

import org.example.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityMapper {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Tue May 17 10:04:55 CST 2022
     */
    int insertSelective(Activity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Tue May 17 10:04:55 CST 2022
     */
    int updateByPrimaryKeySelective(Activity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Tue May 17 10:04:55 CST 2022
     */
    int updateByPrimaryKey(Activity record);
    /**
     * *************************************************************************************************
     */

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     * 保存创建的市场活动
     * @mbggenerated Tue May 17 10:04:55 CST 2022
     */
    int insertActivity(Activity activity);

    /**
     *  根据条件分页查询市场活动列表
     */
    List<Activity> selectActivityByConditionForPage(Map<String,Object> map);
    //根据条件查询市场活动的总条数
    int selectCountOfActivityByCondition(Map<String,Object> map);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Tue May 17 10:04:55 CST 2022
     */
    //根据id 删除市场活动
    int deleteActivityByIds(String[] ids);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * 根据id查询市场活动
     */
    Activity selectActivityById(String id);

    /**
     * 保存更新数据
     */
    int updateActivity(Activity activity);
    /**
     * 查询所有的市场活动，批量导出（文件下载）
     */
    List<Activity> selectAllActivities();

    /**
     * 根据id选择导出市场活动
     */
    List<Activity> selectOptionActivityById(String[] ids);
    /**
     * 导入市场活动
     */
    int insertActivityByList(List<Activity> activityList);
    /**
     *  根据id查询市场活动明细
     */
    Activity selectActivityForDetailById(String id);
    /**
     * 根据clueId查询关联的市场活动明细
     */
    List<Activity> selectActivityForDetailByClueId(String clueId);
    /**
     *  根据clueId和activityName查询市场活动
     *  关联市场活动页面
     *  关联过的不在显示
     */
    List<Activity> selectActivityForDetailNyActivityNameAndClueId(Map<String,Object> map);
    /**
     * 根据clueId和activityId查询市场活动
     * 刷新线索关联市场活动后的部分
     * ids=activityId+clueId
     */
    List<Activity> selectActivityForDetailByIds(String[] ids);
    /**
     *  根据clueId和activityName查询市场活动
     *  关联市场活动页面
     *  只显示已经关联过的
     */
    List<Activity> selectActivityForConvertNyActivityNameAndClueId(Map<String,Object> map);
}
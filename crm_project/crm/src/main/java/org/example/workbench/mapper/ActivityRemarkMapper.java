package org.example.workbench.mapper;

import org.example.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Sun May 22 17:34:09 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Sun May 22 17:34:09 CST 2022
     */
    int insert(ActivityRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Sun May 22 17:34:09 CST 2022
     */
    int insertSelective(ActivityRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Sun May 22 17:34:09 CST 2022
     */
    ActivityRemark selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Sun May 22 17:34:09 CST 2022
     */
    int updateByPrimaryKeySelective(ActivityRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Sun May 22 17:34:09 CST 2022
     */
    int updateByPrimaryKey(ActivityRemark record);

    /**
     * --------------------------------------------------------------------------------------------------------
     */
    //根据activityId，查询该市场活动下的所有备注信息
    List<ActivityRemark> selectActivityRemarkForDetailByActivityId(String activityId);

    //保存添加市场活动备注信息
    int insertActivityRemark(ActivityRemark activityRemark);

    //根据id删除市场活动备注,单个id删除
    int deleteActivityRemarkById(String id);

    //保存修改的市场活动备注
    int updateActivityRemark(ActivityRemark activityRemark);
}
package org.example.workbench.web.controller;

import org.example.commons.contants.ContansDemo;
import org.example.commons.domain.ReturnObject;
import org.example.commons.utils.DateUtils;
import org.example.commons.utils.UUIDUtils;
import org.example.settings.domain.User;
import org.example.workbench.domain.ActivityRemark;
import org.example.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class ActivityRemarkController {

    @Autowired
    private ActivityRemarkService activityRemarkService;

    /**
     * ************************************************ 保存创建的市场活动备注 ********************************************
     */
    @RequestMapping("/workbench/activity/saveCreateActivityRemark.do")
    public @ResponseBody Object saveCreateActivityRemark(ActivityRemark activityRemark, HttpSession session){
        User user = (User) session.getAttribute(ContansDemo.SESSION_USER);
        //封装参数
        activityRemark.setId(UUIDUtils.getUUID());
        activityRemark.setCreateTime(DateUtils.formateDateTime(new Date()));
        activityRemark.setCreateBy(user.getId());
        activityRemark.setEditFlag(ContansDemo.REMARK_EDIT_FLAG_NO_EDITED);
        ReturnObject returnObject = new ReturnObject();
        try {
            //调用ActivityRemarkService层方法
            int ret = activityRemarkService.saveCreateActivityRemark(activityRemark);
            if (ret>0){
                returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setRetData(activityRemark);
            }else {
                returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_ERROR);
                returnObject.setMessage("系统忙。请稍后重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_ERROR);
            returnObject.setMessage("系统忙。请稍后重试");
        }
        return returnObject;
    }

    /**
     * ************************************************ 根据id删除市场活动 ***********************************************
     */
    @RequestMapping("/workbench/activity/deleteActivityRemarkById.do")
    public @ResponseBody Object deleteActivityRemarkById(String id){
        ReturnObject returnObject = new ReturnObject();
        try {
            //调用ActivityRemarkService层方法
            int ret = activityRemarkService.deleteActivityRemarkById(id);
            if (ret>0){
                returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_ERROR);
                returnObject.setMessage("系统忙，请稍后重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_ERROR);
            returnObject.setMessage("系统忙，请稍后重试");
        }
        return returnObject;
    }
    /**
     * ********************************************** 保存修改的市场活动备注 **********************************************
     */
    @RequestMapping("/workbench/activity/saveEditActivityRemark.do")
    public @ResponseBody Object saveEditActivityRemark(ActivityRemark activityRemark,HttpSession session){
        User user = (User) session.getAttribute(ContansDemo.SESSION_USER);
        ReturnObject returnObject = new ReturnObject();

        activityRemark.setEditTime(DateUtils.formateDateTime(new Date()));
        activityRemark.setEditBy(user.getId());
        activityRemark.setEditFlag(ContansDemo.REMARK_EDIT_FLAG_YES_EDITED);

        try {
            //调用ActivityRemarkService层方法
            int ret = activityRemarkService.saveEditActivityRemark(activityRemark);
            if (ret>0){
                returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setRetData(activityRemark);
            }else{
                returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_ERROR);
                returnObject.setMessage("系统忙，请稍后重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_ERROR);
            returnObject.setMessage("系统忙，请稍后重试");
        }
        return returnObject;
    }
}

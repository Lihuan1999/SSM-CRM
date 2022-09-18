package org.example.workbench.web.controller;

import org.example.commons.contants.ContansDemo;
import org.example.commons.domain.ReturnObject;
import org.example.commons.utils.DateUtils;
import org.example.commons.utils.UUIDUtils;
import org.example.settings.domain.DictionaryValue;
import org.example.settings.domain.User;
import org.example.settings.service.DictionaryValueService;
import org.example.settings.service.UserService;
import org.example.workbench.domain.Activity;
import org.example.workbench.domain.Clue;
import org.example.workbench.domain.ClueActivityRelation;
import org.example.workbench.domain.ClueRemark;
import org.example.workbench.service.ActivityService;
import org.example.workbench.service.ClueActivityRelationService;
import org.example.workbench.service.ClueRemarkService;
import org.example.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class ClueController {

    @Autowired
    private UserService userService;
    @Autowired
    private DictionaryValueService dictionaryValueService;
    @Autowired
    private ClueService clueService;
    @Autowired
    private ClueRemarkService clueRemarkService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ClueActivityRelationService clueActivityRelationService;

    /**
     * ******************************************* 根据TypeCode查询数据字典值 ********************************************
     */
    @RequestMapping("/workbench/clue/index.do")
    public String index(HttpServletRequest request){
        //查询所有用户
        List<User> userList = userService.queryAllUser();
        //查询称呼
        List<DictionaryValue> appellationList = dictionaryValueService.queryDicValueByTypeCode("appellation");
        //查询线索状态
        List<DictionaryValue> clueStateList = dictionaryValueService.queryDicValueByTypeCode("clueState");
        //查询线索来源
        List<DictionaryValue> sourceList = dictionaryValueService.queryDicValueByTypeCode("source");

        //把数据放到request中
        request.setAttribute("userList",userList);
        request.setAttribute("appellationList",appellationList);
        request.setAttribute("clueStateList",clueStateList);
        request.setAttribute("sourceList",sourceList);

        //请求转发
        return "workbench/clue/index";
    }
    /**
     * ************************************************ 保存创建的线索 **************************************************
     */
    @RequestMapping("/workbench/clue/saveCreateClue.do")
    public @ResponseBody Object saveCreateClue(Clue clue,HttpSession session){
        User user = (User) session.getAttribute(ContansDemo.SESSION_USER);
        //封装参数
        clue.setId(UUIDUtils.getUUID());
        clue.setCreateTime(DateUtils.formateDateTime(new Date()));
        clue.setCreateBy(user.getId());

        ReturnObject returnObject = new ReturnObject();
        try {
            //调用Service
            int ret = clueService.saveClue(clue);
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
     * *********************************************** 分页查询，总条数 **************************************************
     */
    @RequestMapping("/workbench/clue/queryClueByConditionForPage.do")
    public @ResponseBody Object queryClueByConditionForPage(String fullname,String company,String phone,
                         String source,String owner,String mphone,String state,int pageNo,int pageSize){
        //封装参数
        Map<String,Object> map = new HashMap<>();
        map.put("fullname",fullname);
        map.put("company",company);
        map.put("phone",phone);
        map.put("source",source);
        map.put("owner",owner);
        map.put("mphone",mphone);
        map.put("state",state);
        map.put("beginNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        //分页查询
        List<Clue> clueList = clueService.queryClueByConditionForPage(map);
        //总条数
        int totalRows = clueService.queryCountOfClueByConditionForPage(map);

        //
        Map<String,Object> retMap = new HashMap<>();
        retMap.put("clueList",clueList);
        retMap.put("totalRows",totalRows);
        return retMap;
    }
    /**
     * 根据id查询线索明细
     */
    @RequestMapping("/workbench/clue/queryClueForDetailById")
    public String queryClueForDetailById(String id,HttpServletRequest request){

        //线索明细
        Clue clue = clueService.queryClueForDetailById(id);
        //线索备注
        List<ClueRemark> remarkList = clueRemarkService.queryClueRemarkForDetailById(id);
        //根据clueId查询关联的市场活动明细
        List<Activity> activityList = activityService.queryActivityForDetailByClueId(id);

        request.setAttribute("clue",clue);
        request.setAttribute("remarkList",remarkList);
        request.setAttribute("activityList",activityList);

        return "workbench/clue/detail";
    }
    /**
     * 批量保存市场活动和线索的关联关系
     */
    @RequestMapping("/workbench/clue/saveGuanLianRelation.do")
    public @ResponseBody Object saveGuanLianRelation(String[] activityId,String clueId){
        //封装参数
        ClueActivityRelation car=null;
        List<ClueActivityRelation> relationList=new ArrayList<>();
        ReturnObject returnObject=new ReturnObject();

            for(String ai:activityId){
                car=new ClueActivityRelation();
                car.setActivityId(ai);
                car.setClueId(clueId);
                car.setId(UUIDUtils.getUUID());
                relationList.add(car);
            }
        try {
            //调用service方法，批量保存线索和市场活动的关联关系
            int ret = clueActivityRelationService.saveClueActivityRelationByList(relationList);

            if(ret>0){
                returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_SUCCESS);

                List<Activity> activityList=activityService.queryActivityForDetailByIds(activityId);
                returnObject.setRetData(activityList);
            }else{
                returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_ERROR);
                returnObject.setMessage("系统忙，请稍后重试....");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_ERROR);
            returnObject.setMessage("系统忙，请稍后重试....");
        }

        return returnObject;
    }
    /**
     * 解除线索关联的市场活动
     */
    @RequestMapping("/workbench/clue/removeActivity.do")
    public @ResponseBody Object removeActivity(ClueActivityRelation clueActivityRelation){

        ReturnObject returnObject = new ReturnObject();
        try {
            //
            int ret = clueActivityRelationService.removeClueActivityRelationByActivityIdAndClueId(clueActivityRelation);

            if (ret > 0){
                returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_ERROR);
                returnObject.setMessage("系统繁忙，请稍后重试！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_ERROR);
            returnObject.setMessage("系统繁忙，请稍后重试！");
        }
        return returnObject;
    }
    /**
     * 点击转换按钮，进行跳转
     */
    @RequestMapping("/workbench/clue/convertClue.do")
    public String convertClue(String id,HttpServletRequest request){
        //根据clueId查询线索明细，转换
        Clue clue = clueService.queryClueForDetailById(id);
        //数据字典值
        List<DictionaryValue> stageList = dictionaryValueService.queryDicValueByTypeCode("stage");

        request.setAttribute("clue",clue);
        request.setAttribute("stageList",stageList);

        return "workbench/clue/convert";
    }
    /**
     * 根据clueId和activityName查询市场活动
     * 线索转换，活动源查询
     */
    @RequestMapping("/workbench/clue/convertClueSelectActivity.do")
    public @ResponseBody Object convertClueSelectActivity(String activityName,String clueId){
        //
        Map<String,Object> map = new HashMap<>();
        map.put("activityName",activityName);
        map.put("clueId",clueId);
        //
        List<Activity> activityList = activityService.queryActivityForConvertNyActivityNameAndClueId(map);

        return activityList;
    }

    /**
     * 处理线索转换
     */
    @RequestMapping("/workbench/clue/saveClueConvert.do")
    public @ResponseBody Object saveClueConvert(String clueId,String money,String name,String expectedDate,String stage,String activityId,String isCreateTran,HttpSession session){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("clueId",clueId);
        map.put("money",money);
        map.put("name",name);
        map.put("expectedDate",expectedDate);
        map.put("stage",stage);
        map.put("activityId",activityId);
        map.put("isCreateTran",isCreateTran);
        map.put(ContansDemo.SESSION_USER,session.getAttribute(ContansDemo.SESSION_USER));

        ReturnObject returnObject = new ReturnObject();

        try {
            //
            clueService.saveConvertClue(map);

            returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_ERROR);
            returnObject.setMessage("系统繁忙，请稍后重试");
        }
        return returnObject;
    }

}

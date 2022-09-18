package org.example.workbench.web.controller;

import com.sun.jdi.request.StepRequest;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.example.commons.contants.ContansDemo;
import org.example.commons.domain.ReturnObject;
import org.example.commons.utils.DateUtils;
import org.example.commons.utils.HSSFUtils;
import org.example.commons.utils.UUIDUtils;
import org.example.settings.domain.User;
import org.example.settings.service.UserService;
import org.example.workbench.domain.Activity;
import org.example.workbench.domain.ActivityRemark;
import org.example.workbench.service.ActivityRemarkService;
import org.example.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

/**
 * 市场活动表相关的操作
 */

@Controller
public class ActivityController {

    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ActivityRemarkService activityRemarkService;
    /**
     * ***********************************************市场活动跳转请求,携带所有的用户数据****************************
     */
    @RequestMapping("/workbench/activity/index.do")
    public String index(HttpServletRequest request){
        //调用Service方法，查询所有用户
        List<User> userList = userService.queryAllUser();
        //把数据保存到request中
        request.setAttribute("userList",userList);

        return "workbench/activity/index";
    }
    /**
     *  ************************************************ 保存创建的市场活动，***************************************
     */
    @RequestMapping("/workbench/activity/saveCreateActivity.do")
    public @ResponseBody Object saveCreateActivity(Activity activity, HttpSession session){
        User user = (User) session.getAttribute(ContansDemo.SESSION_USER);
        //封装参数
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(DateUtils.formateDateTime(new Date()));
        activity.setCreateBy(user.getId());
        ReturnObject returnObject = new ReturnObject();
        try {
            //调用Service层方法，保存创建的市场活动
            int ret = activityService.saveCreateActivity(activity);
            //
            if (ret > 0){
                returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_ERROR);
                returnObject.setMessage("系统忙，请稍后重试...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_ERROR);
            returnObject.setMessage("系统忙，请稍后重试...");
        }

        return returnObject;
    }
    /**
     *  **************************************************** 分页查询 ***********************************************
     */
    @RequestMapping("/workBench/activity/queryActivityByConditionForPage.do")
    public @ResponseBody Object queryActivityByConditionForPage(String name,String owner,String startDate,String endDate,int pageNo,int pageSize){
        //封装参数
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("beginNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        //调用Service方法
        //分页
        List<Activity> activityList = activityService.queryActivityByConditionForPage(map);
        //总条数
        int totalRows = activityService.queryCountOfActivityByCondition(map);
        //生成响应信息
        Map<String,Object> retMap = new HashMap<>();
        retMap.put("activityList",activityList);
        retMap.put("totalRows",totalRows);

        return retMap;
    }

    /**
     *  ******************************************************* 根据id 删除市场活动 ***********************************
     * @param id
     * @return
     */
    @RequestMapping("/workBench/activity/deleteActivityByIds.do")
    public @ResponseBody Object deleteActivityBuIds(String[] id){
        ReturnObject returnObject = new ReturnObject();
        try {
            //调用service层方法
            int ret = activityService.deleteActivityByIds(id);
            if (ret >0){
                returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_ERROR);
                returnObject.setMessage("系统繁忙，请稍后重试...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_ERROR);
            returnObject.setMessage("系统繁忙，请稍后重试...");
        }
        return returnObject;
    }
    /**
     * 根据id 查询市场活动
     */
    @RequestMapping("/workbench/activity/queryActivityById.do")
    public @ResponseBody Object queryActivityById(String id){
        //调用service方法
        Activity activity = activityService.queryActivityById(id);

        return activity;
    }
    /**
     *  ************************************************************ 更新数据 *******************************************
     */
    @RequestMapping("/workbench/activity/saveEditActivity.do")
    public @ResponseBody Object saveEditActivity(Activity activity,HttpSession session){
        //获得session,
        User user = (User) session.getAttribute(ContansDemo.SESSION_USER);
        //封装参数
        activity.setEditTime(DateUtils.formateDate(new Date()));
        activity.setEditBy(user.getId());

        ReturnObject returnObject = new ReturnObject();

        try {
            int ret = activityService.saveEditActivity(activity);
            if (ret>0){
                returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_ERROR);
                returnObject.setMessage("系统繁忙，请稍后再试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_ERROR);
            returnObject.setMessage("系统繁忙，请稍后再试");
        }
        return returnObject;
    }
    /**
     * *************************************************** 导出市场活动,文件下载 *****************************************
     */
    @RequestMapping("/workbench/activity/exportAllActivities.do")
    public void exportAllActivities(HttpServletResponse response) throws Exception {
        //调用Service方法
        List<Activity> activityList = activityService.queryAllActivities();
        //创建excel文件，把activitiesList写入Excel文件中
        //创建文件
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        //创建表单
        HSSFSheet sheet = hssfWorkbook.createSheet("市场活动表");
        //创建行
        HSSFRow hssfRow = sheet.createRow(0);
        //创建列,单元格子，赋值
        HSSFCell hssfCell = hssfRow.createCell(0);
        hssfCell.setCellValue("ID");

        hssfCell=hssfRow.createCell(1);
        hssfCell.setCellValue("所有者");
        hssfCell=hssfRow.createCell(2);
        hssfCell.setCellValue("名称");
        hssfCell=hssfRow.createCell(3);
        hssfCell.setCellValue("开始时间");
        hssfCell=hssfRow.createCell(4);
        hssfCell.setCellValue("结束时间");
        hssfCell=hssfRow.createCell(5);
        hssfCell.setCellValue("成本");
        hssfCell=hssfRow.createCell(6);
        hssfCell.setCellValue("描述");
        hssfCell=hssfRow.createCell(7);
        hssfCell.setCellValue("创建时间");
        hssfCell=hssfRow.createCell(8);
        hssfCell.setCellValue("创建者");
        hssfCell=hssfRow.createCell(9);
        hssfCell.setCellValue("修改时间");
        hssfCell=hssfRow.createCell(10);
        hssfCell.setCellValue("修改者");

        //遍历activityList
        if (activityList !=null&&activityList.size()>0){
            Activity activity=null;
            for (int i=0;i<activityList.size();i++){
                activity=activityList.get(i);
                //每遍历一个activity，生成一行
                hssfRow=sheet.createRow(i+1);
                //每一行创建11列，每一列的数据从activity中获取
                hssfCell = hssfRow.createCell(0);
                hssfCell.setCellValue(activity.getId());

                hssfCell=hssfRow.createCell(1);
                hssfCell.setCellValue(activity.getOwner());
                hssfCell=hssfRow.createCell(2);
                hssfCell.setCellValue(activity.getName());
                hssfCell=hssfRow.createCell(3);
                hssfCell.setCellValue(activity.getStartDate());
                hssfCell=hssfRow.createCell(4);
                hssfCell.setCellValue(activity.getEndDate());
                hssfCell=hssfRow.createCell(5);
                hssfCell.setCellValue(activity.getCost());
                hssfCell=hssfRow.createCell(6);
                hssfCell.setCellValue(activity.getDescription());
                hssfCell=hssfRow.createCell(7);
                hssfCell.setCellValue(activity.getCreateTime());
                hssfCell=hssfRow.createCell(8);
                hssfCell.setCellValue(activity.getCreateBy());
                hssfCell=hssfRow.createCell(9);
                hssfCell.setCellValue(activity.getEditTime());
                hssfCell=hssfRow.createCell(10);
                hssfCell.setCellValue(activity.getEditBy());
            }
        }
        //根据hssfWorkbook对象生成excel文件
        /*OutputStream outputStream = new FileOutputStream("E:\\SSM文件\\CrmFileDownLoad\\activities.xlsx");
        hssfWorkbook.write(outputStream);       //写到磁盘中
        //关闭资源
        outputStream.close();
        hssfWorkbook.close();*/

        //生成的excel文件下载到客户端
        //设置响应类型
        response.setContentType("application/octet-stream;charset=Utf-8");
        //响应头
        response.addHeader("Content-Disposition","attachment;filename=activities.xlsx");
        //输出流
        OutputStream out = response.getOutputStream();

        //文件输入流,下载生成的excel文件，从磁盘中下载
       /* InputStream inputStream = new FileInputStream("E:\\SSM文件\\CrmFileDownLoad\\activities.xlsx");
        byte[] buff = new byte[256];
        int len = 0;
        while ((len=inputStream.read(buff)) !=-1){  // 读到缓冲区中
            out.write(buff,0,len);      //把数据从缓冲区中写出来
        }
        inputStream.close();*/

        hssfWorkbook.write(out);   //从内存到内存
        out.flush();
    }
    /**
     * ************************************************** 选择导出市场活动 **********************************************
     */
    @RequestMapping("/workbench/activity/optionExportAllActivities.do")
    public void optionExportAllActivities(String[] id,HttpServletResponse response) throws Exception {
        //
        List<Activity> activityList = activityService.queryOptionActivityById(id);
        //创建excel文件，把activitiesList写入Excel文件中
        //创建文件
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        //创建表单
        HSSFSheet sheet = hssfWorkbook.createSheet("市场活动表");
        //创建行
        HSSFRow hssfRow = sheet.createRow(0);
        //创建列,单元格子，赋值
        HSSFCell hssfCell = hssfRow.createCell(0);
        hssfCell.setCellValue("ID");

        hssfCell=hssfRow.createCell(1);
        hssfCell.setCellValue("所有者");
        hssfCell=hssfRow.createCell(2);
        hssfCell.setCellValue("名称");
        hssfCell=hssfRow.createCell(3);
        hssfCell.setCellValue("开始时间");
        hssfCell=hssfRow.createCell(4);
        hssfCell.setCellValue("结束时间");
        hssfCell=hssfRow.createCell(5);
        hssfCell.setCellValue("成本");
        hssfCell=hssfRow.createCell(6);
        hssfCell.setCellValue("描述");
        hssfCell=hssfRow.createCell(7);
        hssfCell.setCellValue("创建时间");
        hssfCell=hssfRow.createCell(8);
        hssfCell.setCellValue("创建者");
        hssfCell=hssfRow.createCell(9);
        hssfCell.setCellValue("修改时间");
        hssfCell=hssfRow.createCell(10);
        hssfCell.setCellValue("修改者");

        //遍历activityList
        if (activityList !=null&&activityList.size()>0){
            Activity activity=null;
            for (int i=0;i<activityList.size();i++){
                activity=activityList.get(i);
                //每遍历一个activity，生成一行
                hssfRow=sheet.createRow(i+1);
                //每一行创建11列，每一列的数据从activity中获取
                hssfCell = hssfRow.createCell(0);
                hssfCell.setCellValue(activity.getId());

                hssfCell=hssfRow.createCell(1);
                hssfCell.setCellValue(activity.getOwner());
                hssfCell=hssfRow.createCell(2);
                hssfCell.setCellValue(activity.getName());
                hssfCell=hssfRow.createCell(3);
                hssfCell.setCellValue(activity.getStartDate());
                hssfCell=hssfRow.createCell(4);
                hssfCell.setCellValue(activity.getEndDate());
                hssfCell=hssfRow.createCell(5);
                hssfCell.setCellValue(activity.getCost());
                hssfCell=hssfRow.createCell(6);
                hssfCell.setCellValue(activity.getDescription());
                hssfCell=hssfRow.createCell(7);
                hssfCell.setCellValue(activity.getCreateTime());
                hssfCell=hssfRow.createCell(8);
                hssfCell.setCellValue(activity.getCreateBy());
                hssfCell=hssfRow.createCell(9);
                hssfCell.setCellValue(activity.getEditTime());
                hssfCell=hssfRow.createCell(10);
                hssfCell.setCellValue(activity.getEditBy());
            }
        }

        //生成的excel文件下载到客户端
        //设置响应类型
        response.setContentType("application/octet-stream;charset=Utf-8");
        //响应头
        response.addHeader("Content-Disposition","attachment;filename=activities.xlsx");
        //输出流
        OutputStream out = response.getOutputStream();

        hssfWorkbook.write(out);   //从内存到内存
        out.flush();
    }
    /**
     * ************************************************** 导入市场活动 **********************************************
     */
    @RequestMapping("/workbench/activity/importActivity.do")
    public @ResponseBody Object importActivity(MultipartFile activityFile,HttpSession session){
        User user = (User) session.getAttribute(ContansDemo.SESSION_USER);
        ReturnObject returnObject = new ReturnObject();
        try {
            /*//把文件写入到磁盘目录中
            String  originalFilename=activityFile.getOriginalFilename(); //获取上传的文件名
            File file = new File("E:\\SSM文件\\Crm_Service\\",originalFilename);
            activityFile.transferTo(file);  //把上传的文件转换到file中*/

            //解析excel文件，获取文件中的数据，封装到activityList中
            //1.文件输入流，读取文件
           /* InputStream inputStream = new FileInputStream("E:\\SSM文件\\Crm_Service\\"+originalFilename);*/
            /**
             *  直接从内存到内存
             *  MultipartFile activityFile 中提供了生成流的方法，把前台提交上来的文件转换成流
             */
            InputStream inputStream = activityFile.getInputStream();   //优化了上面的方法
            //根据读取的文件生成对应的Excel文件
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            //获取页数
            HSSFSheet sheet = workbook.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;
            Activity activity = null;
            List<Activity> activityList = new ArrayList<>();
            //获取行
            for (int i=1;i<=sheet.getLastRowNum();i++){
                row = sheet.getRow(i);  //获取行
                activity = new Activity();
                activity.setId(UUIDUtils.getUUID());
                activity.setOwner(user.getId());
                activity.setCreateTime(DateUtils.formateDateTime(new Date()));
                activity.setCreateBy(user.getId());

                for (int j=0;j<row.getLastCellNum();j++){
                    cell = row.getCell(j); //获取单元格
                    //获取单元格的数据
                    String cellValue = HSSFUtils.getCellValueFoeStr(cell);
                    if (j==0){
                        activity.setName(cellValue);
                    }
                    if (j==1){
                        activity.setStartDate(cellValue);
                    }
                    if (j==2){
                        activity.setEndDate(cellValue);
                    }
                    if (j==3){
                        activity.setCost(cellValue);
                    }
                    if (j==4){
                        activity.setDescription(cellValue);
                    }
                }
                activityList.add(activity);
            }

            //调用service层方法
            int count = activityService.saveCreateActivityByList(activityList);
            returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_SUCCESS);
            returnObject.setRetData(count);
        } catch (IOException e) {
            e.printStackTrace();
            returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_ERROR);
            returnObject.setMessage("系统繁忙，请稍后重试");
        }
        return returnObject;
    }

    /**
     *********************************************** 跳转到详情信息，携带信息*******************************************
     */
    @RequestMapping("/workbench/activity/detailActivity.do")
    public String detailActivity(String id,HttpServletRequest request){
        //调用ActivityService查询市场活动明细
        Activity activity = activityService.queryActivityForDetailById(id);
        //调用ActivityRemarkService查询备注信息
        List<ActivityRemark> remarkList = activityRemarkService.queryActivityRemarkForDetailByActivityId(id);


        //把数据保存到request中
        request.setAttribute("activity",activity);
        request.setAttribute("remarkList",remarkList);

        //请求转发
        return "workbench/activity/detail";
    }
    /**
     * 根据clueId和activityName查询市场活动 //关联市场活动页面
     */
    @RequestMapping("/workbench/clue/affiliatedMarketingActivities.do")
    @ResponseBody
    public Object affiliatedMarketingActivities(String activityName,String clueId){
        Map<String,Object> map = new HashMap<>();
        map.put("activityName",activityName);
        map.put("clueId",clueId);

        List<Activity> activityList = activityService.queryActivityForDetailNyActivityNameAndClueId(map);

        return activityList;
    }

}

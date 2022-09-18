package org.example.settings.web.controller;

import org.example.commons.contants.ContansDemo;
import org.example.commons.domain.ReturnObject;
import org.example.commons.utils.DateUtils;
import org.example.settings.domain.User;
import org.example.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 此类为 WEB—INF下的 settings/qx/user 目录
 * 接收首页的跳转，
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * url 要和 controller 方法处理完请求之后，响应信息返回的页面的资源目录保持一致
     */
    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin(){
        //请求转发到登录界面
        return "settings/qx/user/login";
    }
    /**
     *
     */
    @RequestMapping("/settings/qx/user/login.do")
    @ResponseBody
    public Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request, HttpSession session, HttpServletResponse response){
        //封装参数
        Map<String,Object> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        //调用Service层方法，查询用户
        User user = userService.queryUserByLoginActAndPwd(map);

        //根据查询结果，生成响应信息
        ReturnObject returnObject = new ReturnObject();
        if(user==null){
            //登录失败，用户名或密码错误
            returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_ERROR);
            returnObject.setMessage("用户名或者密码错误");
        }else{
            //进一步判断账号是否合法

            String nowStr = DateUtils.formateDateTime(new Date());
            if(nowStr.compareTo(user.getExpireTime())>0){
                //登录失败，账号过期
                returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_ERROR);
                returnObject.setMessage("账号已过期");
            }else if ("0".equals(user.getLockState())){
                //登录失败，状态被锁定
                returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_ERROR);
                returnObject.setMessage("状态被锁定");
            }else if (user.getAllowIps().contains(request.getRemoteAddr())){
                //登录失败，IP受限
                returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_ERROR);
                returnObject.setMessage("IP受限");
            }else{
                //登录成功
                returnObject.setCode(ContansDemo.RETURN_OBJECT_CODE_SUCCESS);
                //把user保存到session中
                session.setAttribute(ContansDemo.SESSION_USER,user);

                //如果需要记住密码，则往外写cookie
                if("true".equals(isRemPwd)){
                    Cookie c1 = new Cookie("loginAct",user.getLoginAct());
                    c1.setMaxAge(10*24*60*60);
                    response.addCookie(c1);

                    Cookie c2 = new Cookie("loginPwd",user.getLoginPwd());
                    c2.setMaxAge(10*24*60*60);
                    response.addCookie(c2);
                }else{
                    //把没有过期的cookie删除
                    Cookie c1 = new Cookie("loginAct","1");
                    c1.setMaxAge(0);
                    response.addCookie(c1);
                    Cookie c2 = new Cookie("loginPwd","1");
                    c2.setMaxAge(0);
                    response.addCookie(c2);
                }
            }
        }
        return returnObject;
    }
    @RequestMapping("/settings/qx/user/logout.do")
    public String logout(HttpServletResponse response,HttpSession session){
        //清空cookie
        Cookie c1 = new Cookie("loginAct","1");
        c1.setMaxAge(0);
        response.addCookie(c1);
        Cookie c2 = new Cookie("loginPwd","1");
        c2.setMaxAge(0);
        response.addCookie(c2);

        //销毁session
        session.invalidate();
        //跳转到首页
        return "redirect:/";  //借助SpringMVC来重定向，底层response.sendRedirect("/crm/");
    }
}

package org.example.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 分页切割，工作区显示
 * 在workbench/main/index.jsp中
 * 分割区跳转业务界面
 */
@Controller
public class MainController {
    @RequestMapping("/workBench/main/index.do")
    public String index(){
        //
        return "workbench/main/index";
    }
}

package org.example.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class CustomerController {

    //跳转到客户首页
    @RequestMapping("/workbench/customer/index.do")
    public String index(){
        //

        return "workbench/customer/index";
    }
}

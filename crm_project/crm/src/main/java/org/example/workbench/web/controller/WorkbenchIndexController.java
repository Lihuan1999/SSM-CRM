package org.example.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 跳转到主业务界面
 */
@Controller
public class WorkbenchIndexController {
    @RequestMapping("/workbench/index.do")
    public String index(){
        return "workbench/index";
    }
}

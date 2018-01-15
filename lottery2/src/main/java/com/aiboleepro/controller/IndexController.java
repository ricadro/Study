package com.aiboleepro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author aiboleepro
 * @date 2018-01-09 下午6:44
 **/
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(Model model){
        return "index";
    }
}

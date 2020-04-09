package com.haidm.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomLoginController {

    @RequestMapping(value = "/login/page")
    public String toLogin(){
        return "login"; //该项目已经引入 web 到项目，直接跳转到 web 项目的classpath:template 下面的 login 页面
    }
}

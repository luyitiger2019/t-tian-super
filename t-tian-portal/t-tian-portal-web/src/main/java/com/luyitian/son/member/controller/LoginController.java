package com.luyitian.son.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginController {
    //跳转到登陆页面
    private static final String MB_LOGIN_FTL="member/login";

    /**
     * 跳转页面
     * @return
     */
    @GetMapping("/login.html")
    public String getLogin()
    {
        return MB_LOGIN_FTL;
    }

    /**
     * 接受请求参数
     * @return
     */
    @PostMapping("/login.html")
    public  String postLogin()
    {
        return MB_LOGIN_FTL;
    }
}

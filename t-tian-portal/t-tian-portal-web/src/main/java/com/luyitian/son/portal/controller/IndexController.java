package com.luyitian.son.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    /**
     * 跳转到INDEX页面
     */
    private static final String INDEX_FTL="index";

    @RequestMapping("/")
    public String index()
    {
        return INDEX_FTL;
    }
}

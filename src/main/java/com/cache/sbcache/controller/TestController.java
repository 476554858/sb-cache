package com.cache.sbcache.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/test")
@Controller
public class TestController {

    @ResponseBody
    @RequestMapping("/mytest")
    public String str(){
        return "test";
    }
}

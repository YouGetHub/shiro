package com.controller;


import com.domain.JsonData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminController {

    // 有 admin 超级管理员的角色才可以访问
    @RequestMapping("/video/order")
    public JsonData findMyPlayRecord(){

        Map<String ,String> recordMap = new HashMap<>();

        recordMap.put("SpringBoot入门到高级实战","300元");
        recordMap.put("Cloud微服务入门到高级实战","877元");
        recordMap.put("分布式缓存Redis","990元");

        return JsonData.buildSuccess(recordMap);

    }

}

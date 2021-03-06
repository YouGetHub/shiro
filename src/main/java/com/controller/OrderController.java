package com.controller;


import com.domain.JsonData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 需要用户登录认证才可以访问
 */
@RestController
@RequestMapping("authc")
public class OrderController {


    @RequestMapping("/video/play_record")
    public JsonData findMyPlayRecord(HttpServletRequest request){
        System.out.println(request.getSession().getId());
        Map<String ,String> recordMap = new HashMap<>();

        recordMap.put("SpringBoot入门到高级实战","第8章第1集");
        recordMap.put("Cloud微服务入门到高级实战","第4章第10集");
        recordMap.put("分布式缓存Redis","第10章第3集");

        return JsonData.buildSuccess(recordMap);

    }

}

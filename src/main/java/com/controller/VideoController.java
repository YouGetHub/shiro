package com.controller;


import com.domain.JsonData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("video")
public class VideoController {

    // 用户有 video_add 权限才可以访问呢
    @RequestMapping("/add")
    public JsonData updateVideo(){

        return JsonData.buildSuccess("video更新成功");

    }

}

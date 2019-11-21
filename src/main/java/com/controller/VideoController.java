package com.controller;


import com.domain.JsonData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户有 video_add 权限才可以访问呢
 */
@RestController
@RequestMapping("video")
public class VideoController {

    @RequestMapping("/add")
    public JsonData updateVideo(){

        return JsonData.buildSuccess("video更新成功");

    }

}

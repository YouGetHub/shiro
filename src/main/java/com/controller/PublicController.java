package com.controller;

import com.domain.InputUser;
import com.domain.JsonData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.testng.mustache.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/public")
public class PublicController {

    // 没有登录的用户 请求需要登录的页面时 自动跳转到登录页面。
    @RequestMapping("need_login")
    @ResponseBody
    public JsonData needLogin(){
        return JsonData.buildSuccess("温馨提示：请先登陆",-2);
    }

    //  登陆成功后，访问某个资源 却没有权限 跳转的页面
    @RequestMapping("not_permit")
    @ResponseBody
    public JsonData notPermit(){
        return JsonData.buildSuccess("温馨提示：拒绝访问，没权限",-3);
    }

    @RequestMapping("loginhtml")
    private String loginhtml(){
        return "login";
    }

    @RequestMapping("index")
    @ResponseBody
    public JsonData index(){

        List<String> videoList = new ArrayList<>();
        videoList.add("Mysql零基础入门到实战 数据库教程");
        videoList.add("Redis高并发高可用集群百万级秒杀实战");
        videoList.add("Zookeeper+Dubbo视频教程 微服务教程分布式教程");
        videoList.add("2019年新版本RocketMQ4.X教程消息队列教程");
        videoList.add("微服务SpringCloud+Docker入门到高级实战");
        return JsonData.buildSuccess(videoList);

    }

    @RequestMapping("login")
    @ResponseBody
    private JsonData login(@RequestBody InputUser user){
        Subject subject = SecurityUtils.getSubject();
        Map<String,Object> map = new HashMap<>();
        try{
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
            subject.login(token);
            map.put("massage","登陆成功");
            map.put("sessionId",subject.getSession().getId());
            System.out.println(subject.getSession().getId());
            return JsonData.buildSuccess(map,"true");
        }catch(Exception e){
            return JsonData.buildError("账号或密码错误");
        }
    }
}

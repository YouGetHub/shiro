package com.controller;


import com.domain.JsonData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LogoutController {


    @RequestMapping("/logout")
    @ResponseBody
    public void findMyPlayRecord(HttpServletRequest request) {
        System.out.println("已退出" + request.getSession().getId());
        System.out.println("logout成功");

/*        Subject subject = SecurityUtils.getSubject();

        if(subject.getPrincipals() != null ){

        }

        SecurityUtils.getSubject().logout();

        return JsonData.buildSuccess("logout成功");

    }*/
    }
}

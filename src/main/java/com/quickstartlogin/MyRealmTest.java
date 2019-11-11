package com.quickstartlogin;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 自定义realm测试类
 * @author zhy
 * @time 2019/11/8
 */
public class MyRealmTest {
    private MyRealm myRealm = new MyRealm();

    DefaultSecurityManager securityManager = new DefaultSecurityManager();

    @Before
    public void init(){
        securityManager.setRealm(myRealm);
        SecurityUtils.setSecurityManager(securityManager);
    }

    @Test
    public void testAuthentication(){
        // 获取当前主体
        Subject subject = SecurityUtils.getSubject();

        // username password
        UsernamePasswordToken token = new UsernamePasswordToken("小明","小明123");

        subject.login(token);

        System.out.println(subject.isAuthenticated());
        System.out.println("当前角色"+subject.getPrincipal());

    }
}

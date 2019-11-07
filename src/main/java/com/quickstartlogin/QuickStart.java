package com.quickstartlogin;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * Springboot整合Shiro 权限验证demo
 * @author zou
 */
public class QuickStart{
    private SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    private DefaultSecurityManager securityManager = new DefaultSecurityManager();

    @Before
    public void init(){
        // 初始化数据源
        simpleAccountRealm.addAccount("username1","123","root","admin");
        simpleAccountRealm.addAccount("username2","123","user");

        // 构建环境
        securityManager.setRealm(simpleAccountRealm);
    }

    @Test
    public void testAuthentication(){
        SecurityUtils.setSecurityManager(securityManager);

        //当前的操作主体，application user
        Subject subject = SecurityUtils.getSubject();

        // 用户输入的账号密码
        UsernamePasswordToken usernamePasswordToken =
                new UsernamePasswordToken("username1","123");

        // 开始认证
        subject.login(usernamePasswordToken);

        System.out.println("认证结果:" + subject.isAuthenticated());
        System.out.println("认证是否有对应的角色:" + subject.hasRole("root"));
        System.out.println("返回当前的角色:" + subject.getPrincipal());

        // 退出
        subject.logout();

    }
}

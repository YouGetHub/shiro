package com.quickstartlogin;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.testng.annotations.Test;

/**
 * Springboot+Shiro+\shiro\src\main\resources\shiro.ini内置ini Realm实操和权限验证
 * @author zou
 */
public class ShiroIniQuickStart2 {
    @Test
    public void testAuthentication(){
        // 创建 SecurityManager工厂，通过配置文件.ini创建
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");

        SecurityManager securityManager = factory.getInstance();

        // 将securityManager 设置到当前运行环境中
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();

        // 用户输入的账号密码
        UsernamePasswordToken token =
                new UsernamePasswordToken("username1","123");

        // 开始认证
        subject.login(token);

        System.out.println("认证结果:" + subject.isAuthenticated());
        System.out.println("用户:"+subject.getPrincipal()+"是否有root角色：" + subject.hasRole("root"));
        System.out.println("返回当前的角色:" + subject.getPrincipal());
        System.out.println("用户:"+subject.getPrincipal()+"是否有find权限：" +subject.isPermitted("video:find"));
        try {
            subject.checkPermission("video:delete");
            System.out.println("用户:"+subject.getPrincipal()+"有find权限");
        }catch (Exception e){
            System.out.println("用户:"+subject.getPrincipal()+"没有find权限");
        }
        // 退出
        subject.logout();
    }
}

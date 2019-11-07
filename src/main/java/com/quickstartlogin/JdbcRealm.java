package com.quickstartlogin;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

/**
 * Shiro内置JdbcRealm 权限验证
 * test：使用jdbcrealm.ini 方法  +\shiro\src\main\resources\jdbcrealm.ini
 * test2: test2Authentication  jdbc
 * @author zouhao
 */
public class JdbcRealm {

    @Test
    public void testAuthentication(){
        // 创建 SecurityManager工厂，通过配置文件.ini创建
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:jdbcrealm.ini");

        SecurityManager securityManager = factory.getInstance();

        // 将securityManager 设置到当前运行环境中
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();

        // 用户输入的账号密码
        UsernamePasswordToken token =
                new UsernamePasswordToken("老板","老板123");

        // 开始认证
        subject.login(token);

        try{
            subject.isAuthenticated();
            System.out.println("认证结果:" + subject.isAuthenticated());
        }catch (Exception e){
            System.out.println("认证结果:" + subject.isAuthenticated());
        }
        System.out.println("用户:"+subject.getPrincipal()+"是否有root角色：" + subject.hasRole("root"));
        System.out.println("返回当前的角色:" + subject.getPrincipal());
        System.out.println("用户:"+subject.getPrincipal()+"是否有buy权限：" +subject.isPermitted("video:find"));
    }

    @Test
    public void test2Authentication(){
        DefaultSecurityManager securityManager = new DefaultSecurityManager();

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/jdbcrealm?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");

        org.apache.shiro.realm.jdbc.JdbcRealm jdbcRealm = new org.apache.shiro.realm.jdbc.JdbcRealm();

        // 开启自动查找权限功能
        jdbcRealm.setPermissionsLookupEnabled(true);
        jdbcRealm.setDataSource(dataSource);
        securityManager.setRealm(jdbcRealm);

        // 将securityManager 设置到当前运行环境中
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();

        // 用户输入的账号密码
        UsernamePasswordToken token =
                new UsernamePasswordToken("小明","小明123");

        // 开始认证
        subject.login(token);

        System.out.println("认证结果:" + subject.isAuthenticated());
        System.out.println("用户:"+subject.getPrincipal()+"是否有admin角色：" + subject.hasRole("admin"));
        System.out.println("返回当前的角色:" + subject.getPrincipal());
        System.out.println("用户:"+subject.getPrincipal()+"是否有add权限：" +subject.isPermitted("video:add"));
        try {
            subject.checkPermission("video:delete");
            System.out.println("用户:"+subject.getPrincipal()+"有delete权限");
        }catch (Exception e){
            System.out.println("用户:"+subject.getPrincipal()+"没有delete权限");
        }
        // 退出
        subject.logout();
    }
}

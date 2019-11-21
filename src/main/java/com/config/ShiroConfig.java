package com.config;


import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro 配置类
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        System.out.println("执行 ShiroFilterFactoryBean ");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 拦截的路径
            // 没有登录的用户 请求需要登录的页面时 自动跳转到登录页面。
            shiroFilterFactoryBean.setLoginUrl("/public/need_login");

            // 登录成功默认跳转页面，不配置则跳转至”/”，也可以通过代码进行处理
            shiroFilterFactoryBean.setSuccessUrl("/");

            // 登陆成功后，访问某个资源 却没有权限 跳转的页面
            shiroFilterFactoryBean.setUnauthorizedUrl("/public/not_permit");

            //设置自定义filter
            Map<String, Filter> filterMap1 = new LinkedHashMap<>();
            filterMap1.put("roleOrFilter",new CustomRolesOrAuthorizationFilter());
            shiroFilterFactoryBean.setFilters(filterMap1);

            // 用于存放拦截的路径
            Map<String,String> filterMap = new LinkedHashMap<>();

            // 退出 清空SessionId 回调 setLoginUrl
            filterMap.put("/logout","logout");

            // 匿名可以访问，不需要登陆也可以访问的资源
            filterMap.put("/public/**","anon");

            // 需要用户登录认证才可以访问
            filterMap.put("/authc/**","authc");

            // 管理员角色才可以访问
            filterMap.put("/admin/**","roleOrFilter[admin,root]");

            // 有编辑权限才可以访问
            filterMap.put("/video/add","perms[video_add]");

            // authc : url定义必须通过认证才可以访问
            filterMap.put("/**", "authc");

            shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager(){
        // 如果不是前后端分离，则不必设置下面的sessionManager
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 使用自定义的cacheManager
        securityManager.setSessionManager(sessionManager());

        // 使用自定的CacheManager
        securityManager.setCacheManager(redisCacheManager());

        // 设置realm
        securityManager.setRealm(customRealm());

        return securityManager;
    }

    // 自定义realm
    @Bean
    public CustomRealm customRealm(){
        CustomRealm customRealm = new CustomRealm();
        // 开启双重MD5加密
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return customRealm;
    }

    // 密码加解密规则
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();

        //设置散列算法：这里使用的MD5算法
        credentialsMatcher.setHashAlgorithmName("md5");

        //散列次数，好比散列2次，相当于md5(md5(xxxx))
        credentialsMatcher.setHashIterations(2);

        return credentialsMatcher;
    }

    // 自定义 sessionManager
    @Bean
    public SessionManager sessionManager(){
        CustomSessionManager sessionManager = new CustomSessionManager();

        // 配置Session持久化
        sessionManager.setSessionDAO(redisSessionDAO());

        //超时时间，默认 30分钟，会话超时；方法里面的单位是毫秒  10秒
        sessionManager.setGlobalSessionTimeout(30000);
        return sessionManager;
    }

    // 配置 redisMessager
    public RedisManager redisManager (){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("192.168.220.11");
//        redisManager.setHost("172.16.0.14");
        redisManager.setPort(6379);
        return redisManager;
    }

    // RedisCacheManager
    public RedisCacheManager redisCacheManager (){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());

        // 设置过期时间，单位秒
        redisCacheManager.setExpire(30);
        return redisCacheManager;
    }

    // 自定义session持久化
    public RedisSessionDAO redisSessionDAO (){
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

/*    // 管理shiro一些bean的生命周期 即bean初始化 与销毁
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }*/
}

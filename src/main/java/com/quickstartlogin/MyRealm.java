package com.quickstartlogin;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义realm  继承AuthorizingRealm
 * @author zhy
 * @time 2019/11/8
 */
public class MyRealm extends AuthorizingRealm {

    // 权限校验时调用
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("权限校验");
        Object primaryPrincipal = principalCollection.getPrimaryPrincipal();

        return null;
    }

    // 登陆时调用 运行subject.login(token) 时调用 AuthenticationInfo()
    // AuthenticationToken 用户传的username password
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("用户认证");

        // 获取用户输入的用户名
        String username = (String)token.getPrincipal();

        // 从数据库中读取用户密码
//        String password = usersService.selectUsersByUserName(username);
        String password = "小明123";

        if (password == null || "".equals(password)){
            return null;
        }

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username,password,this.getName());

        return simpleAuthenticationInfo;
    }
}

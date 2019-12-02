package com.config;

import com.domain.Permission;
import com.domain.Role;
import com.domain.User;
import com.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义realm
 */
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("身份授权");
        // 获取username
        User newUser = (User) principalCollection.getPrimaryPrincipal();

        // 根据输入的username获取全部用户信息，包括角色，权限
        User user = userService.findAllUserInfoByUsername(newUser.getUsername());

        // 存放得到的角色信息
        List<String> stringRoleList = new ArrayList<>();
        // 存放得到的权限信息
        List<String> stringPermissionList = new ArrayList<>();

        // 获取用户对应的角色对象
        List<Role> roleList1 = user.getRoleList();

        // 遍历用户的对应的角色
        for (Role role : roleList1){
            // 获取角色 存放到集合
            stringRoleList.add(role.getName());
            // 获取 角色 对应的权限
            List<Permission> permissionList = role.getPermissionList();
            // 遍历角色
            for (Permission permission : permissionList){

                if (permission!=null){
                    // 获取 权限 存放到集合
                    stringPermissionList.add(permission.getName());
                }
            }
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(stringRoleList);
        simpleAuthorizationInfo.addStringPermissions(stringPermissionList);

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("登陆认证");

        // 从token中获取用户输入的信息
        String username =(String) authenticationToken.getPrincipal();
        System.out.println(username);

        // 根据输入的username获取全部用户信息，包括角色，权限
        User user = userService.findAllUserInfoByUsername(username);

        // 从数据库获取密码
        String password = user.getPassword();

        if (password == null || "".equals(password)){
            System.out.println("登陆失败");
            return null;
        }

        System.out.println("认证成功");
        return new SimpleAuthenticationInfo(user,user.getPassword(),this.getClass().getName());
    }
}
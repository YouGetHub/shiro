package com.service.serviceImpl;

import com.dao.PermissionMapper;
import com.dao.RoleMapper;
import com.dao.UserMapper;
import com.domain.Permission;
import com.domain.Role;
import com.domain.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 获取全部用户信息，包括角色，权限
     * @param username
     * @return
     */
    @Override
    public User findAllUserInfoByUsername(String username) {
        // 查询对应的用户基本信息
        User user = userMapper.findByUsername(username);
        // 根据用户id查询相应的角色权限信息
        List<Role> roleList = roleMapper.findRoleListByUserId(user.getId());
        user.setRoleList(roleList);
        return user;
    }

    /**
     * 获取用户基本信息
     * @param userId
     * @return
     */
    @Override
    public User findSimpleUserInfoById(int userId) {
        return userMapper.findById(userId);
    }

    /**
     * 根据用户名查找用户信息
     * @param username
     * @return
     */
    @Override
    public User findSimpleUserInfoByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}

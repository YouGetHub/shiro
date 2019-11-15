package com.dao;

import com.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

    // 使用 username 查找用户数据
    @Select("select * from user where username = #{username}")
    User findByUsername(@Param("username")String username);

    // 使用 id 查找用户数据
    @Select("select * from user where id=#{userId}")
    User findById(@Param("userId") int id);


    // 使用 username password 查找用户数据
    @Select("select * from user where username = #{username} and password = #{password}")
    User findByUsernameAndPassword(@Param("username")String username, @Param("password")String password);
}

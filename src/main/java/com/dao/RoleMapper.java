package com.dao;

import com.domain.Role;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface RoleMapper {

    // 根据 userId 查询对应的角色信息
    @Select("select ur.role_id as id, " +
            "r.name as name, " +
            "r.description as description " +
            " from  user_role ur left join role r on ur.role_id = r.id " +
            "where  ur.user_id = #{userId}")
    @Results(
            value = {
                    @Result(id=true, property = "id",column = "id"),
                    @Result(property = "name",column = "name"),
                    @Result(property = "description",column = "description"),
                    @Result(property = "permissionList",column = "id",
                            many = @Many(select = "com.dao.PermissionMapper.findPermissionListByRoleId", fetchType = FetchType.DEFAULT)
                    )
            }
    )
    List<Role> findRoleListByUserId(@Param("userId")int userId);

}

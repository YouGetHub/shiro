package com.domain;

import java.io.Serializable;

/**
 * 用户角色权限表
 */
public class Permission implements Serializable {
    private Integer id; // 权限id

    private String name; // 权限名

    private String url; // 接口路径

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
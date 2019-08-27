package com.luyitian.son.weixin.entity;

import lombok.Data;

/**
 * 微信应用实体类
 */
@Data
public class AppEntity {
    private String id;
    private String name;

    public AppEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "AppEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

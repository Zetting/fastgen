package com.fastgen.core.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 项目信息
 *
 * @author: zet
 * @date:2019/10/17
 */
@Builder
@Data
public class ProjectInfo implements Serializable {
    /**
     * 项目Id
     */
    private Long id;
    /**
     * 项目名
     */
    private String projectName;
    /**
     * 项目路径
     */
    private String projectPath;
    /**
     * 是否覆盖
     */
    private Boolean convered;
    /**
     * 作者
     */
    private String author;
    /**
     * 模板
     */
    private String templates;

    /**
     * 数据库-类型
     */
    private String dbType;
    /**
     * 数据库-驱动
     */
    private String dbDriverClassName;
    /**
     * 数据库-url
     */
    private String dbUrl;
    /**
     * 数据库-用户名
     */
    private String dbUsername;
    /**
     * 数据库-密码
     */
    private String dbPassword;
}

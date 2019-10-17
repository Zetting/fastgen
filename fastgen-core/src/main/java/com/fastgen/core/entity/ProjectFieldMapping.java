package com.fastgen.core.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 项目字段映射
 *
 * @author: zet
 * @date:2019/10/17
 */
@Builder
@Data
public class ProjectFieldMapping implements Serializable {
    /**
     * Id
     */
    private Long id;
    /**
     * 项目名Id
     */
    private Long projectId;
    /**
     * 原始类型
     */
    private String originalType;

    /**
     * 目标类型
     */
    private String targetType;

}

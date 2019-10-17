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
public class ProjectCustomVariable implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 项目Id
     */
    private Long projectId;
    /**
     * 变量名
     */
    private String variableName;

    /**
     * 变量值
     */
    private String variableValue;

}

package com.fastgen.core.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 动态参数
 *
 * @author: zet
 * @date:2019/10/10
 */
@Builder
@Data
public class ProjectDynamicParams implements Serializable {
    /**
     * 组件标签
     */
    private String componentLabel;
    /**
     * 组件名
     */
    private String componentName;
    /**
     * 值
     */
    private String componentValue;

    /**
     * 是否必填;"true" ,"false"
     */
    private String required;

    /**
     * 组件的唯一键值
     */
    private String key;

}

package com.fastgen.core.contract;

import lombok.Data;

/**
 * 动态表单配置
 *
 * @author: zet
 * @date:2019/10/10
 */
@Data
public class DynamicFormConfigVO {
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

package com.fastgen.core.contract.vo;

import lombok.Data;

import java.util.Map;

/**
 * 自定义配置对象
 *
 * @author: zet
 * @date: 2019/10/1 14:31
 */
@Data
public class CfgSettings {
    /**
     * 配置Key
     */
    private String key;
    /**
     * 配置名称
     */
    private String name;
    /**
     * 配置值
     */
    private Map<String, String> settings;

}

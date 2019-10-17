package com.fastgen.core.contract;

import lombok.Data;

import java.util.Properties;

/**
 * 模块Ftl信息
 *
 * @author: zet
 * @date:2019/9/20
 */
@Data
public class TemplateFtlInfo {
    /**
     * 配置信息
     */
    private Properties properties;

    /**
     * ftl模板文件名
     */
    private String templateName;

    /**
     * 最终要生成的文件路径
     */
    private String filePath;

    /**
     * 去除掉配置信息的模板内容
     */
    private String templateContent;
}

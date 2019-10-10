package com.fastgen.core.service;

import com.fastgen.core.model.TemplateFtlInfo;

import java.util.List;
import java.util.Map;

/**
 * 配置-服务类
 *
 * @author: zet
 * @date:2019/9/20
 */
public interface ConfigService {
    /**
     * 获取启用的模板名称
     *
     * @return
     */
    List<String> templateNames();

    /**
     * 获取模板信息
     *
     * @return
     */
    List<TemplateFtlInfo> templateInfos(Map<String, Object> variableMaps);


    /**
     * 获取模板配置信息
     *
     * @param variableMaps    系统变量
     * @param templateFtlName 模板ftl名称
     * @return
     */
    TemplateFtlInfo getTemplateInfo(Map<String, Object> variableMaps, String templateFtlName);
}

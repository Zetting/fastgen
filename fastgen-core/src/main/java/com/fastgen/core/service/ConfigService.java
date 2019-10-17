package com.fastgen.core.service;

import com.fastgen.core.base.Response;
import com.fastgen.core.contract.BaseConfigItem;
import com.fastgen.core.contract.BaseConfigInfo;
import com.fastgen.core.contract.TemplateFtlInfo;

import java.util.List;
import java.util.Map;

/**
 * 配置-服务类
 *
 * @author: zet
 * @date:2019/9/20
 */
public interface ConfigService {
    BaseConfigInfo getBaseConfig();
    /**
     * 获取当前项目配置
     *
     * @return
     */
    Map getCustomConfig();

    /**
     * 更新当前项目配置
     *
     * @return
     */
    Response updateCustomConfig(Map<String, Object> configs);

    /**
     * 获取当前项目基础配置
     *
     * @return
     */
    BaseConfigItem getCurrentBaseConfig();

    /**
     * 更新基础配置
     */
    Response updateBaseConfig(BaseConfigInfo config);


    /**
     * 获取当前项目启用的模板名称
     *
     * @return
     */
    List<String> templateNames();

    /**
     * 获取当前项目的模板信息
     *
     * @param variableMaps 系统常量
     * @return 模板信息
     */
    List<TemplateFtlInfo> templateInfos(Map<String, Object> variableMaps);


    /**
     * 获取模板配置信息
     *
     * @param variableMaps    系统变量
     * @param templateFtlName 模板ftl名称
     * @return 模板配置信息
     */
    TemplateFtlInfo getTemplateInfo(Map<String, Object> variableMaps, String templateFtlName);
}

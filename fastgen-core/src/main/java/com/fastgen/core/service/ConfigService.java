package com.fastgen.core.service;

import com.fastgen.core.contract.vo.CfgSettings;
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
    List<TemplateFtlInfo> templateInfos(Map<String, Object> templateValue);

    /**
     * 根据文件名获取指定配置
     *
     * @param ftlFileName
     * @return
     */
    TemplateFtlInfo getTemplateFtlInfo(List<TemplateFtlInfo> templateFtlInfos, String ftlFileName);

    /**
     * 获取自定义配置
     *
     * @return
     */
    List<CfgSettings> getSettings();
}

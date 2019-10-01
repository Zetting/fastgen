package com.fastgen.core.service.impl;

import cn.hutool.core.io.FileUtil;
import com.fastgen.core.base.ServerException;
import com.fastgen.core.base.cfgs.SettingMapsCfgs;
import com.fastgen.core.contract.vo.CfgSettings;
import com.fastgen.core.model.TemplateFtlInfo;
import com.fastgen.core.service.ConfigService;
import com.fastgen.core.base.Contants;
import com.fastgen.core.util.FreemarkerUtil;
import com.fastgen.core.util.PropertiesUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 配置实现类
 *
 * @author: zet
 * @date:2019/9/20
 */
@Slf4j
@Service
public class ConfigServiceImpl implements ConfigService {
    @Value("${spring.profiles.active}")
    private String active;
    @Autowired
    private FreemarkerUtil freemarkerUtil;
    @Autowired
    private SettingMapsCfgs settingMapsCfgs;


    /**
     * 获取基本路径
     *
     * @return
     */
    public String getBasePath() {
        String path = null;
        if (Objects.isNull(active) || Contants.ENV_PROD.equals(active)) {
            path = FileUtil.getParent(this.getClass().getClassLoader().getResource("")
                    .getFile().replace("!/BOOT-INF/classes!", ""), 1);
        } else {
            path = this.getClass().getResource("/").getPath();
        }
        return path;
    }

    /**
     * 获取模板路径
     *
     * @return
     */
    public String getTemplatePath() {
        return getBasePath() + File.separator + Contants.TEMPLATES_PATH_NAME;
    }

    @Override
    public List<String> templateNames() {
        String templatePath = getTemplatePath();
        String[] templates = new File(templatePath).list();
        if (templates == null || templates.length == 0) {
            log.warn("模板配置为空,template path ={}", templatePath);
            return Collections.emptyList();
        }

        Map<String, Object> templateValue = new HashMap<>();
        List<TemplateFtlInfo> templateFtlInfos = templateInfos(templateValue);
        List<String> names = new ArrayList<>();
        templateFtlInfos.forEach(info -> {
            names.add(info.getTemplateName());
        });
        return names;
    }

    @Override
    public List<TemplateFtlInfo> templateInfos(Map<String, Object> templateValue) {
        String templatePath = getTemplatePath();
        String[] templates = new File(templatePath).list();
        if (templates == null || templates.length == 0) {
            return Collections.emptyList();
        }
        List<TemplateFtlInfo> templateFtlInfos = new ArrayList<>();
        TemplateFtlInfo templateFtlInfo = null;
        for (String template : templates) {
            templateFtlInfo = getTemplateInfo(templateValue, template);
            if (Objects.nonNull(templateFtlInfo)) {
                templateFtlInfos.add(templateFtlInfo);
            }
        }
        return templateFtlInfos;
    }

    @Override
    public TemplateFtlInfo getTemplateFtlInfo(List<TemplateFtlInfo> templateFtlInfos, String ftlFileName) {
        if (CollectionUtils.isEmpty(templateFtlInfos)) {
            return null;
        }
        List<TemplateFtlInfo> filterList = templateFtlInfos.stream().filter(
                templateFtlInfo -> templateFtlInfo.getTemplateName().equals(ftlFileName)).collect(Collectors.toList());
        return CollectionUtils.isEmpty(filterList) ? null : filterList.get(0);
    }

    @Override
    public List<CfgSettings> getSettings() {
        Map<String, Map<String, String>> settingMaps = settingMapsCfgs.getMaps();
        if (settingMaps.size() == 0) {
            return Collections.emptyList();
        }
        List<CfgSettings> cfgSettings = new ArrayList<>();
        CfgSettings cfgSetting = null;
        for (String key : settingMaps.keySet()) {
            cfgSetting = new CfgSettings();

            Map<String, String> settings = settingMaps.get(key);
            cfgSetting.setKey(key);
            cfgSetting.setName(settings.get("name"));
            cfgSetting.setSettings(settings);
            cfgSettings.add(cfgSetting);
        }
        return cfgSettings;
    }

    /**
     * 获取ftl模板信息
     *
     * @param templateFtlName 模板名称
     * @return
     */
    private TemplateFtlInfo getTemplateInfo(Map<String, Object> templateValue, String templateFtlName) {
        TemplateConfig templateConfig = getFtlConfigInfo(templateFtlName, templateValue);
        Properties properties = PropertiesUtil.contentToProperties(templateConfig.getConfigStr());
        String enable = properties.getProperty(Contants.FTL_CONFIG_ENABLE);
        TemplateFtlInfo templateFtlInfo = null;
        if (StringUtils.isEmpty(enable) || Contants.TRUE.equals(enable)) {
            templateFtlInfo = new TemplateFtlInfo();
            templateFtlInfo.setProperties(properties);
            templateFtlInfo.setTemplateName(templateFtlName);
            templateFtlInfo.setFilePath(properties.getProperty(Contants.FTL_CONFIG_FILE_PATH, ""));
            templateFtlInfo.setTemplateContent(templateConfig.getTemplateContent());
            return templateFtlInfo;
        }
        return null;
    }

    /**
     * 读取ftl配置信息
     *
     * @param templateFtlName
     * @param templateValue
     * @return
     */
    private TemplateConfig getFtlConfigInfo(String templateFtlName, Map<String, Object> templateValue) {
        String templatePath = getTemplatePath();
        String ftlNamePath = templatePath + File.separator + templateFtlName;
        String content = FileUtil.readString(ftlNamePath, Contants.CHARSET_CODE);

        if (StringUtils.isEmpty(content)) {
            throw new ServerException("模板内容为空");
        }
        int startIndex = content.indexOf(Contants.TAG_START_CONFIG);
        int endIndex = content.indexOf(Contants.TAG_END_CONFIG);

        if (startIndex == -1 || endIndex == -1) {
            throw new ServerException(String.format("模板%s缺乏%s\n%s配置/或设置有误",
                    templateFtlName, Contants.TAG_START_CONFIG, Contants.TAG_END_CONFIG));
        }
        String configStr = content.substring(startIndex + Contants.TAG_START_CONFIG.length(), endIndex);
        if (configStr == null || "".equals(configStr.trim())) {
            throw new ServerException(String.format("模板%s,%s\n%s配置不能为空",
                    templateFtlName, Contants.TAG_START_CONFIG, Contants.TAG_END_CONFIG));
        }
        if (templateValue != null && templateValue.size() > 0) {
            try {
                configStr = freemarkerUtil.parse(configStr, templateValue);
            } catch (Exception e) {
                throw new ServerException("获取模板配置信息异常");
            }
        }

        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setConfigStr(configStr);
        templateConfig.setTemplateContent(content.substring(0, startIndex));
        return templateConfig;
    }


    /**
     * 模板配置信息
     */
    @Data
    public static class TemplateConfig {
        /**
         * 配置信息
         */
        private String configStr;
        /**
         * 模板信息
         */
        private String templateContent;
    }
}

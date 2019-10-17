package com.fastgen.core.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.fastgen.core.base.Contants;
import com.fastgen.core.base.Response;
import com.fastgen.core.base.ServerException;
import com.fastgen.core.contract.BaseConfigItem;
import com.fastgen.core.contract.BaseConfigInfo;
import com.fastgen.core.contract.TemplateFtlInfo;
import com.fastgen.core.service.ConfigService;
import com.fastgen.core.util.ConfigUtil;
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
    private ConfigUtil configUtil = ConfigUtil.getInstance(active);

    @Override
    public BaseConfigInfo getBaseConfig() {
        return configUtil.getBaseConfig();
    }

    @Override
    public Map<String, Object> getCustomConfig() {
        BaseConfigItem projectCfg = getCurrentBaseConfig();
        String path = projectCfg.getPath() + File.separator + Contants.FILE_NAME_FGCUSTOM;
        Map<String, Object> config = configUtil.getMapFormJson(path);
        return config;
    }

    @Override
    public Response updateCustomConfig(Map<String, Object> configs) {
        BaseConfigItem projectCfg = getCurrentBaseConfig();
        String path = projectCfg.getPath() + File.separator + Contants.FILE_NAME_FGCUSTOM;
        configUtil.write(path, JSONUtil.toJsonStr(configs));
        return Response.success();
    }


    @Override
    public Response updateBaseConfig(BaseConfigInfo config) {
        configUtil.write(Contants.TAG_CURRENT_PATH + Contants.FILE_NAME_FGBASE, JSONUtil.toJsonStr(config));
        return Response.success();
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
    public List<TemplateFtlInfo> templateInfos(Map<String, Object> variableMaps) {
        String templatePath = getTemplatePath();
        String[] templates = new File(templatePath).list();
        if (templates == null || templates.length == 0) {
            return Collections.emptyList();
        }
        List<TemplateFtlInfo> templateFtlInfos = new ArrayList<>();
        TemplateFtlInfo templateFtlInfo = null;
        for (String template : templates) {
            templateFtlInfo = getTemplateInfo(variableMaps, template);
            if (Objects.nonNull(templateFtlInfo)) {
                templateFtlInfos.add(templateFtlInfo);
            }
        }
        return templateFtlInfos;
    }

    /**
     * 获取ftl模板信息
     *
     * @param templateFtlName 模板名称
     * @return
     */
    public TemplateFtlInfo getTemplateInfo(Map<String, Object> variableMaps, String templateFtlName) {
        TemplateConfig templateConfig = getFtlConfigInfo(templateFtlName, variableMaps);
        Properties properties = PropertiesUtil.contentToProperties(templateConfig.getConfigStr());
        String enable = properties.getProperty(Contants.FIELD_FTL_CONFIG_ENABLE);
        TemplateFtlInfo templateFtlInfo = null;
        if (StringUtils.isEmpty(enable) || Contants.TRUE.equals(enable)) {
            templateFtlInfo = new TemplateFtlInfo();
            templateFtlInfo.setProperties(properties);
            templateFtlInfo.setTemplateName(templateFtlName);
            templateFtlInfo.setFilePath(properties.getProperty(Contants.FIELD_FTL_CONFIG_FILE_PATH, ""));
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
     * 获取指定项目基础配置
     *
     * @return
     */
    @Override
    public BaseConfigItem getCurrentBaseConfig() {
        BaseConfigInfo projectsConfig = configUtil.getBaseConfig();
        if (Objects.isNull(projectsConfig) || CollectionUtils.isEmpty(projectsConfig.getProjects())) {
            log.error("项目基础配置为空，请新增相应项目");
            throw new ServerException("项目基础配置为空，请新增相应项目");
        }
        for (BaseConfigItem projectCfg : projectsConfig.getProjects()) {
            if (projectsConfig.getCurrentProjectId().equals(projectCfg.getProjectId())) {
                return projectCfg;
            }
        }
        log.error("找不到该项目配置,projectId=[{}]", projectsConfig.getCurrentProjectId());
        throw new ServerException("找不到该项目配置");
    }

    /**
     * 获取基本路径
     *
     * @return
     */
    public String getBasePath() {
        String path = null;
        if (StringUtils.isEmpty(active) || Contants.ENV_RELEASE.equals(active)) {
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
        BaseConfigItem projectCfg = getCurrentBaseConfig();
        String path = projectCfg.getPath() + File.separator + Contants.DIR_NAME_TEMPLATES;
        if (path.startsWith(Contants.TAG_CURRENT_PATH)) {
            path = path.replaceFirst(Contants.TAG_CURRENT_PATH, "");
            return getBasePath() + File.separator + path;
        } else {
            return path;
        }
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

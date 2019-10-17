package com.fastgen.core.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.json.JSONUtil;
import com.fastgen.core.base.Contants;
import com.fastgen.core.base.ServerException;
import com.fastgen.core.contract.BaseConfigInfo;
import com.fastgen.core.contract.BaseConfigItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.*;

/**
 * 配置-工具类
 *
 * @author: zet
 * @date:2019/9/12
 */
@Slf4j
public class ConfigUtil {
    private String active;
    private transient static ConfigUtil configUtil = null;

    private ConfigUtil(String active) {
        this.active = active;
    }

    /**
     * 单例
     *
     * @return
     */
    public static ConfigUtil getInstance(String active) {
        try {
            if (configUtil == null) {
                synchronized (ConfigUtil.class) {
                    if (configUtil == null) {
                        configUtil = new ConfigUtil(active);
                        return configUtil;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{}", e);
        }
        return configUtil;
    }

    public final String JAR_HOME = System.getProperty("user.dir");

    /**
     * 写入配置
     *
     * @param suffixPath
     * @param content
     */
    public void write(String suffixPath, String content) {
        try {
            File file = getFile(suffixPath);
            FileUtil.writeString(content, file, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取配置
     *
     * @param suffixPath
     * @return
     */
    public String getContent(String suffixPath) {
        try {
            File file = getFile(suffixPath);
            return FileUtil.readString(file,"utf-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文件
     *
     * @param suffixPath
     * @return
     * @throws FileNotFoundException
     */
    private File getFile(String suffixPath) throws FileNotFoundException {
        File file = null;
        if (suffixPath.startsWith(Contants.TAG_CURRENT_PATH)) {
            suffixPath = suffixPath.replaceFirst(Contants.TAG_CURRENT_PATH, "");
            if (StringUtils.isBlank(active) || Contants.ENV_RELEASE.equals(active)) {
                file = ResourceUtils.getFile(JAR_HOME + File.separator + suffixPath);
            } else {
                file = ResourceUtils.getFile("classpath:" + suffixPath);
            }
        } else {
            file = ResourceUtils.getFile(suffixPath);
        }
        return file;
    }

    /**
     * 读取json对象配置
     *
     * @param suffixPath
     * @return
     */
    public Map getMapFormJson(String suffixPath) {
        String configStr = getContent(suffixPath);
        return JSONUtil.toBean(configStr, Map.class);
    }

    /**
     * 读取json对象配置
     *
     * @param suffixPath
     * @return
     */
    public Map<String, Object> getMapFormProperties(String suffixPath) {
        Map<String, Object> map = new HashMap<>();
        try {
            Properties properties = new Properties();
            File configFile = getFile(suffixPath);
            properties = new Properties();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(configFile));
            properties.load(bufferedReader);
            map = (Map) properties;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 转换mysql数据类型为java数据类型
     *
     * @param type
     * @return
     */
    public String cloToJava(Map<String, Object> fieldConfigs, String type) {
        String javaType = Objects.nonNull(fieldConfigs.get(type)) ? fieldConfigs.get(type).toString() : "";
        return "".equals(javaType) ? "Object" : javaType;
    }

    /**
     * 获取基本配置
     *
     * @return
     */
    public BaseConfigInfo getBaseConfig() {
        String configStr = getContent(Contants.TAG_CURRENT_PATH + Contants.FILE_NAME_FGBASE);
        if (StringUtils.isBlank(configStr)) {
            log.error("读取配置文件内容{}为空", Contants.FILE_NAME_FGBASE);
            throw new ServerException("读取配置文件内容为空");
        }
        BaseConfigInfo baseConfig = JSONUtil.toBean(configStr,
                BaseConfigInfo.class);
        if (Objects.isNull(baseConfig)) {
            log.error("解析配置文件{}为空", Contants.FILE_NAME_FGBASE);
            throw new ServerException("解析配置文件为空");
        }
        if (StringUtils.isBlank(baseConfig.getCurrentProjectId())) {
            log.error("配置文件{},当前项目Id为空", Contants.FILE_NAME_FGBASE);
            throw new ServerException("当前项目Id为空");
        }
        if (CollectionUtils.isEmpty(baseConfig.getProjects())) {
            log.error("配置文件{},项目信息为空", Contants.FILE_NAME_FGBASE);
            throw new ServerException("项目信息为空");
        }
        return baseConfig;
    }

    /**
     * 获取字段映射配置
     *
     * @return
     */
    public Map<String, Object> getFieldConfigs(String suffixPath) {
        Map<String, Object> maps = this.getMapFormProperties(suffixPath);
        Map<String, Object> respMap = new HashMap<>();
        for (String key : maps.keySet()) {
            if (key.startsWith(Contants.FIELD_PREFIX_FIELD_MAPPING)) {
                respMap.put(key, maps.get(key));
            }
        }
        return respMap;
    }

    /**
     * 获取自定义配置
     *
     * @return
     */
    public Map<String, Object> getCustomConfigs(String suffixPath) {
        Map<String, Object> maps = this.getMapFormProperties(suffixPath);
        Map<String, Object> respMap = new HashMap<>();
        for (String key : maps.keySet()) {
            if (key.startsWith(Contants.FIELD_PREFIX_CUSTOM_CONFIG)) {
                respMap.put(key, maps.get(key));
            }
        }
        return respMap;
    }

    /**
     * 获取数据库配置
     *
     * @return
     */
    public Map<String, Object> getDbConfigs(String suffixPath) {
        Map<String, Object> maps = this.getMapFormProperties(suffixPath);
        Map<String, Object> respMap = new HashMap<>();
        for (String key : maps.keySet()) {
            if (key.startsWith(Contants.FIELD_PREFIX_DATASOURCE_CONFIG)) {
                respMap.put(key.replace(Contants.FIELD_PREFIX_DATASOURCE_CONFIG, ""), maps.get(key));
            }
        }
        return respMap;
    }

    /**
     * 获取所有数据库配置
     *
     * @return
     */
    public List<Map<String, Object>> getAllDbConfigs() {
        BaseConfigInfo baseConfig = getBaseConfig();
        List<Map<String, Object>> configList = new ArrayList<>();
        Map<String, Object> config = null;
        List<BaseConfigItem> projects = baseConfig.getProjects();
        for (BaseConfigItem project : projects) {
            String suffixPath = project.getPath() + File.separator + Contants.FILE_NAME_PROJECT;
            config = getDbConfigs(suffixPath);
            config.put(Contants.FIELD_DB_PROJECTID, project.getProjectId());
            if (project.getProjectId().equals(baseConfig.getCurrentProjectId())) {
                config.put(Contants.FIELD_DB_ISCURRENT, true);
            } else {
                config.put(Contants.FIELD_DB_ISCURRENT, false);
            }
            configList.add(config);
        }
        return configList;
    }

}

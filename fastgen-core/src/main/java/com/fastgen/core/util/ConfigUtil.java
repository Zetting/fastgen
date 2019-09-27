package com.fastgen.core.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.json.JSONUtil;
import com.fastgen.core.base.cfgs.FieldMapsCfgs;
import com.fastgen.core.contract.vo.GenConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 配置文件工具类
 *
 * @author: zet
 * @date:2019/9/12
 */
@Component
public class ConfigUtil {
    @Value("${spring.profiles.active}")
    private String active;
    @Autowired
    private FieldMapsCfgs fieldMapsCfgs;

    public static final String PROD = "prod";
    public static final String JAR_HOME = System.getProperty("user.dir");

    /**
     * 写入配置
     *
     * @param configName
     * @param content
     */
    public void insertConfig(String configName, String content) {
        try {
            File file = getFile(configName);
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
     * @param configName
     * @return
     */
    public String getConfig(String configName) {
        try {
            File file = getFile(configName);
            return FileUtil.readString(file, "UTF-8");
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
     * @param configName
     * @return
     * @throws FileNotFoundException
     */
    private File getFile(String configName) throws FileNotFoundException {
        File file = null;
        if (PROD.equals(active)) {
            file = ResourceUtils.getFile(JAR_HOME + File.separator + configName);
        } else {
            file = ResourceUtils.getFile("classpath:" + configName);
        }
        return file;
    }

    /**
     * 读取json对象配置
     *
     * @param configName
     * @return
     */
    public GenConfig getConfigBean(String configName) {
        String configStr = getConfig(configName);
        return JSONUtil.toBean(configStr, GenConfig.class);
    }

    /**
     * 转换mysql数据类型为java数据类型
     *
     * @param type
     * @return
     */
    public String cloToJava(String type) {
        String javaType = fieldMapsCfgs.getMaps().get(type);
        return "".equals(javaType) ? "Object" : javaType;
    }


}

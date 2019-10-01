package com.fastgen.core.base.cfgs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 自定义配置
 *
 * @author: zet
 * @date: 2019/9/14 13:36
 */
@Data
@Component
@ConfigurationProperties(prefix = "setting")
public class SettingMapsCfgs {
    Map<String, Map<String, String>> maps;
}

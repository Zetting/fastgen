package com.fastgen.core.base.cfgs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 数据库字段映射配置
 *
 * @author: zet
 * @date: 2019/9/14 13:36
 */
@Data
@Component
@ConfigurationProperties(prefix = "field")
public class FieldMapsCfgs {
    private Map<String, String> maps;
}

package com.fastgen.core.model;

import lombok.Data;

import java.util.List;

/**
 * 项目配置-vo
 *
 * @author: zet
 * @date:2019/10/14
 */
@Data
public class BaseConfigInfo {
    /**
     * 当前项目Id
     */
    private String currentProjectId;

    /**
     * 项目配置
     */
    List<BaseConfigItem> projects;
}

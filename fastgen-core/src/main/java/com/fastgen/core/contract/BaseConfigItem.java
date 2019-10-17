package com.fastgen.core.contract;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 项目配置-vo
 *
 * @author: zet
 * @date:2019/10/14
 */
@Data
public class BaseConfigItem {
    /**
     * 项目Id
     */
    @NotBlank
    private String projectId;
    /**
     * 项目名称
     */
    @NotBlank
    private String projectName;

    /**
     * 项目路径
     */
    @NotBlank
    private String path;
}

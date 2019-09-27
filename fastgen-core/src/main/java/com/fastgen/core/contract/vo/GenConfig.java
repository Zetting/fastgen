package com.fastgen.core.contract.vo;

import lombok.Data;

/**
 * 代码生成配置
 *
 * @author zet
 * @date 2019-01-03
 */
@Data
public class GenConfig {

    private Long id;

    /**
     * 包路径
     **/
    private String pack;

    /**
     * 分组名
     **/
    private String groupName;

    /**
     * 后台项目路径
     **/
    private String serverPath;

    /**
     * 前端文件路径
     **/
    private String frontPath;

    /**
     * 作者
     **/
    private String author;

    /**
     * 表前缀
     **/
    private String prefix;

    /**
     * 是否覆盖
     **/
    private Boolean cover;

    /**
     * 要生成的模板集
     **/
    private String templates;

    /**
     * 生成模式；admin：后台管理模式，Api：Api模式，详细见备注
     **/
    private String genMode;

    /**
     * 备注
     **/
    private String remark;
}

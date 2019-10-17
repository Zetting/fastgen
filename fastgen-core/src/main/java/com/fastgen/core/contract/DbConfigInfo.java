package com.fastgen.core.contract;

import lombok.Data;

/**
 * 数据库信息
 *
 * @author: zet
 * @date:2019/10/15
 */
@Data
public class DbConfigInfo {
    /**
     * 类型
     */
    private String type;

    /**
     * 驱动类名
     */
    private String driverClassName;

    /**
     * url地址
     */
    private String url;

    /**
     * 数据库连接名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}

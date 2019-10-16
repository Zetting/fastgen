package com.fastgen.core.base;

/**
 * 常量
 *
 * @author: zet
 * @date:2019/9/12
 */
public class Contants {
    /**
     * 基础
     */
    public static final String TRUE = "true";//true
    public static final String FALSE = "false";//false

    public static final String ENV_RELEASE = "release";//发布环境
    public static final String CHARSET_CODE = "utf-8";//默认编码
    public static final String JAVA_IO_TMPDIR = "java.io.tmpdir";//临时目录
    public static final String PREFIX_SRC_MAIN_JAVA = "\\\\src\\\\main\\\\java";//java所在目录
    public static final String PREFIX_SRC_MAIN_RESOURCES = "\\\\src\\\\main\\\\resources"; //resources所在目录


    /**
     * 文件/目录名
     */
    public static final String FILE_NAME_FGBASE = ".fgbase";//基础配置文件名
    public static final String FILE_NAME_FGCUSTOM = ".fgcustom";//项目私有个性化配置文件名
    public static final String FILE_NAME_PROJECT = "project.properties";//项目配置
    public static final String DIR_NAME_TEMPLATES = "templates";//模板配置所在目录名

    /**
     * 标记符
     */
    public static final String TAG_START_CONFIG = "<#--start_config-->";//起始标记
    public static final String TAG_END_CONFIG = "<#--end_config-->";//结束标记
    public static final String TAG_CURRENT_PATH = "./";//当前项目


    /**
     * 配置字段名
     */
    public static final String FIELD_PREFIX_FIELD_MAPPING = "field.";//字段类型映射前缀
    public static final String FIELD_PREFIX_CUSTOM_CONFIG = "custom.";//自定义配置前缀
    public static final String FIELD_PREFIX_DATASOURCE_CONFIG = "spring.datasource.druid.";//数据库配置前缀

    public static final String FIELD_FTL_CONFIG_ENABLE = "enable";//ftl中：是否有效配置
    public static final String FIELD_FTL_CONFIG_FILE_PATH = "filePath";//ftl中：生成路径配置
    public static final String FIELD_TEMPLATES = "templates";//字段名:模板
    public static final String FIELD_COVER = "cover";//字段名:是否覆盖
    public static final String FIELD_AUTHOR = "author";//字段名:作者
    public static final String FIELD_DYNAMICFORM = "dynamicForm";//:字段名:动态组件

    public static final String FIELD_DB_ISCURRENT = "isCurrent";//:字段名：是否为当前数据库
    public static final String FIELD_DB_PROJECTID = "projectId";//:字段名：项目Id
    public static final String FIELD_DB_TYPE = "spring.datasource.type";//:字段名:数据库类型
    public static final String FIELD_DB_DRIVERCLASSNAME = "driverClassName";//:字段名:数据库驱动类名
    public static final String FIELD_DB_URL = "url";//:字段名:数据库url
    public static final String FIELD_DB_USERNAME = "username";//:字段名:数据库用户名
    public static final String FIELD_DB_PASSWORD = "password";//:字段名:数据库密码
}

package com.fastgen.core.base;

/**
 * 常量
 *
 * @author: zet
 * @date:2019/9/12
 */
public class Contants {
    public static final String USER_CFG = "user-cfg.properties";//用户配置文件名
    public static final String TEMPLATES_PATH_NAME = "templates";//模板配置所在目录名
    public static final String TAG_START_CONFIG = "<#--start_config-->";//起始标记
    public static final String TAG_END_CONFIG = "<#--end_config-->";//结束标记

    public static final String FTL_CONFIG_FILE_PATH = "filePath";//生成路径配置
    public static final String FTL_CONFIG_ENABLE = "enable";//是否有效配置

    public static final String TRUE = "true";//true
    public static final String FALSE = "false";//false

    public static final String ENV_RELEASE = "release";//发布环境
    public static final String CHARSET_CODE = "utf-8";//默认编码
    public static final String JAVA_IO_TMPDIR = "java.io.tmpdir";//临时目录
    public static final String PREFIX_SRC_MAIN_JAVA = "\\\\src\\\\main\\\\java";//java所在目录
    public static final String PREFIX_SRC_MAIN_RESOURCES = "\\\\src\\\\main\\\\resources"; //resources所在目录


    public static final String FIELD_NAME_TEMPLATES="templates";//字段名:模板
    public static final String FIELD_NAME_COVER="cover";//字段名:是否覆盖
    public static final String FIELD_NAME_AUTHOR="author";//字段名:作者
    public static final String FIELD_NAME_DYNAMICFORM="dynamicForm";//:字段名:动态组件
}

package com.fastgen.core.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统常量枚举
 *
 * @author: zet
 * @date:2019/10/10
 */
@Getter
@AllArgsConstructor
public enum SysVariableEnum {
    GROUP_NAME("groupName", "分组名称"),
    AUTHOR("author", "作者"),
    DATE("date", "当前日期"),
    TABLE_NAME("tableName", "表名"),
    TABLE_COMMENT("tableComment", "表备注"),
    CLASS_NAME("className", "类名"),
    FIRST_LOWER_CLASS_NAME("firstLowerClassName", "首字符小写类名"),
    UPPER_CASE_CLASS_NAME("upperCaseClassName", "大写类名"),
    CAMEL_CASE_CLASS_NAME("camelCaseClassName", "驼峰类名"),

    COLUMNS("columns", "列Key"),
    HAS_QUERY("hasQuery", "是否有搜索"),
    HAS_TIMESTAMP("hasTimestamp", "是否含Timestamp"),
    HAS_BIGDECIMAL("hasBigDecimal", "是否含BigDecimal"),
    HAS_AUTO("hasAuto", "是否有自增类型"),


    COLUMN_COLUMN_COMMENT("columnComment", "字段备注"),
    COLUMN_COLUMN_KEY("columnKey", " 列类型,PRI为主键"),
    COLUMN_PKCOLUMNTYPE("pkColumnType", "主键字段类型"),
    COLUMN_PKCOLCAMELCASENAME("pkColCamelCaseName", "主键驼峰名"),
    COLUMN_PKCAPITALCOLNAME("pkCapitalColName", "主键驼峰名（首字符大写）"),
    COLUMN_UNDERSCORECASECOLUMNNAME("underScoreCaseColumnName", "toUnderScoreCase(\"helloWorld\") = \"hello_world\""),
    COLUMN_COLUMNTYPE("columnType", "字段类型"),
    COLUMN_COLUMNNAME("columnName", "字段名"),
    COLUMN_ISNULLABLE("isNullable", "是否非空"),
    COLUMN_COLUMNSHOW("columnShow", "是否显示"),
    COLUMN_COLUMNQUERY("columnQuery", " 查询 1:模糊 2：精确"),
    COLUMN_QUERYCOLUMNS("queryColumns", "搜索字段");


    /**
     * 变量名
     */
    private String name;
    /**
     * 备注说明
     */
    private String remark;
}

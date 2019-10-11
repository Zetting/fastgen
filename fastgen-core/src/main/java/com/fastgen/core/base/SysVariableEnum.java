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


    COL_COMMENT("colComment", "列字段备注"),
    COL_KEY("colKey", " 列类型,PRI为主键"),
    COL_PKTYPE("colPkType", "主键字段类型"),
    COL_CAMELCASENAME("colCamelCaseName", "列名驼峰名"),
    COL_CAPITALCOLNAME("colCapitalColName", "列名驼峰名（首字符大写）"),
    COL_UNDERSCORECASECOLUMNNAME("colUnderScoreCaseColumnName", "toUnderScoreCase(\"helloWorld\") = \"hello_world\""),
    COL_TYPE("colType", "列字段类型"),
    COL_NAME("colName", "列字段名"),
    COL_ISNULLABLE("colIsNullable", "列是否非空"),
    COL_ISSHOW("colIsShow", "列是否显示"),
    COL_QUERYTYPE("colQueryType", "列查询 1:模糊 2：精确"),
    QUERYCOLUMNS("queryColumns", "列搜索字段");


    /**
     * 变量名
     */
    private String name;
    /**
     * 备注说明
     */
    private String remark;
}

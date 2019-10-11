| groupName        |   分组名称   |  ${groupName}   |
| author        |   作者   |  ${author}   |
| date        |   当前日期   |  ${date}   |
| tableName        |   表名   |  ${tableName}   |
| tableComment        |   表备注   |  ${tableComment}   |
| className        |   类名   |  ${className}   |
| firstLowerClassName        |   首字符小写类名   |  ${firstLowerClassName}   |
| upperCaseClassName        |   大写类名   |  ${upperCaseClassName}   |
| camelCaseClassName        |   驼峰类名   |  ${camelCaseClassName}   |
<#if columns??>
    <#list columns as column>
| columnComment        |   字段备注   |  ${column.columnComment}   |
| columnKey        |   列类型,PRI为主键   |  ${column.columnKey}   |
        <#if column.columnKey != 'PRI'>
| pkColumnType        |   主键字段类型   |  ${column.pkColumnType!}   |
| capitalColName        |   主键驼峰名（首字符大写）   |  ${column.capitalColName!}   |
        </#if>
| columnName        |   列字段名   |  ${column.columnName}   |
| isNullable        |   列是否非空   |  ${column.isNullable}   |
| columnShow        |   列是否显示   |  ${column.columnShow}   |
| columnQuery        |   列查询 1:模糊 2：精确"   |  ${column.columnQuery!}   |
| underScoreCaseColumnName        |   toUnderScoreCase("helloWorld") = "hello_world\   |  ${column.underScoreCaseColumnName}   |
    </#list>
</#if>

<#--start_config-->
#是否有效
enable=true
#文件生成路径
filePath=${serverPath}\\Sys-Variable.txt
<#--end_config-->

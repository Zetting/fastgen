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
| columnComment        |   列字段备注   |  ${column.colComment}   |
| colKey        |   列类型,PRI为主键   |  ${column.colKey}   |
        <#if column.colKey != 'PRI'>
| pkColumnType        |   主键字段类型   |  ${column.colPkType}   |
        </#if>
| colCamelCaseName        |   列名驼峰名   |  ${column.colCamelCaseName}   |
| colCapitalColName        |   列名驼峰名（首字符大写）   |  ${column.colCapitalColName}   |
| colUnderScoreCaseColumnName        |   列名下划线格式   |  ${column.colUnderScoreCaseColumnName}   |
| colType        |   列字段类型   |  ${column.colType}   |
| colName        |   列字段名   |  ${column.colName}   |
| colIsNullable        |   列是否非空   |  ${column.colIsNullable}   |
| colIsShow        |   列是否显示   |  ${column.colIsShow}   |
| colQueryType        |   列查询 1:模糊 2：精确"   |  ${column.colQueryType!}   |
        <#break>
    </#list>
</#if>

<#--start_config-->
#是否有效
enable=true
#文件生成路径
filePath=${serverPath}\\Sys-Variable.txt
<#--end_config-->

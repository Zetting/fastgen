<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${package}.${groupName}.dao.${className}Dao">
    <#if genMode = 'admin'>
    <select id="listPages" resultType="${package}.${groupName}.contract.vo.${className}PageVO">
        select * from ${tableName} where 1=1
<#if queryColumns??>
   <#list queryColumns as column>
     <#if column.columnQuery = '1'>
         <if test="query.${column.changeColumnName} != null">
         and ${column.underScoreCaseColumnName} like concat("%",<#noparse>#{</#noparse>query.${column.changeColumnName}<#noparse>}</#noparse>,"%")
         </if>
    </#if>
    <#if column.columnQuery = '2'>
        <if test="query.${column.changeColumnName} != null">
        and ${column.underScoreCaseColumnName} = <#noparse>#{</#noparse>query.${column.changeColumnName}<#noparse>}</#noparse>
        </if>
    </#if>
   </#list>
</#if>
    </select>
    </#if>
</mapper>
<#--start_config-->
#是否有效
enable=true
#文件生成路径
filePath=${resourcesPath}\\dao\\${groupName}\\${className}Dao.xml
<#--end_config-->

package ${package}.${groupName}.entity;

import lombok.Builder;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
<#if hasBigDecimal>
import java.math.BigDecimal;
</#if>
import java.io.Serializable;

/**
* ${tableComment}-实体对象
* @author ${author}
* @date ${date}
*/
@Builder
@Data
public class ${className} implements Serializable{
<#if columns??>
    <#list columns as column>

    @ApiModelProperty("${column.colComment}")
    private ${column.colType} ${column.colCamelCaseName};
    </#list>
</#if>
}
<#--start_config-->
#是否有效
enable=true
#文件生成路径
filePath=${serverPath}\\src\\main\\java\\${packagePath}\\${groupName}\\entity\\${className}.java
<#--end_config-->

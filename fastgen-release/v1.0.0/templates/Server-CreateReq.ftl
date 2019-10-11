package ${package}.${groupName}.contract.req;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
<#if hasBigDecimal>
import java.math.BigDecimal;
</#if>

/**
* ${tableComment}-创建请求
* @author ${author}
* @date ${date}
*/
@Data
public class ${className}CreateReq{
<#if columns??>
<#list columns as column>
   <#if column.colKey != 'PRI'>

    @ApiModelProperty("${column.colComment}")
    private ${column.colType} ${column.colCamelCaseName};
   </#if>
    </#list>
</#if>
}
<#--start_config-->
#是否有效
enable=true
#文件生成路径
filePath=${serverPath}\\src\\main\\java\\${packagePath}\\${groupName}\\contract\\req\\${className}CreateReq.java
<#--end_config-->

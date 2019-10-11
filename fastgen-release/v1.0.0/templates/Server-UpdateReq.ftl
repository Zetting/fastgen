package ${package}.${groupName}.contract.req;

import lombok.Data;
import javax.persistence.*;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
<#if hasBigDecimal>
import java.math.BigDecimal;
</#if>
import java.io.Serializable;

/**
* ${tableComment}-更新请求
* @author ${author}
* @date ${date}
*/
@Data
public class ${className}UpdateReq{
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
filePath=${serverPath}\\src\\main\\java\\${packagePath}\\${groupName}\\contract\\req\\${className}UpdateReq.java
<#--end_config-->

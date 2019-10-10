package ${package}.${groupName}.contract.vo;

import lombok.Data;
import javax.persistence.*;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
<#if hasBigDecimal>
import java.math.BigDecimal;
</#if>
import java.io.Serializable;

/**
* ${tableComment}-获取响应
* @author ${author}
* @date ${date}
*/
@Data
public class ${className}GetVO {
<#if columns??>
    <#list columns as column>

    @ApiModelProperty("${column.columnComment}")
    private ${column.columnType} ${column.changeColumnName};
    </#list>
</#if>
}
<#--start_config-->
#是否有效
enable=true
#文件生成路径
filePath=${javaPath}\\${packagePath}\\${groupName}\\contract\\vo\\${className}GetVO.java
<#--end_config-->

package ${package}.${groupName}.contract.request;

import lombok.Data;
import javax.persistence.*;
import io.swagger.annotations.ApiModelProperty;
import cn.lansion.base.BaseRequest;
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
public class ${className}UpdateReq extends BaseRequest {
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
filePath=${javaPath}\\${packagePath}\\${groupName}\\contract\\request\\${className}UpdateReq.java
<#--end_config-->

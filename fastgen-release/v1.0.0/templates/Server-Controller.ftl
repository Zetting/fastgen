package ${package}.${groupName}.controller;

<#if genMode = 'api'>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
</#if>
<#if genMode = 'admin'>
import cn.lansion.base.Response;
import cn.lansion.base.PageData;
import ${package}.${groupName}.contract.request.${className}PageReq;
import ${package}.${groupName}.contract.request.${className}CreateReq;
import ${package}.${groupName}.contract.request.${className}UpdateReq;
import ${package}.${groupName}.contract.vo.${className}PageVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.domain.Pageable;
</#if>
import ${package}.${groupName}.service.${className}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* ${tableComment}-控制层
*
* @author ${author}
* @date ${date}
*/
<#if genMode = 'api'>
@Api(tags = "${tableComment}")
</#if>
@RestController
@RequestMapping("${groupName}/${changeClassName}")
public class ${className}Controller {

    @Autowired
    private ${className}Service ${changeClassName}Service;

<#if genMode = 'admin'>
    @GetMapping(value = "/listPages")
    @PreAuthorize("hasAnyRole('ADMIN','${upperCaseClassName}_ALL','${upperCaseClassName}_SELECT')")
    public Response listPages(${className}PageReq request, Pageable pageable){
        PageData<${className}PageVO> pages = ${changeClassName}Service.listPages(request, pageable);
        return Response.success(pages);
    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasAnyRole('ADMIN','${upperCaseClassName}_ALL','${upperCaseClassName}_CREATE')")
    public Response create(@RequestBody ${className}CreateReq request){
        ${changeClassName}Service.create(request);
        return Response.success();
    }

    @PostMapping(value = "/update")
    @PreAuthorize("hasAnyRole('ADMIN','${upperCaseClassName}_ALL','${upperCaseClassName}_EDIT')")
    public Response update(@RequestBody ${className}UpdateReq request){
        ${changeClassName}Service.update(request);
        return Response.success();
    }


    @GetMapping(value = "/delete/{${pkChangeColName}}")
    @PreAuthorize("hasAnyRole('ADMIN','${upperCaseClassName}_ALL','${upperCaseClassName}_DELETE')")
    public Response delete(@PathVariable ${pkColumnType} ${pkChangeColName}){
        ${changeClassName}Service.delete(${pkChangeColName});
        return Response.success();
    }
</#if>
}
<#--start_config-->
#是否有效
enable=true
#文件生成路径
filePath=${javaPath}\\${packagePath}\\${groupName}\\controller\\${className}Controller.java
<#--end_config-->

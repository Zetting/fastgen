package ${package}.${groupName}.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.fastgen.sample.action.base.Response;
import com.fastgen.sample.action.base.PageData;
import ${package}.${groupName}.contract.req.${className}PageReq;
import ${package}.${groupName}.contract.req.${className}CreateReq;
import ${package}.${groupName}.contract.req.${className}UpdateReq;
import ${package}.${groupName}.contract.vo.${className}PageVO;
//import org.springframework.security.access.prepost.PreAuthorize;;
import ${package}.${groupName}.service.${className}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* ${tableComment}-控制层
*
* @author ${author}
* @date ${date}
*/
@Api(tags = "${tableComment}")
@RestController
@RequestMapping("${groupName}/${camelCaseClassName}")
public class ${className}Controller {

    @Autowired
    private ${className}Service ${camelCaseClassName}Service;

    @GetMapping(value = "/listPages")
    @ApiOperation("${tableComment}-分页")
    public Response listPages(${className}PageReq request){
        PageData<${className}PageVO> pages = ${camelCaseClassName}Service.listPages(request);
        return Response.success(pages);
    }

    @PostMapping(value = "/create")
    @ApiOperation("${tableComment}-新增")
    public Response create(@RequestBody ${className}CreateReq request){
        ${camelCaseClassName}Service.create(request);
        return Response.success();
    }

    @PostMapping(value = "/update")
    @ApiOperation("${tableComment}-更新")
    public Response update(@RequestBody ${className}UpdateReq request){
        ${camelCaseClassName}Service.update(request);
        return Response.success();
    }


    @GetMapping(value = "/delete/{id}")
    @ApiOperation("${tableComment}-删除")
    public Response delete(@PathVariable ${colPkType} id){
        ${camelCaseClassName}Service.delete(id);
        return Response.success();
    }
}
<#--start_config-->
#是否有效
enable=true
#文件生成路径
filePath=${serverPath}\\src\\main\\java\\${packagePath}\\${groupName}\\controller\\${className}Controller.java
<#--end_config-->

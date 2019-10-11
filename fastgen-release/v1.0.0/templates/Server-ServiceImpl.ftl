package ${package}.${groupName}.service.impl;

import com.fastgen.sample.action.base.PageData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${package}.${groupName}.entity.${className};
import ${package}.${groupName}.service.${className}Service;
import ${package}.${groupName}.dao.${className}Dao;
import ${package}.${groupName}.contract.req.${className}PageReq;
import ${package}.${groupName}.contract.req.${className}CreateReq;
import ${package}.${groupName}.contract.req.${className}UpdateReq;
import ${package}.${groupName}.contract.vo.${className}GetVO;
import ${package}.${groupName}.contract.vo.${className}PageVO;
import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.List;

/**
* ${tableComment}-服务实现类
*
* @author ${author}
* @date ${date}
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ${className}ServiceImpl extends ServiceImpl<${className}Dao, ${className}> implements ${className}Service {
    @Autowired
    private ${className}Dao  ${camelCaseClassName}Dao;

    @Override
    public PageData<${className}PageVO>  listPages(${className}PageReq request){
        Page page = new Page(request.getPageNumber(), request.getPageSize());
        List<${className}PageVO> list = ${camelCaseClassName}Dao.listPages(page,request);
        return new PageData(page.getTotal(),list);
    }

    @Override
    public ${className}GetVO findById(${colPkType} id) {
        ${className}GetVO response = new ${className}GetVO();
        ${className} ${camelCaseClassName} =  ${camelCaseClassName}Dao.selectById(id);
        BeanUtils.copyProperties(${camelCaseClassName}, response);
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(${className}CreateReq request) {
        ${className} ${camelCaseClassName} = ${className}.builder().build();
        BeanUtils.copyProperties(request, ${camelCaseClassName});
        ${camelCaseClassName}Dao.insert(${camelCaseClassName});
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(${className}UpdateReq request) {
        ${className} ${camelCaseClassName} = ${className}.builder().build();
        BeanUtils.copyProperties(request, ${camelCaseClassName});
        ${camelCaseClassName}Dao.updateById(${camelCaseClassName});
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(${colPkType} id) {
        ${camelCaseClassName}Dao.deleteById(id);
    }
}
<#--start_config-->
#是否有效
enable=true
#文件生成路径
filePath=${serverPath}\\src\\main\\java\\${packagePath}\\${groupName}\\service\\impl\\${className}ServiceImpl.java
<#--end_config-->

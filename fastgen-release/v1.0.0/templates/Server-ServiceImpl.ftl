package ${package}.${groupName}.service.impl;

import cn.lansion.base.PageData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.quickcanteen.domain.${groupName}.${className}DO;

import ${package}.${groupName}.service.${className}Service;
import ${package}.${groupName}.dao.${className}Dao;
<#if genMode = 'admin'>
import ${package}.${groupName}.contract.request.${className}PageReq;
import ${package}.${groupName}.contract.request.${className}CreateReq;
import ${package}.${groupName}.contract.request.${className}UpdateReq;
import ${package}.${groupName}.contract.vo.${className}GetVO;
import ${package}.${groupName}.contract.vo.${className}PageVO;
import java.util.Optional;
import java.util.List;
import org.springframework.data.domain.Pageable;

</#if>
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
public class ${className}ServiceImpl extends ServiceImpl<${className}Dao, ${className}DO> implements ${className}Service {
<#if genMode = 'admin'>
    @Autowired
    private ${className}Dao  ${changeClassName}Dao;

    @Override
    public PageData<${className}PageVO>  listPages(${className}PageReq request, Pageable pageable){
        Page page = new Page(request.getPageNumber(), request.getPageSize());
        List<${className}PageVO> list = ${changeClassName}Dao.listPages(page,request);
        return new PageData(page.getTotal(),list);
    }

    @Override
    public ${className}GetVO findById(${pkColumnType} ${pkChangeColName}) {
        ${className}GetVO response = new ${className}GetVO();
        ${className}DO ${changeClassName} =  ${changeClassName}Dao.selectById(id);
        BeanUtils.copyProperties(${changeClassName}, response);
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(${className}CreateReq request) {
        ${className}DO ${changeClassName} = ${className}DO.builder().build();
        BeanUtils.copyProperties(request, ${changeClassName});
        ${changeClassName}Dao.insert(${changeClassName});
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(${className}UpdateReq request) {
        ${className}DO ${changeClassName} = ${className}DO.builder().build();
        BeanUtils.copyProperties(request, ${changeClassName});
        ${changeClassName}Dao.updateById(${changeClassName});
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(${pkColumnType} ${pkChangeColName}) {
        ${changeClassName}Dao.deleteById(${pkChangeColName});
    }
</#if>
}
<#--start_config-->
#是否有效
enable=true
#文件生成路径
filePath=${javaPath}\\${packagePath}\\${groupName}\\service\\impl\\${className}ServiceImpl.java
<#--end_config-->

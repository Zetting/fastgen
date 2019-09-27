package ${package}.${groupName}.service;

import cn.lansion.base.PageData;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.quickcanteen.domain.${groupName}.${className}DO;
<#if genMode = 'admin'>
import ${package}.${groupName}.contract.request.${className}PageReq;
import ${package}.${groupName}.contract.request.${className}CreateReq;
import ${package}.${groupName}.contract.request.${className}UpdateReq;
import ${package}.${groupName}.contract.vo.${className}GetVO;
import ${package}.${groupName}.contract.vo.${className}PageVO;
</#if>
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
* ${tableComment}-服务类
* @author ${author}
* @date ${date}
*/
public interface ${className}Service extends IService<${className}DO>{
<#if genMode = 'admin'>
    /**
    * 分页
    * @param request
    * @param pageable
    * @return
    */
    PageData<${className}PageVO> listPages(${className}PageReq request, Pageable pageable);


    /**
     * 获取${tableComment}
     * @param ${pkChangeColName}
     * @return
     */
    ${className}GetVO findById(${pkColumnType} ${pkChangeColName});

    /**
     * 新增${tableComment}
     * @param request
     * @return
     */
    void create(${className}CreateReq request);

    /**
     * 更新${tableComment}
     * @param request
     */
    void update(${className}UpdateReq request);

    /**
     * 删除${tableComment}
     * @param ${pkChangeColName}
     */
    void delete(${pkColumnType} ${pkChangeColName});
</#if>
}
<#--start_config-->
#是否有效
enable=true
#文件生成路径
filePath=${javaPath}\\${packagePath}\\${groupName}\\service\\${className}Service.java
<#--end_config-->

package ${package}.${groupName}.service;

import com.fastgen.sample.action.base.PageData;
import com.baomidou.mybatisplus.extension.service.IService;
import ${package}.${groupName}.entity.${className};
import ${package}.${groupName}.contract.req.${className}PageReq;
import ${package}.${groupName}.contract.req.${className}CreateReq;
import ${package}.${groupName}.contract.req.${className}UpdateReq;
import ${package}.${groupName}.contract.vo.${className}GetVO;
import ${package}.${groupName}.contract.vo.${className}PageVO;
import java.util.List;

/**
* ${tableComment}-服务类
* @author ${author}
* @date ${date}
*/
public interface ${className}Service extends IService<${className}>{
    /**
    * ${tableComment} 分页
    * @param request
    * @return
    */
    PageData<${className}PageVO> listPages(${className}PageReq request);


    /**
     * 获取${tableComment}
     * @param id
     * @return
     */
    ${className}GetVO findById(${colPkType} id);

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
     * @param id
     */
    void delete(${colPkType} id);
}
<#--start_config-->
#是否有效
enable=true
#文件生成路径
filePath=${serverPath}\\src\\main\\java\\${packagePath}\\${groupName}\\service\\${className}Service.java
<#--end_config-->

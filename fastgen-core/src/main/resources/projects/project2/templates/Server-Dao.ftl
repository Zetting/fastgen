package ${package}.${groupName}.dao;

import ${package}.${groupName}.contract.req.${className}PageReq;
import ${package}.${groupName}.contract.vo.${className}PageVO;
import ${package}.${groupName}.entity.${className};
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

/**
* ${tableComment}-持久层
*
* @author ${author}
* @date ${date}
*/
public interface ${className}Dao extends BaseMapper<${className}> {
   List<${className}PageVO> listPages(IPage page,@Param("query") ${className}PageReq query);
}

<#--start_config-->
#是否有效
enable=true
#文件生成路径
filePath=${serverPath}\\src\\main\\java\\${packagePath}\\${groupName}\\dao\\${className}Dao.java
<#--end_config-->

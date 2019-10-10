package ${package}.${groupName}.dao;

<#if genMode = 'admin'>
import ${package}.${groupName}.contract.request.${className}PageReq;
import ${package}.${groupName}.contract.vo.${className}PageVO;
</#if>
import cn.quickcanteen.domain.${groupName}.${className}DO;
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
public interface ${className}Dao extends BaseMapper<${className}DO> {
<#if genMode = 'admin'>
    List<${className}PageVO> listPages(IPage page,@Param("query") ${className}PageReq query);
</#if>
}

<#--start_config-->
#是否有效
enable=true
#文件生成路径
filePath=${javaPath}\\${packagePath}\\${groupName}\\dao\\${className}Dao.java
<#--end_config-->

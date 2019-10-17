package com.fastgen.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fastgen.core.entity.ProjectFieldMapping;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProjectDynamicParamsMapper extends BaseMapper<ProjectFieldMapping> {
}

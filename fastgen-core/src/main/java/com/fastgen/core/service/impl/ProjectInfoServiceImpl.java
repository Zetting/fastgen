package com.fastgen.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastgen.core.entity.ProjectInfo;
import com.fastgen.core.mapper.ProjectInfoMapper;
import com.fastgen.core.service.ProjectInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 项目信息-服务实现类
 *
 * @author: zet
 * @date:2019/10/17
 */
@Slf4j
@Service
public class ProjectInfoServiceImpl extends ServiceImpl<ProjectInfoMapper, ProjectInfo>
        implements ProjectInfoService {


}

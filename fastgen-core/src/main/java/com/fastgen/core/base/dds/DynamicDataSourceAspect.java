package com.fastgen.core.base.dds;

import com.fastgen.core.contract.BaseConfigItem;
import com.fastgen.core.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * controller 层入参校验切面
 *
 * @Author linzetian
 * @Date 2017/11/15 21:50
 **/
@Slf4j
@Order(2)
@Component
@Aspect
public class DynamicDataSourceAspect {
    @Autowired
    private ConfigService configService;

    public DynamicDataSourceAspect() {
    }

    /**
     * 切入点
     */
    @Pointcut(
            "@annotation(org.springframework.web.bind.annotation.RequestMapping)" +
                    "||@annotation(org.springframework.web.bind.annotation.PostMapping)" +
                    "||@annotation(org.springframework.web.bind.annotation.PutMapping)" +
                    "||@annotation(org.springframework.web.bind.annotation.DeleteMapping)" +
                    "||@annotation(org.springframework.web.bind.annotation.GetMapping)"
    )
    private void pointCut() {
    }

    /**
     * 处理
     *
     * @param point
     */
    @Before("pointCut()")
    public void setDataSourceRouterKey(JoinPoint point) {
        BaseConfigItem baseConfigItem = configService.getCurrentBaseConfig();
        String dsId = baseConfigItem.getProjectId();
        if (DynamicDataSourceContextHolder.dataSourceIds.contains(dsId)) {
            log.debug("Use DataSource :{} >", dsId, point.getSignature());
            DynamicDataSourceContextHolder.setDataSourceRouterKey(dsId);
        } else {
            log.info("数据源[{}]不存在，使用默认数据源 >{}", dsId, point.getSignature());
        }
    }

    @After("pointCut()")
    public void removeDataSourceRouterKey(JoinPoint point) {
//        log.debug("Revert DataSource : " + ds.value() + " > " + point.getSignature());
        DynamicDataSourceContextHolder.removeDataSourceRouterKey();
    }
}

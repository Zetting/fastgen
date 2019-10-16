package com.fastgen.core.base.dds;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @Auther: yukong
 * @Date: 2018/8/15 10:47
 * @Description: 动态数据源路由配置
 */
@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceName = DynamicDataSourceContextHolder.getDataSourceRouterKey();
        log.debug("当前数据源是：{}", dataSourceName);
        return DynamicDataSourceContextHolder.getDataSourceRouterKey();
    }
}

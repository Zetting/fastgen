package com.fastgen.sample.configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author miemie
 * @since 2018-08-10
 */
@Configuration
@MapperScan(basePackages = {
        " com.fastgen.sample.modules.*.dao"
})
@EntityScan(basePackages = {"com.fastgen.sample.*"})
@ComponentScan(basePackages = {"com.fastgen.sample.*"})
public class ScanConfigureration {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        // 开启 count 的 join 优化,只针对 left join !!!
        return new PaginationInterceptor().setCountSqlParser(new JsqlParserCountOptimize(true));
    }
}

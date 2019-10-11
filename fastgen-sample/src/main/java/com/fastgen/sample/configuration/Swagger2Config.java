package com.fastgen.sample.configuration;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2API文档的配置
 * Created by zet on 2019/4/26.
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Value("${swagger.enable:true}")
    private Boolean enable;
    @Value("${jwt.header:Authorization}")
    private String header;
    @Value("${swagger.host:}")
    private String swaggerHost;

    @Bean
    public Docket platformApi() {
        Docket docket =  new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).forCodeGeneration(true)
                .enable(enable)
                .select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("^.*(?<!error)$"))
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
        if (swaggerHost != null && swaggerHost != "") {
            docket.host(swaggerHost);
        }
        return docket;
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("fastgen-管理后台Api")
                .description("管理后台")
                .version("1.0")
                .build();
    }

    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList= new ArrayList();
        apiKeyList.add(new ApiKey(header, header, "header"));
        return apiKeyList;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts=new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build());
        return securityContexts;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences=new ArrayList<>();
        securityReferences.add(new SecurityReference(header, authorizationScopes));
        return securityReferences;
    }
}

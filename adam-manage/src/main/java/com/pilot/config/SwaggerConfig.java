package com.pilot.config;

import com.pilot.entity.property.SwaggerBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Resource
    private SwaggerBean swaggerBean;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerBean.getBasePackage()))
                .paths(PathSelectors.any())
                .build();
    }

    public ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerBean.getTitle())
                .contact(new Contact(swaggerBean.getContactName(), swaggerBean.getContactUrl(), swaggerBean.getContactEmail()))
                .version(swaggerBean.getVersion())
                .description(swaggerBean.getDescription())
                .build();
    }
}

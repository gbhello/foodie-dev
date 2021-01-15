package com.imooc.controller.config;

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

/**
 * @author gengbin
 * @date 2021/1/15
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
    @Bean
    public Docket createRestApi() {
        // 指定api类型为swagger2
        return new Docket(DocumentationType.SWAGGER_2)
                // 用于定义api文档汇总信息
                .apiInfo(apiInfo())
                .select()
                // 指定controller包
                .apis(RequestHandlerSelectors.basePackage("com.imooc.controller"))
                // 所有controller
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 文档页标题
                .title("天天吃货 电商平台接口api")
                // 联系人信息
                .contact(new Contact("imooc", "https://www.imooc.com", "abc@imooc.com"))
                // 详细信息
                .description("天天吃货api文档")
                // 文档版本号
                .version("1.0.1")
                // 网站地址
                .termsOfServiceUrl("https.//www.imooc.com")
                .build();
    }
}

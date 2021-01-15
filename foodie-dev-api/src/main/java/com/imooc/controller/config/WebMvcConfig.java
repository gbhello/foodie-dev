package com.imooc.controller.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author gengbin
 * @date 2021/1/15
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 实现静态资源的映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                // 映射swagger2
                .addResourceLocations("classpath:/META-INF/resources/")
                // 映射
                .addResourceLocations("file:/workspaces/images/");
    }

    @Bean
    public RestTemplate reatTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}

package com.imooc.resource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author gengbin
 * @date 2021/2/12
 */
@Component
@ConfigurationProperties(prefix = "file")
@PropertySource("classpath:file-upload-prod.properties")
@Data
public class FileUpload {
    private String imageUserFaceLocation;
    private String imagServerUrl;
}

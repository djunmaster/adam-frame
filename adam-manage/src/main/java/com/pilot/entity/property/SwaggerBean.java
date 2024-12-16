package com.pilot.entity.property;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "swagger")
public class SwaggerBean {
    private String basePackage;

    private String title;

    private String contactName;

    private String contactUrl;

    private String contactEmail;

    private String version;

    private String description;
}
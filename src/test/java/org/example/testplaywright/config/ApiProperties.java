package org.example.testplaywright.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "api")
@ConfigurationPropertiesScan
public class ApiProperties {
    private String baseUrl;
    private Map<String, String> defaultHeaders = new HashMap<>();
    private double timeout;
}

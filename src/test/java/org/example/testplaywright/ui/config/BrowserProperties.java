package org.example.testplaywright.ui.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@Getter
@Setter
@ConfigurationProperties(prefix = "browser")
@ConfigurationPropertiesScan
public class BrowserProperties {
    private String type;
    private boolean headless = false;
    private int viewportWidth = 1920;
    private int viewportHeight = 1080;
    private String videoDir = "test-results/videos/";
    private boolean traceScreenshots = true;
    private boolean traceSnapshots = true;
    private boolean traceSources = false;
    private String baseUrl;
    private double timeout;

}

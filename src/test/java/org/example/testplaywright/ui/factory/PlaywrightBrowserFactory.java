package org.example.testplaywright.ui.factory;

import com.microsoft.playwright.*;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import org.example.testplaywright.ui.config.BrowserProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

@Component
public class PlaywrightBrowserFactory {

    private final BrowserProperties browserProperties;


    public PlaywrightBrowserFactory(BrowserProperties browserProperties) {
        this.browserProperties = browserProperties;
    }


    public Browser createBrowser(Playwright playwright) {
        return switch (browserProperties.getType()) {
            case "firefox" -> playwright.firefox()
                    .launch(new BrowserType.LaunchOptions()
                            .setHeadless(browserProperties.isHeadless()));
            case "webkit" -> playwright.webkit()
                    .launch(new BrowserType.LaunchOptions()
                            .setHeadless(browserProperties.isHeadless()));
            default -> playwright.chromium()
                    .launch(new BrowserType.LaunchOptions()
                            .setChannel("chrome")
                            .setHeadless(browserProperties.isHeadless()));
        };
    }
}

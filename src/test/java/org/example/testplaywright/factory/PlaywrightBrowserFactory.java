package org.example.testplaywright.factory;

import com.microsoft.playwright.*;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import org.example.testplaywright.config.BrowserProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
@Component
@ScenarioScope
public class PlaywrightBrowserFactory {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;
    private final BrowserProperties browserProperties;

    public PlaywrightBrowserFactory(BrowserProperties browserProperties) {
        this.browserProperties = browserProperties;
    }

    public Page getPage() {
        if (page == null) {
            throw new IllegalStateException("Browser not initialized. Call init() first.");
        }
        return page;
    }

    public BrowserContext getContext() {
        if (context == null) {
            throw new IllegalStateException("Browser not initialized. Call init() first.");
        }
        return context;
    }

    public Page init(String browserName) {
        playwright = Playwright.create();
        browser = createBrowser(playwright, browserName);
        context = createContext(browser);
        page = context.newPage();
        page.setDefaultTimeout(browserProperties.getTimeout());
        return page;
    }

    private Browser createBrowser(Playwright playwright, String browserName) {
        return switch (browserName.toLowerCase()) {
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

    private BrowserContext createContext(Browser browser) {
        this.context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(
                        browserProperties.getViewportWidth(),
                        browserProperties.getViewportHeight()
                )
                .setRecordVideoDir(Paths.get(browserProperties.getVideoDir())));

        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(browserProperties.isTraceScreenshots())
                .setSnapshots(browserProperties.isTraceSnapshots())
                .setSources(browserProperties.isTraceSources()));

        return context;
    }

    @PreDestroy
    public void cleanup() {
        if (page != null) {
            page.close();
            page = null;
        }
        if (context != null) {
            context.close();
            context = null;
        }
        if (browser != null) {
            browser.close();
            browser = null;
        }
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }
}

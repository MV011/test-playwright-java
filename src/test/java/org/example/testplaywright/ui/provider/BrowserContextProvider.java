package org.example.testplaywright.ui.provider;

import com.microsoft.playwright.*;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import org.example.testplaywright.ui.config.BrowserProperties;
import org.example.testplaywright.ui.factory.PlaywrightBrowserFactory;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

@Component
@ScenarioScope
public class BrowserContextProvider {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page currentPage;

    private final PlaywrightBrowserFactory browserFactory;
    private final BrowserProperties browserProperties;

    public BrowserContextProvider(PlaywrightBrowserFactory browserFactory,
                                  BrowserProperties browserProperties) {
        this.browserFactory = browserFactory;
        this.browserProperties = browserProperties;
    }

    public Page init() {
        playwright = Playwright.create();
        browser = browserFactory.createBrowser(playwright);
        context = createContext(browser);
        currentPage = context.newPage();
        currentPage.setDefaultTimeout(browserProperties.getTimeout());
        return currentPage;
    }

    public Page getPage() {
        if (currentPage == null) {
            throw new IllegalStateException("Browser not initialized. Call init() first.");
        }
        return currentPage;
    }

    public BrowserContext getContext() {
        if (context == null) {
            throw new IllegalStateException("Browser not initialized. Call init() first.");
        }
        return context;
    }

    public Page openNewTab() {
        if (context == null) {
            throw new IllegalStateException("Browser not initialized. Call init() first.");
        }
        currentPage = context.newPage();
        currentPage.setDefaultTimeout(browserProperties.getTimeout());
        return currentPage;
    }

    public void closeCurrentTab() {
        if (context == null) {
            throw new IllegalStateException("Browser not initialized. Call init() first.");
        }
        currentPage.close();

        if(!context.pages().isEmpty()) {
            currentPage = context.pages().getLast();
            currentPage.bringToFront();
        }
    }

    public Page switchToTab(int index) {
        if (context == null) {
            throw new IllegalStateException("Browser not initialized. Call init() first.");
        }
        currentPage = context.pages().get(index);
        currentPage.setDefaultTimeout(browserProperties.getTimeout());
        currentPage.bringToFront();
        return currentPage;
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

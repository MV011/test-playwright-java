package org.example.testplaywright.hooks;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.spring.ScenarioScope;
import lombok.extern.slf4j.Slf4j;
import org.example.testplaywright.api.client.PlaywrightApiClient;
import org.example.testplaywright.api.config.ApiProperties;
import org.example.testplaywright.ui.config.BrowserProperties;
import org.example.testplaywright.ui.factory.PlaywrightBrowserFactory;
import org.example.testplaywright.ui.provider.BrowserContextProvider;
import org.springframework.stereotype.Component;
import org.testng.SkipException;

import java.nio.file.Paths;


//@ScenarioScope
@Slf4j
public class Hooks {

    private final BrowserContextProvider browserContextProvider;
    private final ApiProperties apiProperties;
    private final BrowserProperties browserProperties;
    private final PlaywrightApiClient playwrightApiClient;

    public Hooks(BrowserContextProvider browserContextProvider, ApiProperties apiProperties, PlaywrightApiClient playwrightApiClient, BrowserProperties browserProperties) {
        this.browserContextProvider = browserContextProvider;
        this.apiProperties = apiProperties;
        this.playwrightApiClient = playwrightApiClient;
        this.browserProperties = browserProperties;
    }

    @Before("@Ignore")
    public void skip_scenario(Scenario scenario){
        log.info("SKIP SCENARIO: " + scenario.getName());
        throw new SkipException("Test ignored via @Ignore tag");
    }

    @Before(order = 1, value = "@UI")
    public void launchBrowser() {
        Page page = browserContextProvider.init();
        page.navigate(browserProperties.getBaseUrl());
    }

    @Before(order = 1, value = "@API")
    public void createApiContext() {
        playwrightApiClient.setApiRequestContext(
                apiProperties.getBaseUrl(),
                apiProperties.getDefaultHeaders(),
                apiProperties.getTimeout()
        );
    }

    @After(order = 1, value = "@API")
    public void cleanupApi() {
        playwrightApiClient.cleanup();
    }

    @After(order = 1, value= "@UI")
    public void takeScreenshotAndTrace(Scenario scenario) {
        if (scenario.isFailed()) {
            String screenshotName = scenario.getName().replaceAll("", "_");
            byte[] sourcePath = browserContextProvider.getPage().screenshot();
            scenario.attach(sourcePath, "image/png", screenshotName);
            browserContextProvider.getContext().tracing().stop(new Tracing.StopOptions().setPath(Paths.get("target/" + screenshotName + ".zip")));
        }
    }

    @After(order = 2, value = "@UI")
    public void cleanupBrowser() {
        browserContextProvider.cleanup();
    }

}

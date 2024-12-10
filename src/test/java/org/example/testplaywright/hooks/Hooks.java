package org.example.testplaywright.hooks;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.spring.ScenarioScope;
import lombok.extern.slf4j.Slf4j;
import org.example.testplaywright.client.PlaywrightApiClient;
import org.example.testplaywright.config.ApiProperties;
import org.example.testplaywright.config.BrowserProperties;
import org.example.testplaywright.factory.PlaywrightBrowserFactory;
import org.testng.SkipException;

import java.nio.file.Paths;


@ScenarioScope
@Slf4j
public class Hooks {

    private final PlaywrightBrowserFactory playwrightBrowserFactory;
    private final ApiProperties apiProperties;
    private final BrowserProperties browserProperties;
    private final PlaywrightApiClient playwrightApiClient;

    public Hooks(PlaywrightBrowserFactory playwrightBrowserFactory, ApiProperties apiProperties, PlaywrightApiClient playwrightApiClient, BrowserProperties browserProperties) {
        this.playwrightBrowserFactory = playwrightBrowserFactory;
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
        Page page = playwrightBrowserFactory.init(browserProperties.getType());
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

    @After(order = 1, value = "@UI")
    public void cleanupBrowser() {
        playwrightBrowserFactory.cleanup();
    }

    @After(order = 1, value = "@API")
    public void cleanupApi() {
        playwrightApiClient.cleanup();
    }

    @After(order = 2, value= "@UI")
    public void takeScreenshotAndTrace(Scenario scenario) {
        if (scenario.isFailed()) {
            String screenshotName = scenario.getName().replaceAll("", "_");
            byte[] sourcePath = playwrightBrowserFactory.getPage().screenshot();
            scenario.attach(sourcePath, "image/png", screenshotName);
            playwrightBrowserFactory.getContext().tracing().stop(new Tracing.StopOptions().setPath(Paths.get("target/" + screenshotName + ".zip")));
        }
    }

}

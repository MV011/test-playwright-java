package org.example.testplaywright.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.testplaywright.context.ScenarioContext;
import org.example.testplaywright.ui.factory.PlaywrightBrowserFactory;
import org.example.testplaywright.pages.DashboardPage;
import org.example.testplaywright.ui.provider.BrowserContextProvider;

import static org.testng.Assert.assertTrue;

public class UiDashboardSteps {

    private final BrowserContextProvider browserContextProvider;
    private final ScenarioContext scenarioContext;
    private final DashboardPage dashboardPage;

    public UiDashboardSteps(BrowserContextProvider browserContextProvider, ScenarioContext scenarioContext) {
        this.browserContextProvider = browserContextProvider;
        this.scenarioContext = scenarioContext;
        this.dashboardPage = new DashboardPage(browserContextProvider.getPage());
    }

    @Then("I should be redirected to the dashboard")
    public void iShouldBeRedirectedToTheDashboard() {
        assertTrue(dashboardPage.isCreateTestCaseFormDisplayed(),"Dashboard page should be displayed");
    }

    @And("I should see {string} in the header")
    public void iShouldSeeInTheHeader(String text) {
        assertTrue(dashboardPage.isHeaderGreetingDisplayed(), "Header greeting should be displayed");
        assertTrue(dashboardPage.getHeaderGreetingText().contains(text), "Header greeting should contain " + text);
    }

    @When("I click the Logout button")
    public void iClickTheLogoutButton() {
        dashboardPage.clickLogoutButton();
    }
}

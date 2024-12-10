package org.example.testplaywright.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.testplaywright.context.ScenarioContext;
import org.example.testplaywright.factory.PlaywrightBrowserFactory;
import org.example.testplaywright.pages.DashboardPage;
import org.example.testplaywright.pages.LoginPage;

import static org.testng.Assert.assertTrue;

public class UiDashboardSteps {

    private final PlaywrightBrowserFactory playwrightBrowserFactory;
    private final ScenarioContext scenarioContext;
    private final DashboardPage dashboardPage;

    public UiDashboardSteps(PlaywrightBrowserFactory playwrightBrowserFactory, ScenarioContext scenarioContext) {
        this.playwrightBrowserFactory = playwrightBrowserFactory;
        this.scenarioContext = scenarioContext;
        this.dashboardPage = new DashboardPage(playwrightBrowserFactory.getPage());
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

package org.example.testplaywright.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.testplaywright.context.ScenarioContext;
import org.example.testplaywright.pages.LoginPage;
import org.example.testplaywright.ui.provider.BrowserContextProvider;

import static org.testng.Assert.assertTrue;

public class UiLoginSteps {

    private final BrowserContextProvider browserContextProvider;
    private final ScenarioContext scenarioContext;
    private final LoginPage loginPage;

    public UiLoginSteps(BrowserContextProvider browserContextProvider, ScenarioContext scenarioContext) {
        this.browserContextProvider = browserContextProvider;
        this.scenarioContext = scenarioContext;
        this.loginPage = new LoginPage(browserContextProvider.getPage());
    }

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
    }

    @When("I enter {string} as username")
    public void iEnterAsUsername(String username) {
        loginPage.fillUserName(username);
    }

    @And("I enter {string} as password")
    public void iEnterAsPassword(String password) {
        loginPage.fillPassword(password);
    }

    @And("I click the Login button")
    public void iClickTheButton() {
        loginPage.clickLoginButton();
    }

    @And("I click the Signup button")
    public void iClickTheSignupButton() {
        loginPage.clickSignUp();
    }


    @Then("I should see an error message {string}")
    public void iShouldSeeAnErrorMessage(String error) {
        assertTrue(loginPage.getAlertText().contains(error),"Alert text should contain " + error);
    }
}

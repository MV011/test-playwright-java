package org.example.testplaywright.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.ScenarioScope;
import org.example.testplaywright.context.ScenarioContext;
import org.example.testplaywright.pages.SignupPage;
import org.example.testplaywright.ui.provider.BrowserContextProvider;

import static org.testng.Assert.assertTrue;

@ScenarioScope
public class UiSignUpSteps {

    private final BrowserContextProvider browserContextProvider;
    private final ScenarioContext scenarioContext;
    private final SignupPage signupPage;

    public UiSignUpSteps(BrowserContextProvider browserContextProvider, ScenarioContext scenarioContext) {
        this.browserContextProvider = browserContextProvider;
        this.scenarioContext = scenarioContext;
        this.signupPage = new SignupPage(browserContextProvider.getPage());
    }

    @Given("I am on the signup page")
    public void iAmOnTheSignupPage() {
        assertTrue(signupPage.isSignupPageDisplayed(), "Signup page should be displayed");
    }

    @When("I enter {string} as signup username")
    public void iEnterAsSignupUsername(String username) {
        signupPage.fillUserName(username);
    }

    @And("I enter {string} as signup email")
    public void iEnterAsSignupEmail(String email) {
        signupPage.fillEmail(email);
    }

    @And("I enter {string} as signup password")
    public void iEnterAsSignupPassword(String password) {
        signupPage.fillPassword(password);
    }

    @And("I confirm the password by entering {string}")
    public void iConfirmThePasswordByEntering(String confirmPassword) {
        signupPage.fillConfirmPassword(confirmPassword);
    }

    @And("I click the Signup button")
    public void iClickTheSignupButton() {
        signupPage.clickSignUpButton();
    }

    @Then("I should see a confirmation message {string}")
    public void iShouldSeeAConfirmationMessage(String message) {
        assertTrue(signupPage.getConfirmationText().contains(message), "Confirmation text should contain " + message);
    }

    @Then("I should see an alert with the message {string}")
    public void iShouldSeeAnAlertWithTheMessage(String alertMessage) {
        assertTrue(signupPage.isAlertDisplayed(), "Alert should be displayed");
        assertTrue(signupPage.getAlertText().contains(alertMessage), "Alert message should contain " + alertMessage);
    }

    @When("I click the Back button")
    public void iClickTheBackButton() {
        browserContextProvider.getPage().goBack(); // Example for navigating back, replace as needed
    }

    @Then("I should remain on the signup page")
    public void iShouldRemainOnTheSignupPage() {
        assertTrue(signupPage.isSignupPageDisplayed(), "Should still be on the signup page");
    }
}

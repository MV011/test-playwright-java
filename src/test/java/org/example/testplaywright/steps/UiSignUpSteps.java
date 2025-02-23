package org.example.testplaywright.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.example.testplaywright.context.ScenarioContext;
import org.example.testplaywright.pages.SignupPage;
import org.example.testplaywright.ui.provider.BrowserContextProvider;

import static org.testng.Assert.assertTrue;

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

    @Then("I am redirected to the sign up page")
    public void iAmRedirectedToTheSignUpPage() {
        assertTrue(signupPage.isSignupPageDisplayed(), "Signup page should be displayed");
    }

    @And("I enter {string} as signup email")
    public void iEnterAsSignupEmail(String email) {
        signupPage.fillEmail(email);
    }

    @And("I confirm the password by entering {string}")
    public void iConfirmThePasswordByEntering(String confirmPassword) {
        signupPage.fillConfirmPassword(confirmPassword);
    }

    @And("I submit the Signup form")
    public void ISubmitTheSignupForm() {
        signupPage.clickSignUpButton();
    }

}

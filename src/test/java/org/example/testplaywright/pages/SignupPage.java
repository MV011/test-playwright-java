package org.example.testplaywright.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.example.testplaywright.utils.BrowserUtils;

public class SignupPage {

    private final Page page;
    private final Locator userName;
    private final Locator email;
    private final Locator password;
    private final Locator confirmPassword;
    private final Locator signUpButton;
    private final Locator header;
    private final Locator alert;

    public SignupPage(Page page) {
        this.page = page;
        this.userName = page.locator("#username");
        this.email = page.locator("#email");
        this.password = page.locator("#password");
        this.confirmPassword = page.locator("#confirmPassword");
        this.signUpButton = page.locator("button[type='submit']");
        this.header = page.locator("h3");
        this.alert = page.locator("//div[@role='alert']");
    }

    public void fillUserName(String username) {
        this.userName.fill(username);
    }

    public void fillEmail(String email) {
        this.email.fill(email);
    }

    public void fillPassword(String password) {
        this.password.fill(password);
    }

    public void fillConfirmPassword(String confirmPassword) {
        this.confirmPassword.fill(confirmPassword);
    }

    public void clickSignUpButton() {
        signUpButton.click();
    }

    public boolean isAlertDisplayed() {
        return alert.isVisible();
    }

    public String getAlertText() {
        return alert.innerText();
    }

    public boolean isSignupPageDisplayed() {
        return BrowserUtils.waitForLocatorToBeVisible(header) && header.innerText().equals("Create Account");
    }

    public String getConfirmationText() {
        return "";
    }

}

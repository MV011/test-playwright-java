package org.example.testplaywright.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.example.testplaywright.utils.BrowserUtils;

public class LoginPage {

    private final Page page;
    private final Locator userName;
    private final Locator password;
    private final Locator loginButton;
    private final Locator error;
    private final Locator signUp;
    private final Locator header;
    private final Locator alert;

    public LoginPage(Page page) {
        this.page = page;
        this.userName = page.locator("#username");
        this.password = page.locator("#password");
        this.loginButton = page.locator("//button[@type='submit']");
        this.header = page.locator("//h3");
        this.signUp = page.getByText("Sign up");
        this.error = page.getByText("Please fill out this field.");
        this.alert = page.locator("//div[@role='alert']");
    }

    public void login(String username, String password) {
        this.userName.fill(username);
        this.password.fill(password);
        loginButton.click();
    }

    public void fillUserName(String username) {
        userName.fill(username);
    }

    public void fillPassword(String password) {
        this.password.fill(password);
    }

    public void clickLoginButton() {
        loginButton.click();
    }
    public void clickSignUp() {
        signUp.click();
    }
    public boolean isErrorDisplayed() {
        return error.isVisible();
    }

    public boolean isAlertDisplayed() {
        return alert.isVisible();
    }

    public String getAlertText() {
        return alert.locator("div").innerText();
    }

    public boolean isLoginPageDisplayed() {
        return BrowserUtils.waitForLocatorToBeVisible(header) && header.innerText().equals("Login");
    }
}

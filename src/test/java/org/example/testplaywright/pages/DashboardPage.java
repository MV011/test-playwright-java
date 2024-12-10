package org.example.testplaywright.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.example.testplaywright.utils.BrowserUtils;

public class DashboardPage {

    private final Page page;
    private final Locator createTestCaseForm;
    private final Locator headerGreeting;
    private final Locator logoutButton;

    public DashboardPage(Page page) {
        this.page = page;
        this.createTestCaseForm = page.locator("#create-test-case-form");
        this.headerGreeting = page.locator("//span[contains(text(),'Welcome')]");
        this.logoutButton = page.locator("#logout-button");
    }

    public boolean isCreateTestCaseFormDisplayed() {
        return BrowserUtils.waitForLocatorToBeVisible(createTestCaseForm);
    }

    public boolean isHeaderGreetingDisplayed() {
        return BrowserUtils.waitForLocatorToBeVisible(headerGreeting);
    }

    public String getHeaderGreetingText() {
        return headerGreeting.innerText();
    }

    public void clickLogoutButton() {
        logoutButton.click();
    }
}

package org.example.testplaywright.utils;

import com.microsoft.playwright.Locator;

public class BrowserUtils {

    public static boolean waitForLocatorToBeVisible(Locator locator) {
        locator.waitFor();
        return locator.isVisible();
    }

}

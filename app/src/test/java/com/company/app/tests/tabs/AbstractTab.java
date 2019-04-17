package com.company.app.tests.tabs;

import com.company.app.tests.driver.AndroidDriverFacade;
import com.company.app.tests.driver.SwipeDirection;
import io.qameta.allure.Step;

import java.io.IOException;

public abstract class AbstractTab {
    protected AndroidDriverFacade driver;

    public AbstractTab(AndroidDriverFacade driver) {
        this.driver = driver;
    }

    public void takeScrennshot(String name) {
        try {
            driver.takeScreenshot(name);
        } catch (IOException e) {
        }
    }

    @Step("Swipe screen {0}")
    public void swipeTo(SwipeDirection direction, int times) throws InterruptedException {
        for (int i = 0; i < times; i++) {
            driver.swipe(direction);
            //wait a bit between swipes
            Thread.sleep(1000);
        }
    }

    public String getElementText(ITabElement element) {
        return driver.findElement(element).getText();
    }

    public boolean isElementVisible(ITabElement element) {
        return driver.findElement(element).isDisplayed();
    }

    public void resetApk() {
        driver.resetApp();
    }

    public void loadApp() {
        driver.launchApp();
    }
}

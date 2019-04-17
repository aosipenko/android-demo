package com.company.app.tests.tabs;

import com.company.app.tests.driver.AndroidDriverFacade;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class MainActivityTab extends AbstractTab {

    public MainActivityTab(AndroidDriverFacade driver) {
        super(driver);
    }

    @Step("Emulate click on invintation URL")
    public void performAppLogin() {
        driver.clickInvintationURL();
    }

    @Step("Click GotIt button")
    public void gotIt() throws InterruptedException {
        driver.clickElement(DisclaimerElement.GOT_IT);
        Thread.sleep(500);
    }

    public String getTotalStreams() {
        return driver.findElement(ArtistElement.TOTAL_STREAMS).getText();
    }

    public String getWeekStreams() {
        return driver.findElement(ArtistElement.WEEK_STREAMS).getText();
    }

    public String getRegion() {
        return driver.findElement(SomeTab.REGION_NAME).getText();
    }

    public enum SomeTab implements ITabElement {
        REGION_NAME(By.id("header"));

        private By locator;

        SomeTab(By locator) {
            this.locator = locator;
        }

        @Override
        public By getLocator() {
            return locator;
        }
    }

    public enum ArtistElement implements ITabElement {
        TOTAL_STREAMS(By.id("totalStreamsSinceYear")),
        WEEK_STREAMS(By.id("totalStreamsForWeek"));

        private By locator;

        ArtistElement(By locator) {
            this.locator = locator;
        }

        @Override
        public By getLocator() {
            return locator;
        }
    }

    public enum DisclaimerElement implements ITabElement {
        TITLE(By.id("title")),
        DISCLAIMER(By.id("disclaimer")),
        TERMS(By.id("text")),
        GOT_IT(By.id("gotItBtn")),
        TELL_ME_MORE(By.id("tellMeMore"));

        private By locator;

        DisclaimerElement(By locator) {
            this.locator = locator;
        }

        @Override
        public By getLocator() {
            return locator;
        }
    }
}

package com.company.app.tests.tabs;

import com.company.app.tests.driver.AndroidDriverFacade;
import org.openqa.selenium.By;

/**
 * first tab the user can see. Contains dome greting info
 */

public class WelcomeTab extends AbstractTab {

    public WelcomeTab(AndroidDriverFacade driver) {
        super(driver);
    }

    public void clickSendText() {
        driver.clickElement(WelcomeTabElement.SEND_TEXT_BTN);
    }

    public enum WelcomeTabElement implements ITabElement {
        ACCEPT_BTN(By.id("action_done")),
        BACKGROUND(By.id("dots")),
        WMG_LOGO(By.id("logo")),
        SEND_TEXT_BTN(By.id("sendText")),
        WELLCOME_TEXT(By.id("text")),
        PHONE_COUNTRY_CODE(By.id("code")),
        PHONE_INPUT(By.id("phone"));


        private By locator;

        WelcomeTabElement(By locator) {
            this.locator = locator;
        }

        @Override
        public By getLocator() {
            return locator;
        }
    }
}

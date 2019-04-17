package com.company.app.tests.stories;

import com.company.app.tests.tabs.WelcomeTab;
import io.qameta.allure.Link;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.testng.Assert.assertTrue;

@Test
public class WelcomePageTest extends TestParent {

    @BeforeTest
    public void resetApp() {
        welcomeTab().resetApk();
        welcomeTab().loadApp();
    }

    @Test
    @Link("https://jira.company_name.com/browse/ISSUE-123")
    @Severity(SeverityLevel.NORMAL)
    public void load_welcome_page_and_check_country_code() {
        assertTrue(welcomeTab().isElementVisible(WelcomeTab.WelcomeTabElement.SEND_TEXT_BTN));
        assertTrue(welcomeTab().isElementVisible(WelcomeTab.WelcomeTabElement.WMG_LOGO));
        assertTrue(welcomeTab().isElementVisible(WelcomeTab.WelcomeTabElement.BACKGROUND));
        assertTrue(welcomeTab().isElementVisible(WelcomeTab.WelcomeTabElement.WELLCOME_TEXT));
        welcomeTab().clickSendText();
        //Validate country code
        assertThat(welcomeTab().getElementText(WelcomeTab.WelcomeTabElement.PHONE_COUNTRY_CODE), is("+1"));
    }
}

package com.company.app.tests.stories;

import com.company.app.tests.driver.SwipeDirection;
import com.company.app.tests.tabs.MainActivityTab;
import com.company.app.tests.util.TestListener;
import com.company.app.tests.util.MetaDataUtil;
import io.qameta.allure.Link;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.json.JSONException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

@Test
@Listeners({TestListener.class})
public class MainFlowTest extends TestParent {

    @BeforeTest
    public void resetApp() throws IOException, JSONException {
        mainActivityTab().resetApk();
        MetaDataUtil.updateMetadata();
    }

    @Test
    @Link("https://jira.company_name.com/browse/ISSUE-124")
    @Severity(SeverityLevel.CRITICAL)
    public void load_tutorial_page_and_check_elements_presense() throws InterruptedException {
        mainActivityTab().performAppLogin();
        assertThat("Element GOT_IT not visible! ", mainActivityTab().isElementVisible(MainActivityTab.DisclaimerElement.GOT_IT), is(true));
        assertThat("Element TELL_ME_MORE not visible! ", mainActivityTab().isElementVisible(MainActivityTab.DisclaimerElement.TELL_ME_MORE), is(true));
        assertThat("Element TITLE not visible! ", mainActivityTab().isElementVisible(MainActivityTab.DisclaimerElement.TITLE), is(true));
        assertThat("Element TERMS not visible! ", mainActivityTab().isElementVisible(MainActivityTab.DisclaimerElement.TERMS), is(true));
        assertThat("Element DISCLAIMER not visible! ", mainActivityTab().isElementVisible(MainActivityTab.DisclaimerElement.DISCLAIMER), is(true));
        mainActivityTab().gotIt();
    }

    @Test
    @Link("https://jira.company_name.com/browse/ISSUE-125")
    @Severity(SeverityLevel.CRITICAL)
    public void load_dashboard_and_validate_elements_presene() throws InterruptedException {
        mainActivityTab().performAppLogin();
        mainActivityTab().gotIt();
        mainActivityTab().gotIt();
        //make sure that we are on the start tab
        mainActivityTab().swipeTo(SwipeDirection.LEFT, 1);
        assertThat("Wrong overall performance value", mainActivityTab().getTotalStreams().toUpperCase(), containsString(MetaDataUtil.getOverallPerformance().toUpperCase()));
        assertThat("Wrong weekly performance value", mainActivityTab().getWeekStreams().toUpperCase(), containsString(MetaDataUtil.getWeekPerformance().toUpperCase()));
    }

    @Test
    @Link("https://jira.company_name.com/browse/ISSUE-127")
    @Severity(SeverityLevel.CRITICAL)
    public void load_charts_tab_and_check_top_3() throws InterruptedException {
        mainActivityTab().performAppLogin();
        mainActivityTab().swipeTo(SwipeDirection.RIGHT, 1);
        assertThat("Wrong field REGION value!", mainActivityTab().getRegion(), containsString("USA"));
    }
}
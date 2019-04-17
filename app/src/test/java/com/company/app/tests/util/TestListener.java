package com.company.app.tests.util;

import com.company.app.tests.driver.AndroidDriverFacade;
import com.company.app.tests.service.AppiumService;
import io.qameta.allure.Attachment;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.net.MalformedURLException;

/**
 * This class is used to process failed tests. For examaple, take device screenshot when test failed
 */
public class TestListener extends TestListenerAdapter {

    //    {"deviceUrl", "deviceName", "inviteToken"}
    @Override
    public void onTestFailure(ITestResult result) {
        try {
            makeScreenshotOnFailure(AppiumService.getDriver(
                    result.getTestContext().getCurrentXmlTest().getParameter("deviceUrl"),
                    result.getTestContext().getCurrentXmlTest().getParameter("deviceName"),
                    result.getTestContext().getCurrentXmlTest().getParameter("inviteToken")
            ));
        } catch (MalformedURLException e) {
            Assert.fail();
        }
    }

    @Attachment(value = "FailedTest", type = "image/png")
    public byte[] makeScreenshotOnFailure(AndroidDriverFacade driver) {
        return driver.takeReportScreenshot();
    }
}
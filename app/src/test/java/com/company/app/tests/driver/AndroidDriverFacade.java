package com.company.app.tests.driver;

import com.google.common.base.Function;
import com.company.app.tests.tabs.ITabElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import javafx.scene.input.SwipeEvent;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.collections.Lists;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * An aggregator class, which contains some utility methods to work with AndroidDriver
 */

public class AndroidDriverFacade {

    private final AndroidDriver androidDriver;
    private final Wait<AndroidDriver> androidDriverWait;
    private final String inviteToken;

    public AndroidDriverFacade(AndroidDriver androidDriver, String inviteToken) {
        this.androidDriver = androidDriver;
        this.inviteToken = inviteToken;
        this.androidDriverWait = new FluentWait<>(this.androidDriver)
                .withTimeout(15, TimeUnit.SECONDS)
                .pollingEvery(2, TimeUnit.SECONDS)
                .ignoring(Exception.class);

    }

    public AndroidElement findElement(ITabElement element) {
        return (AndroidElement) androidDriverWait.until((Function<WebDriver, WebElement>) driver -> driver.findElement(element.getLocator()));
    }

    public void clickElement(ITabElement element) {
        findElement(element).click();
    }

    public void resetApp() {
        androidDriver.removeApp("com.company.app");

        Map<String, Object> args = new HashMap<>();
        args.put("command", "pm");
        args.put("args", Lists.newArrayList("clear", "com.company.app"));

        androidDriver.executeScript("mobile: shell", args);

        androidDriver.installApp("/entry/stub.apk");
    }

    public void launchApp() {
        androidDriver.launchApp();
    }

    public void closeApp() {
        androidDriver.closeApp();
    }

    public void switchContext() {
        androidDriver.getWindowHandles();
        androidDriver.switchTo().frame("test");

    }

    public void quitDriver() {
        androidDriver.quit();
    }

    public void clickInvintationURL() {
        Map<String, Object> args = new HashMap<>();
        args.put("command", "am");
        args.put("args", Lists.newArrayList("start",
                "-a",
                "android.intent.action.VIEW",
                "-d",
                "https://artistcharts.wmg.com/invite?code=" + inviteToken,
                "com.company.app/.ui.main.MainActivity"));

        androidDriver.executeScript("mobile: shell", args);
    }

    public void processAlert(boolean accept) {
        if (accept) {
            waitForAlert().accept();
        } else {
            waitForAlert().dismiss();
        }
        androidDriver.switchTo().defaultContent();
    }

    private Alert waitForAlert() {
        androidDriverWait.until(ExpectedConditions.alertIsPresent());
        return androidDriver.switchTo().alert();
    }

    public void takeScreenshot(String name) throws IOException {
        File scrFile = ((TakesScreenshot) androidDriver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("screenshots/" + name));
    }

    public byte[] takeReportScreenshot() {
        return ((TakesScreenshot) androidDriver).getScreenshotAs(OutputType.BYTES);
    }

    public void setElementText(ITabElement elementText, String text) {
        findElement(elementText).clear();
        findElement(elementText).sendKeys(text);
    }

    public String getAlertText() {
        return waitForAlert().getText();
    }

    public void swipe(SwipeDirection direction) {
        new TouchAction(androidDriver)
                .press(direction.getStartX(), direction.getStartY())
                .moveTo(direction.getEndX(), direction.getEndY())
                .release()
                .perform();
    }
}
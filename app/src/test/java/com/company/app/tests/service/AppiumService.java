package com.company.app.tests.service;

import com.company.app.tests.driver.AndroidDriverFacade;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * A class, responsible for emulator or device connection.
 */

public class AppiumService {

    //In case of parallel tests involved, we may want to use thread-local driver
    private final static ThreadLocal<AndroidDriverFacade> driverInstance = new ThreadLocal<>();

    public static AndroidDriverFacade getDriver(String url, String deviceName, String inviteToken) throws MalformedURLException {
        //check if there is an instance running, use it
        if (driverInstance.get() != null) {
            return driverInstance.get();
        }
        AndroidDriverFacade facade = new AndroidDriverFacade(new AndroidDriver(new URL(url), AppiumService.capabilities(deviceName)), inviteToken);
        driverInstance.set(facade);
        return facade;
    }

    public static void tearDown() {
        driverInstance.get().quitDriver();
        driverInstance.remove();
    }

    private static DesiredCapabilities capabilities(String deviceName) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("BROWSER_NAME", "Android");
        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("app", "/entry/stub.apk");

        return capabilities;
    }
}

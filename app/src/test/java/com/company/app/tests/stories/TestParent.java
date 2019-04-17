package com.company.app.tests.stories;

import com.company.app.tests.docker.DockerContainer;
import com.company.app.tests.docker.DockerUtil;
import com.company.app.tests.service.AppiumService;
import com.company.app.tests.tabs.MainActivityTab;
import com.company.app.tests.tabs.WelcomeTab;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;

import static org.testng.Assert.assertNotNull;

/**
 * A class which contains tab containers, and other shared stuff.
 * Unfortunately, i did not have time to make a proper flow with Spring framework, and its poorly written.
 */

public abstract class TestParent {

    private final static ThreadLocal<WelcomeTab> welcomeTab = new ThreadLocal<>();
    private final static ThreadLocal<MainActivityTab> mainActivityTab = new ThreadLocal<>();

    private DockerContainer container;

    @BeforeSuite
    @Parameters ({"imageId"})
    public void initDockerContainer(String imageId) throws InterruptedException {
        String cid = DockerUtil.createDockerContainer(imageId, 1350);
        this.container = new DockerContainer(cid, 1350);
        container.start();
        container.waitForLogEntry("Appium REST http interface listener started on");
    }

    @BeforeSuite
    @Parameters({"deviceUrl", "deviceName", "inviteToken"})
    public void initTabs(String deviceURL, String deviceName, String inviteToken) throws IOException {
        welcomeTab.set(new WelcomeTab(AppiumService.getDriver(deviceURL, deviceName, inviteToken)));
        mainActivityTab.set(new MainActivityTab(AppiumService.getDriver(deviceURL, deviceName, inviteToken)));

        //Clean screenshots before running suite
        File scrnFolder = new File("screenshots");
        if (scrnFolder.exists() && scrnFolder.isDirectory()) {
            FileUtils.cleanDirectory(scrnFolder);
        } else {
            FileUtils.forceMkdir(scrnFolder);
        }
    }


    protected WelcomeTab welcomeTab() {
        assertNotNull(welcomeTab.get(), "Welcome tab was not initialized!");
        return welcomeTab.get();
    }

    protected MainActivityTab mainActivityTab() {
        assertNotNull(mainActivityTab.get(), "MainActivityTab was not initialized!");
        return mainActivityTab.get();
    }

    @AfterSuite
    public void quitDriver() {
        AppiumService.tearDown();
        container.stop();
    }
}

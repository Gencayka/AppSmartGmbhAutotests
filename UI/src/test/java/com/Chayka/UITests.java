package com.Chayka;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import com.Chayka.commons.Platform;
import com.Chayka.pageobjects.MainPage;
import com.Chayka.pageobjects.MainPageDesktop;
import com.Chayka.pageobjects.MainPageMobile;


@SpringBootTest
public abstract class UITests extends AbstractTestNGSpringContextTests {
    @Autowired
    protected AppTestDataHolder testDataHolder;

    @BeforeSuite
    public void beforeSuite(){
        //Configuration.timeout = 1000;
    }

    @AfterMethod
    public void afterMethod() {
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        Selenide.refresh();
    }

    protected MainPage setup(Platform platform) {
        MainPage mainPage;
        if (platform == Platform.WINDOWS) {
            mainPage = MainPageDesktop.switchToDesktopVer();
        } else {
            mainPage = MainPageMobile.switchToMobileVer();
        }
        return mainPage;
    }
}

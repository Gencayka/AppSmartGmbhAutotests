package ru.Chayka;

import com.codeborne.selenide.Selenide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import ru.Chayka.commons.Platform;
import ru.Chayka.pageobjects.MainPage;
import ru.Chayka.pageobjects.MainPageDesktop;

import static com.codeborne.selenide.Selenide.open;

@SpringBootTest
public abstract class UITests extends AbstractTestNGSpringContextTests {
    @Autowired
    protected AppTestDataHolder testDataHolder;

    @AfterMethod
    public void afterMethod() {
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        Selenide.refresh();
    }

    protected MainPage setup(Platform platform) {
        MainPage mainPage;
        if (platform == Platform.WINDOWS) {
            mainPage = MainPageDesktop.switchToBrowserVer();
        } else {
            //mainPage = MainPageMobile.switchToMobileVer();
            mainPage = MainPageDesktop.switchToBrowserVer();
        }
        return mainPage;
    }
}

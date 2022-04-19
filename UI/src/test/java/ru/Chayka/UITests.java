package ru.Chayka;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import ru.Chayka.commons.Browser;
import ru.Chayka.commons.Platform;
import ru.Chayka.enums.AppLanguage;
import ru.Chayka.pageobjects.MainPage;
import ru.Chayka.pageobjects.MainPageBrowser;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

@SpringBootTest
public abstract class UITests extends AbstractTestNGSpringContextTests {
    @Autowired
    protected AppTestDataHolder testDataHolder;

    @BeforeClass
    public void beforeClass(){
        closeWebDriver();
    }

    @BeforeMethod
    public void beforeMethod() {
        Selenide.clearBrowserCookies();
    }

    protected MainPage setup(Platform platform,
                             Browser browser) {
        Configuration.browser = browser.getWebDriverName();
        open("");
        MainPage mainPage;
        if (platform == Platform.WINDOWS) {
            mainPage = MainPageBrowser.switchToBrowserVer();
        } else {
            //mainPage = MainPageMobile.switchToMobileVer();
            mainPage = MainPageBrowser.switchToBrowserVer();
        }
        return mainPage;
    }

    protected MainPage setup(Platform platform,
                           Browser browser,
                           AppLanguage appLanguage) {
        MainPage mainPage = setup(platform, browser);
        mainPage.switchLanguage(appLanguage);
        return mainPage;
    }
}

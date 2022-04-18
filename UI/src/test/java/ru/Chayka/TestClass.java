package ru.Chayka;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Dimension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;

@SpringBootTest//(classes = SpringStarter.class)
public class TestClass extends AbstractTestNGSpringContextTests {
    @BeforeClass
    public void setup(){
        //Configuration.browser = "opera";
        //Configuration.browserSize = "400x700";
        Configuration.holdBrowserOpen = true;
        //Configuration.browserPosition = "0x0";
        Configuration.timeout = 4000;
    }

    @BeforeMethod
    public void beforeMethod(){
        Selenide.clearBrowserCookies();
    }

    @Test
    public void debugTest() {
        open("");

        MainPage mainPage = new MainPage();
        mainPage.closeWeHaveJustClosedDoc();
        mainPage.switchLanguageNormal(AppLanguage.CZECH);
        mainPage.switchLanguageNormal(AppLanguage.ENGLISH);
        mainPage.switchLanguageNormal(AppLanguage.DEUTSCH);
        mainPage.switchLanguageNormal(AppLanguage.ENGLISH);

        WebDriverRunner.getWebDriver().manage().window().setSize(new Dimension(400,700));
        mainPage.switchLanguageMobile(AppLanguage.POLISH);
        mainPage.switchLanguageMobile(AppLanguage.ENGLISH);
        Assert.assertEquals(1,1);


        /*$(By.linkText("Поиск в Google")).click();
        $("#blogName")
                .shouldBe(visible)
                .shouldHave(text("Li Chen"));*/
    }
}

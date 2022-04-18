package ru.Chayka;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.ex.ElementNotFound;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;

@SpringBootTest(classes = SpringStarter.class)
public class TestClass extends AbstractTestNGSpringContextTests {
    @BeforeClass
    public void setup(){
        //Configuration.browser = "opera";
        //Configuration.browserSize = "400x400";
        Configuration.holdBrowserOpen = true;
        //Configuration.browserPosition = "0x0";
        Configuration.timeout = 4000;
    }
    @Test
    public void debugTest() {
        Selenide.clearBrowserCookies();
        open("");

        if ($(byText("Wir haben gerade geschlossen.")).isDisplayed()){
            $(byText("Speisekarte ansehen")).click();
        }


        /*$(By.linkText("Поиск в Google")).click();
        $("#blogName")
                .shouldBe(visible)
                .shouldHave(text("Li Chen"));*/
    }
}

package ru.Chayka;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.By;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

@SpringBootTest(classes = SpringStarter.class)
public class TestClass extends AbstractTestNGSpringContextTests {
    @BeforeClass
    public void setup(){
        Configuration.browser = "opera";
        Configuration.browserSize = "100x100";
        Configuration.holdBrowserOpen = true;
        Configuration.browserPosition = "0x0";
        Configuration.timeout = 500;
    }
    @Test
    public void debugTest() {
        open("http://google.com");
        $(By.linkText("Поиск в Google")).click();
        $("#blogName")
                .shouldBe(visible)
                .shouldHave(text("Li Chen"));
        /*$(By.name("q")).val("selenide").pressEnter();

        $$("#ires .g").shouldHave(size(10));

        $("#ires .g").shouldBe(visible).shouldHave(
                text("Selenide: concise UI tests in Java"),
                text("selenide.org"));*/
    }
}

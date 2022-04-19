package ru.Chayka;

import com.codeborne.selenide.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.*;
import ru.Chayka.enums.AppLanguage;
import ru.Chayka.commons.Browser;
import ru.Chayka.commons.Platform;
import ru.Chayka.enums.ProductType;
import ru.Chayka.pageobjects.MainPage;
import ru.Chayka.pageobjects.MainPageDesktop;
import ru.Chayka.productdto.ProductWithOptions;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;

@SpringBootTest//(classes = SpringStarter.class)
public class UITestClass extends AbstractTestNGSpringContextTests {
    @Autowired
    AppTestDataHolder testDataHolder;

    @BeforeClass
    public void setup() {
        //Configuration.browser = "opera";
        //Configuration.browserSize = "400x700";
        //Configuration.holdBrowserOpen = true;
        //Configuration.browserPosition = "0x0";
        Configuration.timeout = 4000;
    }

    /*@BeforeMethod
    public void beforeMethod() {
        Selenide.clearBrowserCookies();
        Selenide.refresh();
        //Selenide.clearBrowserLocalStorage();
    }*/

    /*@AfterMethod
    public void afterMethod() {
        Selenide.clearBrowserCookies();
        Selenide.refresh();
        //Selenide.clearBrowserLocalStorage();
    }*/

    @BeforeGroups({"Google Chrome Tests", "Opera Tests"})
    public void beforeGroups(){
        closeWebDriver();
    }

    @AfterSuite(groups = {"Google Chrome Tests"}, alwaysRun = true)
    public void beforeGroupsS(){
        System.out.println("be");
        closeWebDriver();
    }

    private MainPage setup(Platform platform,
                           Browser browser) {
        Configuration.browser = browser.getWebDriverName();
        open("");
        MainPage mainPage;
        if (platform == Platform.WINDOWS) {
            mainPage = MainPageDesktop.switchToBrowserVer();
        } else {
            //mainPage = MainPageMobile.switchToMobileVer();
            mainPage = MainPageDesktop.switchToBrowserVer();
        }
        return mainPage;
    }

    private MainPage setup(Platform platform,
                           Browser browser,
                           AppLanguage appLanguage) {
        MainPage mainPage = setup(platform, browser);
        mainPage.switchLanguage(appLanguage);
        return mainPage;
    }

    @DataProvider
    public Object[][] discountCalDPChrome(){
        return testDataHolder.getDiscountCalculationSpecificBrowserTestData(Browser.GOOGLE_CHROME);
    }

    @DataProvider
    public Object[][] discountCalDPOpera(){
        return testDataHolder.getDiscountCalculationSpecificBrowserTestData(Browser.OPERA);
    }

    @Test(enabled = false)
    public void debugTest() {
        open("");

        MainPageDesktop mainPageDesktop = new MainPageDesktop();
        mainPageDesktop.closeWeHaveJustClosedDoc();
        /*mainPageBrowser.switchLanguageNormal(AppLanguage.CZECH);
        mainPageBrowser.switchLanguageNormal(AppLanguage.ENGLISH);
        mainPageBrowser.switchLanguageNormal(AppLanguage.DEUTSCH);
        mainPageBrowser.switchLanguageNormal(AppLanguage.ENGLISH);

        WebDriverRunner.getWebDriver().manage().window().setSize(new Dimension(400,700));
        MainPageMobile mainPageMobile = new MainPageMobile();
        mainPageMobile.switchLanguage(AppLanguage.POLISH);
        mainPageMobile.switchLanguage(AppLanguage.ENGLISH);

        mainPageBrowser = MainPageBrowser.switchToBrowserVer();*/
        //mainPageBrowser.chooseMenuSection(ProductType.AMERICAN_BIG_BURGER);
        //mainPageBrowser.addProductToCart("Big American Burger");
        ProductWithOptions testProduct = ProductWithOptions.builder()
                .productType(ProductType.AMERICAN_BURGER)
                .toppingOptional("Ei-Patty")
                .sideDish("Pommes + Fanta")
                .totalPrice(12.29)
                .build();
        Assert.assertEquals(1, 1);


        /*$(By.linkText("Поиск в Google")).click();
        $("#blogName")
                .shouldBe(visible)
                .shouldHave(text("Li Chen"));*/
    }

    @Test(dataProvider = "discountCalDPChrome", groups = {"Google Chrome Tests"},enabled = false)
    public void discountCalculationChromeTest(String testName,
                                              Platform platform,
                                              Browser browser,
                                              AppLanguage appLanguage,
                                              List<ProductWithOptions> products) {
        MainPage mainPage = setup(platform, browser);

        mainPage.closeWeHaveJustClosedDoc();
        mainPage.switchLanguage(appLanguage);

        //closeWindow();
        //closeWebDriver();
    }

    @Test(dataProvider = "discountCalDPOpera", groups = {"Opera Tests"}, enabled = false)
    public void discountCalculationOperaTest(String testName,
                                              Platform platform,
                                              Browser browser,
                                              AppLanguage appLanguage,
                                              List<ProductWithOptions> products) {
        MainPage mainPage = setup(platform, browser);

        mainPage.closeWeHaveJustClosedDoc();
        mainPage.switchLanguage(appLanguage);

        //closeWindow();
        //closeWebDriver();
    }
}

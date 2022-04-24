package com.Chayka;

import com.codeborne.selenide.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import com.Chayka.commons.Browser;
import com.Chayka.commons.Platform;
import com.Chayka.enums.AppLanguage;
import com.Chayka.pageobjects.MainPage;
import com.Chayka.productdto.ProductDto;

import java.util.List;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class UIChromeTests extends UITests {
    private Logger logger = LoggerFactory.getLogger(UIChromeTests.class.getSimpleName());

    @BeforeClass
    public void beforeClass(){
        closeWebDriver();
        Configuration.browser = Browser.GOOGLE_CHROME.getWebDriverName();
        open("");
    }

    @DataProvider
    public Object[][] discountCalDP(){
        return testDataHolder.getDiscountCalculationSpecificBrowserTestData(Browser.GOOGLE_CHROME);
    }

    @Test(dataProvider = "discountCalDP")
    public void discountCalculationTest(String testName,
                                        Platform platform,
                                        Browser browser,
                                        AppLanguage appLanguage,
                                        List<ProductDto> products) {
        logger.info("Starting " + testName + " test");
        open("");

        MainPage mainPage = setup(platform);
        mainPage.openingHoursCheck(platform);

        mainPage.switchLanguage(appLanguage);

        mainPage.addProductsToCart(products);
        mainPage.checkDiscount(products, 0.1);

        logger.info("Test completed successfully");
    }
}

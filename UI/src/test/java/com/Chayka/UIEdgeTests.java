package com.Chayka;

import com.Chayka.commons.Browser;
import com.Chayka.commons.Platform;
import com.Chayka.pageobjects.MainPage;
import com.Chayka.productdto.ProductDto;
import com.codeborne.selenide.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.Chayka.enums.AppLanguage;

import java.util.List;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class UIEdgeTests extends UITests {
    private Logger logger = LoggerFactory.getLogger(UIEdgeTests.class.getSimpleName());

    @BeforeClass
    public void beforeClass(){
        closeWebDriver();
        Configuration.browser = Browser.MICROSOFT_EDGE.getWebDriverName();
        open("");
    }

    @DataProvider
    public Object[][] discountCalDP(){
        return testDataHolder.getDiscountCalculationSpecificBrowserTestData(Browser.MICROSOFT_EDGE);
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

package ru.Chayka;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.Chayka.commons.Browser;
import ru.Chayka.commons.Platform;
import ru.Chayka.enums.AppLanguage;
import ru.Chayka.pageobjects.MainPage;
import ru.Chayka.productdto.ProductDto;

import java.util.List;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class UIEdgeTests extends UITests {
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
        //Configuration.holdBrowserOpen = true;
        open("");

        MainPage mainPage = setup(platform);

        mainPage.switchLanguage(appLanguage);

        mainPage.addProductsToCart(products);
        mainPage.checkDiscount(products, 0.1);
    }
}

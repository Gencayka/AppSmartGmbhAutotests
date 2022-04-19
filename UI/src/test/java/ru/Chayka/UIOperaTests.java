package ru.Chayka;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.Chayka.commons.Browser;
import ru.Chayka.commons.Platform;
import ru.Chayka.enums.AppLanguage;
import ru.Chayka.pageobjects.MainPage;

import java.util.List;

import static com.codeborne.selenide.Selenide.open;

public class UIOperaTests extends UITests {
    @DataProvider
    public Object[][] discountCalDP(){
        return testDataHolder.getDiscountCalculationSpecificBrowserTestData(Browser.OPERA);
    }

    @Test(dataProvider = "discountCalDP")
    public void discountCalculationTest(String testName,
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

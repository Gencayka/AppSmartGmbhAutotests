package ru.Chayka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.Chayka.commons.Browser;
import ru.Chayka.commons.Platform;
import ru.Chayka.commons.TestDataHolder;
import ru.Chayka.enums.AppLanguage;
import ru.Chayka.enums.ProductType;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppTestDataHolder extends TestDataHolder {
    private final List<AppDiscountCalculationTestData> discountCalcSingleProductNoOptionsOptionsNotAddableTestData;
    private final List<AppDiscountCalculationTestData> discountCalcSingleProductNoOptionsOptionsAddableTestData;

    public AppTestDataHolder() {
        super(LoggerFactory.getLogger(AppTestDataHolder.class.getSimpleName()));

        discountCalcSingleProductNoOptionsOptionsNotAddableTestData = genDiscountCalcSingleProductNoOptionsOptionsNotAddableTestData();
        discountCalcSingleProductNoOptionsOptionsAddableTestData = genDiscountCalcSingleProductNoOptionsOptionsAddableTestData();
    }

    private List<AppDiscountCalculationTestData> genDiscountCalcSingleProductNoOptionsOptionsNotAddableTestData() {
        List<AppDiscountCalculationTestData> testData = new ArrayList<>();
        String baseTestName = "SingleProduct_NoOptions_OptionsNotAddable";

        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_1_Mobile_Chrome_Deutsch",
                Platform.MOBILE, Browser.GOOGLE_CHROME, AppLanguage.DEUTSCH,
                new ArrayList<>() {{
                    add(ProductWithOptions.builder()
                            .productType(ProductType.NACHOS)
                            .productName("Nachos Chili Cheese")

                            .totalCost(6.0)

                            .build());
                }}));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_2_Windows_Firefox_Polish",
                Platform.WINDOWS, Browser.FIREFOX, AppLanguage.POLISH,
                new ArrayList<>() {{
                    add(ProductWithOptions.builder()
                            .productType(ProductType.NACHOS)
                            .productName("Nachos Chili Cheese")

                            .totalCost(6.0)

                            .build());
                }}));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_3_Mobile_Opera_Czech",
                Platform.MOBILE, Browser.OPERA, AppLanguage.CZECH,
                new ArrayList<>() {{
                    add(ProductWithOptions.builder()
                            .productType(ProductType.NACHOS)
                            .productName("Nachos Chili Cheese")

                            .totalCost(6.0)

                            .build());
                }}));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_4_Windows_Safari_English",
                Platform.WINDOWS, Browser.APPLE_SAFARI, AppLanguage.ENGLISH,
                new ArrayList<>() {{
                    add(ProductWithOptions.builder()
                            .productType(ProductType.NACHOS)
                            .productName("Nachos Chili Cheese")

                            .totalCost(6.0)

                            .build());
                }}));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_5_Mobile_Edge_Deutsch",
                Platform.MOBILE, Browser.MICROSOFT_EDGE, AppLanguage.DEUTSCH,
                new ArrayList<>() {{
                    add(ProductWithOptions.builder()
                            .productType(ProductType.NACHOS)
                            .productName("Nachos Chili Cheese")

                            .totalCost(6.0)

                            .build());
                }}));
        return testData;
    }

    private List<AppDiscountCalculationTestData> genDiscountCalcSingleProductNoOptionsOptionsAddableTestData() {
        List<AppDiscountCalculationTestData> testData = new ArrayList<>();
        String baseTestName = "SingleProduct_NoOptions_OptionsAddable";

        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_6_Mobile_Chrome_Polish",
                Platform.MOBILE, Browser.GOOGLE_CHROME, AppLanguage.POLISH,
                new ArrayList<>() {{
                    add(ProductWithOptions.builder()
                            .productType(ProductType.PIZZA)
                            .productName("Funghi")

                            .totalCost(7.2)

                            .build());
                }}));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_7_Windows_Firefox_Czech",
                Platform.WINDOWS, Browser.FIREFOX, AppLanguage.CZECH,
                new ArrayList<>() {{
                    add(ProductWithOptions.builder()
                            .productType(ProductType.PIZZA)
                            .productName("Funghi")

                            .totalCost(7.2)

                            .build());
                }}));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_8_Mobile_Opera_English",
                Platform.MOBILE, Browser.OPERA, AppLanguage.ENGLISH,
                new ArrayList<>() {{
                    add(ProductWithOptions.builder()
                            .productType(ProductType.PIZZA)
                            .productName("Funghi")

                            .totalCost(7.2)

                            .build());
                }}));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_9_Windows_Safari_Deutsch",
                Platform.WINDOWS, Browser.APPLE_SAFARI, AppLanguage.DEUTSCH,
                new ArrayList<>() {{
                    add(ProductWithOptions.builder()
                            .productType(ProductType.PIZZA)
                            .productName("Funghi")

                            .totalCost(7.2)

                            .build());
                }}));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_10_Mobile_Edge_English",
                Platform.MOBILE, Browser.MICROSOFT_EDGE, AppLanguage.ENGLISH,
                new ArrayList<>() {{
                    add(ProductWithOptions.builder()
                            .productType(ProductType.PIZZA)
                            .productName("Funghi")

                            .totalCost(7.2)

                            .build());
                }}));
        return testData;
    }

    public Object[][] getDiscountCalculationSpecificBrowserTestData(Browser browser) {
        List<AppDiscountCalculationTestData> specificBrowserTestData = new ArrayList<>();
        List<AppDiscountCalculationTestData> allTestData = new ArrayList<>();
        allTestData.addAll(discountCalcSingleProductNoOptionsOptionsNotAddableTestData);
        allTestData.addAll(discountCalcSingleProductNoOptionsOptionsAddableTestData);

        for (AppDiscountCalculationTestData testData : allTestData) {
            if (testData.browser == browser) {
                specificBrowserTestData.add(testData);
            }
        }

        return testDataToObjectsArray(new ArrayList<>(specificBrowserTestData));
    }

    private List<AppDiscountCalculationTestData> findDiscountCalculationTestDataByBrowser(List<AppDiscountCalculationTestData> allTestData,
                                                                                          Browser browser) {
        List<AppDiscountCalculationTestData> specificBrowserTestData = new ArrayList<>();
        for (AppDiscountCalculationTestData testData : allTestData) {
            if (testData.browser == browser) {
                specificBrowserTestData.add(testData);
            }
        }
        return specificBrowserTestData;
    }

    @Getter
    @AllArgsConstructor
    public static class AppDiscountCalculationTestData {
        public final String testName;
        public final Platform platform;
        public final Browser browser;
        public final AppLanguage appLanguage;
        public final List<ProductWithOptions> products;
    }
}

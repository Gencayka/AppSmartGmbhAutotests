package ru.Chayka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.Chayka.commons.Browser;
import ru.Chayka.commons.Platform;
import ru.Chayka.commons.TestDataHolder;
import ru.Chayka.enums.AppLanguage;
import ru.Chayka.enums.ProductType;
import ru.Chayka.productdto.ProductDto;
import ru.Chayka.productdto.ProductWithOptions;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppTestDataHolder extends TestDataHolder {
    private final List<AppDiscountCalculationTestData> discountCalcSingleProductNoOptionsOptionsNotChoosableTestData;
    private final List<AppDiscountCalculationTestData> discountCalcSingleProductNoOptionsOptionsChoosableTestData;
    private final List<AppDiscountCalculationTestData> discountCalcSingleProductWithOptionsTestData;
    private final List<AppDiscountCalculationTestData> discountCalcSeveralProductsOnePositionNoOptionsTestData;
    private final List<AppDiscountCalculationTestData> discountCalcSeveralProductsOnePositionEqualOptionsTestData;
    private final List<AppDiscountCalculationTestData> discountCalcSeveralProductsOnePositionDifferentOptionsTestData;
    private final List<AppDiscountCalculationTestData> discountCalcSeveralProductsSeveralPositionsNoOptionsTestData;
    private final List<AppDiscountCalculationTestData> discountCalcSeveralProductsSeveralPositionsWithOptionsTestData;

    public AppTestDataHolder() {
        discountCalcSingleProductNoOptionsOptionsNotChoosableTestData = genDiscountCalcSingleProductNoOptionsOptionsNotChoosableTestData();
        discountCalcSingleProductNoOptionsOptionsChoosableTestData = genDiscountCalcSingleProductNoOptionsOptionsChoosableTestData();
        discountCalcSingleProductWithOptionsTestData = genDiscountCalcSingleProductWithOptionsTestData();
        discountCalcSeveralProductsOnePositionNoOptionsTestData = genDiscountCalcSeveralProductsOnePositionNoOptionsTestData();
        discountCalcSeveralProductsOnePositionEqualOptionsTestData = genDiscountCalcSeveralProductsOnePositionEqualOptionsTestData();
        discountCalcSeveralProductsOnePositionDifferentOptionsTestData = genDiscountCalcSeveralProductsOnePositionDifferentOptionsTestData();
        discountCalcSeveralProductsSeveralPositionsNoOptionsTestData = genDiscountCalcSeveralProductsSeveralPositionsNoOptionsTestData();
        discountCalcSeveralProductsSeveralPositionsWithOptionsTestData = genDiscountCalcSeveralProductsSeveralPositionsWithOptionsTestData();
    }

    private List<AppDiscountCalculationTestData> genDiscountCalcSingleProductNoOptionsOptionsNotChoosableTestData() {
        List<AppDiscountCalculationTestData> testData = new ArrayList<>();
        String baseTestName = "SingleProduct_NoOptions_OptionsNotAddable";
        List<ProductDto> testProducts = new ArrayList<>() {{
            add(ProductWithOptions.builder()
                    .productType(ProductType.NACHOS)
                    .positionName("Nachos Chili Cheese")

                    .totalPrice(6.0)
                    .build());
        }};

        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_1_Mobile_Chrome_Deutsch",
                Platform.MOBILE, Browser.GOOGLE_CHROME, AppLanguage.DEUTSCH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_2_Windows_Firefox_Polish",
                Platform.WINDOWS, Browser.FIREFOX, AppLanguage.POLISH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_3_Mobile_Opera_Czech",
                Platform.MOBILE, Browser.OPERA, AppLanguage.CZECH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_4_Windows_Safari_English",
                Platform.WINDOWS, Browser.APPLE_SAFARI, AppLanguage.ENGLISH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_5_Mobile_Edge_Deutsch",
                Platform.MOBILE, Browser.MICROSOFT_EDGE, AppLanguage.DEUTSCH,
                testProducts));
        return testData;
    }

    private List<AppDiscountCalculationTestData> genDiscountCalcSingleProductNoOptionsOptionsChoosableTestData() {
        List<AppDiscountCalculationTestData> testData = new ArrayList<>();
        String baseTestName = "SingleProduct_NoOptions_OptionsAddable";
        List<ProductDto> testProducts = new ArrayList<>() {{
            add(ProductWithOptions.builder()
                    .productType(ProductType.PIZZA)
                    .positionName("Funghi")

                    .totalPrice(7.2)
                    .build());
        }};

        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_6_Mobile_Chrome_Polish",
                Platform.MOBILE, Browser.GOOGLE_CHROME, AppLanguage.POLISH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_7_Windows_Firefox_Czech",
                Platform.WINDOWS, Browser.FIREFOX, AppLanguage.CZECH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_8_Mobile_Opera_English",
                Platform.MOBILE, Browser.OPERA, AppLanguage.ENGLISH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_9_Windows_Safari_Deutsch",
                Platform.WINDOWS, Browser.APPLE_SAFARI, AppLanguage.DEUTSCH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_10_Mobile_Edge_English",
                Platform.MOBILE, Browser.MICROSOFT_EDGE, AppLanguage.ENGLISH,
                testProducts));
        return testData;
    }

    private List<AppDiscountCalculationTestData> genDiscountCalcSingleProductWithOptionsTestData() {
        List<AppDiscountCalculationTestData> testData = new ArrayList<>();
        String baseTestName = "SingleProduct_WithOptions";
        List<ProductDto> testProducts = new ArrayList<>() {{
            add(ProductWithOptions.builder()
                    .productType(ProductType.AMERICAN_BURGER)
                    .positionName("American Burger")

                    .toppingOptional("Ei-Patty")
                    .sideDish("Pommes + Fanta")

                    .totalPrice(12.29)
                    .build());
        }};

        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_11_Mobile_Opera_Deutsch",
                Platform.MOBILE, Browser.OPERA, AppLanguage.DEUTSCH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_12_Windows_Safari_English",
                Platform.WINDOWS, Browser.APPLE_SAFARI, AppLanguage.ENGLISH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_13_Mobile_Edge_Deutsch",
                Platform.MOBILE, Browser.MICROSOFT_EDGE, AppLanguage.DEUTSCH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_14_Windows_Chrome_Polish",
                Platform.WINDOWS, Browser.GOOGLE_CHROME, AppLanguage.POLISH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_15_Mobile_Firefox_Czech",
                Platform.MOBILE, Browser.FIREFOX, AppLanguage.CZECH,
                testProducts));
        return testData;
    }

    private List<AppDiscountCalculationTestData> genDiscountCalcSeveralProductsOnePositionNoOptionsTestData() {
        List<AppDiscountCalculationTestData> testData = new ArrayList<>();
        String baseTestName = "SeveralProducts_OnePosition_NoOptions";
        List<ProductDto> testProducts = new ArrayList<>() {{
            add(ProductWithOptions.builder()
                    .productType(ProductType.FRIES_AND_SIDES)
                    .positionName("Potato Wedges")

                    .totalPrice(3.29)
                    .build());
            add(ProductWithOptions.builder()
                    .productType(ProductType.FRIES_AND_SIDES)
                    .positionName("Potato Wedges")

                    .totalPrice(3.29)
                    .build());
        }};

        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_16_Windows_Safari_Deutsch",
                Platform.WINDOWS, Browser.APPLE_SAFARI, AppLanguage.DEUTSCH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_17_Mobile_Edge_Polish",
                Platform.MOBILE, Browser.MICROSOFT_EDGE, AppLanguage.POLISH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_18_Windows_Chrome_Czech",
                Platform.WINDOWS, Browser.GOOGLE_CHROME, AppLanguage.CZECH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_19_Mobile_Firefox_English",
                Platform.MOBILE, Browser.FIREFOX, AppLanguage.ENGLISH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_20_Windows_Opera_Deutsch",
                Platform.WINDOWS, Browser.OPERA, AppLanguage.DEUTSCH,
                testProducts));
        return testData;
    }

    private List<AppDiscountCalculationTestData> genDiscountCalcSeveralProductsOnePositionEqualOptionsTestData() {
        List<AppDiscountCalculationTestData> testData = new ArrayList<>();
        String baseTestName = "SeveralProducts_OnePosition_EqualOptions";
        List<ProductDto> testProducts = new ArrayList<>() {{
            add(ProductWithOptions.builder()
                    .productType(ProductType.SALAD)
                    .positionName("Kartoffelsalat")

                    .size("groß")
                    .dressingRequired("Balsamicoessig")
                    .dressingsExtra("Frenchdressing")
                    .dressingsExtra("Joghurtdressing")

                    .totalPrice(6.20)
                    .build());
            add(ProductWithOptions.builder()
                    .productType(ProductType.SALAD)
                    .positionName("Kartoffelsalat")

                    .size("groß")
                    .dressingRequired("Balsamicoessig")
                    .dressingsExtra("Frenchdressing")
                    .dressingsExtra("Joghurtdressing")

                    .totalPrice(6.20)
                    .build());
        }};

        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_21_Windows_Safari_Polish",
                Platform.WINDOWS, Browser.APPLE_SAFARI, AppLanguage.POLISH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_22_Mobile_Edge_Czech",
                Platform.MOBILE, Browser.MICROSOFT_EDGE, AppLanguage.CZECH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_23_Windows_Chrome_English",
                Platform.WINDOWS, Browser.GOOGLE_CHROME, AppLanguage.ENGLISH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_24_Mobile_Firefox_Deutsch",
                Platform.MOBILE, Browser.FIREFOX, AppLanguage.DEUTSCH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_25_Windows_Opera_Polish",
                Platform.WINDOWS, Browser.OPERA, AppLanguage.POLISH,
                testProducts));
        return testData;
    }

    private List<AppDiscountCalculationTestData> genDiscountCalcSeveralProductsOnePositionDifferentOptionsTestData() {
        List<AppDiscountCalculationTestData> testData = new ArrayList<>();
        String baseTestName = "SeveralProducts_OnePosition_DifferentOptions";
        List<ProductDto> testProducts = new ArrayList<>() {{
            add(ProductWithOptions.builder()
                    .productType(ProductType.AMERICAN_BIG_BURGER)
                    .positionName("Big Mexican Burger")

                    .size("normal")
                    .toppingOptional("Extra Käse (2 Scheiben)")
                    .toppingOptional("Extra Turkey Bacon")
                    .sideDish("Pommes + Pils")

                    .totalPrice(14.79)
                    .build());
            add(ProductWithOptions.builder()
                    .productType(ProductType.AMERICAN_BIG_BURGER)
                    .positionName("Big Mexican Burger")

                    .size("normal")
                    .toppingOptional("Extra Käse (2 Scheiben)")
                    .toppingOptional("Extra Käse (2 Scheiben)")
                    .toppingOptional("Extra Käse (2 Scheiben)")

                    .totalPrice(12.49)
                    .build());
        }};

        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_26_Windows_Safari_Czech",
                Platform.WINDOWS, Browser.APPLE_SAFARI, AppLanguage.CZECH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_27_Windows_Chrome_Deutsch",
                Platform.WINDOWS, Browser.GOOGLE_CHROME, AppLanguage.DEUTSCH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_28_Mobile_Firefox_Polish",
                Platform.MOBILE, Browser.FIREFOX, AppLanguage.POLISH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_29_Windows_Opera_Czech",
                Platform.WINDOWS, Browser.OPERA, AppLanguage.CZECH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_30_Mobile_Safari_English",
                Platform.MOBILE, Browser.APPLE_SAFARI, AppLanguage.ENGLISH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_31_Mobile_Edge_Deutsch",
                Platform.MOBILE, Browser.MICROSOFT_EDGE, AppLanguage.DEUTSCH,
                testProducts));
        return testData;
    }

    private List<AppDiscountCalculationTestData> genDiscountCalcSeveralProductsSeveralPositionsNoOptionsTestData() {
        List<AppDiscountCalculationTestData> testData = new ArrayList<>();
        String baseTestName = "SeveralProducts_SeveralPositions_NoOptions";
        List<ProductDto> testProducts = new ArrayList<>() {{
            add(ProductWithOptions.builder()
                    .productType(ProductType.SPECIAL_BOX)
                    .positionName("9er Box")

                    .totalPrice(6.49)
                    .build());
            add(ProductWithOptions.builder()
                    .productType(ProductType.HOT_DOGS)
                    .positionName("Bacon Flavour Dog")

                    .isDiscounted(false)
                    .totalPrice(3.99)
                    .build());
        }};

        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_32_Mobile_Edge_Deutsch",
                Platform.MOBILE, Browser.MICROSOFT_EDGE, AppLanguage.DEUTSCH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_33_Windows_Chrome_Polish",
                Platform.WINDOWS, Browser.GOOGLE_CHROME, AppLanguage.POLISH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_34_Mobile_Firefox_Czech",
                Platform.MOBILE, Browser.FIREFOX, AppLanguage.CZECH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_35_Windows_Opera_English",
                Platform.WINDOWS, Browser.OPERA, AppLanguage.ENGLISH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_36_Mobile_Safari_Deutsch",
                Platform.MOBILE, Browser.APPLE_SAFARI, AppLanguage.DEUTSCH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_37_Windows_Edge_English",
                Platform.WINDOWS, Browser.MICROSOFT_EDGE, AppLanguage.ENGLISH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_38_Mobile_Chrome_Deutsch",
                Platform.MOBILE, Browser.GOOGLE_CHROME, AppLanguage.DEUTSCH,
                testProducts));
        return testData;
    }

    private List<AppDiscountCalculationTestData> genDiscountCalcSeveralProductsSeveralPositionsWithOptionsTestData() {
        List<AppDiscountCalculationTestData> testData = new ArrayList<>();
        String baseTestName = "SeveralProducts_SeveralPositions_WithOptions";
        List<ProductDto> testProducts = new ArrayList<>() {{
            add(ProductWithOptions.builder()
                    .productType(ProductType.PIZZA_BREAD)
                    .positionName("Pizzabrot")

                    .size("mittel 32 cm")
                    .sauce("Honig-Senf-Dip")
                    .ingredientExtra("Ei")
                    .ingredientExtra("Jalapenos")
                    .ingredientExtra("Mais")
                    .ingredientExtra("Mais")
                    .ingredientExtra("Mais")

                    .totalPrice(17.20)
                    .build());
            add(ProductWithOptions.builder()
                    .productType(ProductType.VEGETARIAN_BURGER)
                    .positionName("Italian Burger")

                    .toppingOptional("Bacon")
                    .toppingOptional("Bacon")

                    .totalPrice(8.30)
                    .build());
            add(ProductWithOptions.builder()
                    .productType(ProductType.DESSERT)
                    .positionName("Apple Cinnamon Donut")

                    .isDiscounted(false)
                    .totalPrice(2.20)
                    .build());
        }};

        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_39_Mobile_Opera_English",
                Platform.MOBILE, Browser.OPERA, AppLanguage.ENGLISH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_40_Windows_Safari_Deutsch",
                Platform.WINDOWS, Browser.APPLE_SAFARI, AppLanguage.DEUTSCH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_41_Mobile_Edge_Polish",
                Platform.MOBILE, Browser.MICROSOFT_EDGE, AppLanguage.POLISH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_42_Windows_Chrome_Czech",
                Platform.WINDOWS, Browser.GOOGLE_CHROME, AppLanguage.CZECH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_43_Mobile_Firefox_English",
                Platform.MOBILE, Browser.FIREFOX, AppLanguage.ENGLISH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_44_Windows_Opera_Deutsch",
                Platform.WINDOWS, Browser.OPERA, AppLanguage.DEUTSCH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_45_Mobile_Safari_English",
                Platform.MOBILE, Browser.APPLE_SAFARI, AppLanguage.ENGLISH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_46_Windows_Edge_Deutsch",
                Platform.WINDOWS, Browser.MICROSOFT_EDGE, AppLanguage.DEUTSCH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_47_Mobile_Chrome_Polish",
                Platform.MOBILE, Browser.GOOGLE_CHROME, AppLanguage.POLISH,
                testProducts));
        testData.add(new AppDiscountCalculationTestData(
                baseTestName + "_48_Windows_Firefox_Czech",
                Platform.WINDOWS, Browser.FIREFOX, AppLanguage.CZECH,
                testProducts));
        return testData;
    }

    public Object[][] getDiscountCalculationSpecificBrowserTestData(Browser browser) {
        List<AppDiscountCalculationTestData> specificBrowserTestData = new ArrayList<>();
        List<AppDiscountCalculationTestData> allTestData = new ArrayList<>();
        allTestData.addAll(discountCalcSingleProductNoOptionsOptionsNotChoosableTestData);
        allTestData.addAll(discountCalcSingleProductNoOptionsOptionsChoosableTestData);
        allTestData.addAll(discountCalcSingleProductWithOptionsTestData);
        allTestData.addAll(discountCalcSeveralProductsOnePositionNoOptionsTestData);
        allTestData.addAll(discountCalcSeveralProductsOnePositionEqualOptionsTestData);
        allTestData.addAll(discountCalcSeveralProductsOnePositionDifferentOptionsTestData);
        allTestData.addAll(discountCalcSeveralProductsSeveralPositionsNoOptionsTestData);
        allTestData.addAll(discountCalcSeveralProductsSeveralPositionsWithOptionsTestData);

        for (AppDiscountCalculationTestData testData : allTestData) {
            if (testData.browser == browser) {
                specificBrowserTestData.add(testData);
            }
        }

        return testDataToObjectsArray(new ArrayList<>(specificBrowserTestData));
    }

    @Getter
    @AllArgsConstructor
    public static class AppDiscountCalculationTestData {
        public final String testName;
        public final Platform platform;
        public final Browser browser;
        public final AppLanguage appLanguage;
        public final List<ProductDto> products;
    }
}

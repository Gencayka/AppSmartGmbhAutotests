package com.Chayka;

import com.Chayka.commons.Browser;
import com.Chayka.commons.Platform;
import com.Chayka.commons.TestDataHolder;
import com.Chayka.productdto.Product;
import com.Chayka.productdto.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import com.Chayka.enums.AppLanguage;
import com.Chayka.enums.ProductType;
import com.Chayka.productdto.ProductDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for generating and holding test data for different test cases for App application
 */
@Component
public class AppTestDataHolder extends TestDataHolder {
    private final List<AppDiscountCalculationTestData> disCalcSingleProductSingleUnitTestData;
    private final List<AppDiscountCalculationTestData> disCalcSingleProductMultipleUnitsTestData;
    private final List<AppDiscountCalculationTestData> disCalcMultipleProductsTestData;

    public AppTestDataHolder() {
        disCalcSingleProductSingleUnitTestData = genDisCalcSingleProductSingleUnitTestData();
        disCalcSingleProductMultipleUnitsTestData = genDisCalcSingleProductMultipleUnitsTestData();
        disCalcMultipleProductsTestData = genDisCalcMultipleProductsTestData();
    }

    private List<AppDiscountCalculationTestData> genDisCalcSingleProductSingleUnitTestData() {
        List<AppDiscountCalculationTestData> testData = new ArrayList<>();

        String baseTest1Name = "T1_SinglePosition_SingleUnit_NoOptions_OptionsNotAddable_Discounted";
        List<ProductDto> test1Products = new ArrayList<>() {{
            add(Product.builder()
                    .productType(ProductType.NACHOS)
                    .positionName("Nachos Chili Cheese")

                    .totalPrice(6.0)
                    .build());
        }};
        List<TestEnvironment> test1Environments = new ArrayList<>() {{
            add(new TestEnvironment(Platform.MOBILE, Browser.GOOGLE_CHROME, AppLanguage.CZECH));
        }};
        testData.addAll(genDisCalcTestDataWithDifEnvironments(baseTest1Name, test1Products, test1Environments));

        String baseTest2Name = "T2_SinglePosition_SingleUnit_NoOptions_OptionsAddable_Discounted";
        List<ProductDto> test2Products = new ArrayList<>() {{
            add(Product.builder()
                    .productType(ProductType.CHICKEN_BURGER)
                    .positionName("Ciabatta Long Chicken")

                    .totalPrice(6.39)
                    .build());
        }};
        List<TestEnvironment> test2Environments = new ArrayList<>() {{
            add(new TestEnvironment(Platform.DESKTOP, Browser.GOOGLE_CHROME, AppLanguage.DEUTSCH));
        }};
        testData.addAll(genDisCalcTestDataWithDifEnvironments(baseTest2Name, test2Products, test2Environments));

        String baseTest3Name = "T3_SinglePosition_SingleUnit_WithOptions_Discounted";
        List<ProductDto> test3Products = new ArrayList<>() {{
            add(Product.builder()
                    .productType(ProductType.AMERICAN_BURGER)
                    .positionName("American Burger")

                    .toppingOptional("Ei-Patty")
                    .toppingOptional("Ei-Patty")
                    .sideDish("Pommes + Fanta")

                    .totalPrice(14.09)
                    .build());
        }};
        List<TestEnvironment> test3Environments = new ArrayList<>() {{
            add(new TestEnvironment(Platform.MOBILE, Browser.GOOGLE_CHROME, AppLanguage.POLISH));
        }};
        testData.addAll(genDisCalcTestDataWithDifEnvironments(baseTest3Name, test3Products, test3Environments));

        String baseTest4Name = "T4_SinglePosition_SingleUnit_NoOptions_OptionsNotAddable_NotDiscounted";
        List<ProductDto> test4Products = new ArrayList<>() {{
            add(Product.builder()
                    .productType(ProductType.DESSERT)
                    .positionName("Apple Cinnamon Donut")

                    .isDiscounted(false)
                    .totalPrice(2.20)
                    .build());
        }};
        List<TestEnvironment> test4Environments = new ArrayList<>() {{
            add(new TestEnvironment(Platform.DESKTOP, Browser.GOOGLE_CHROME, AppLanguage.CZECH));
        }};
        testData.addAll(genDisCalcTestDataWithDifEnvironments(baseTest4Name, test4Products, test4Environments));

        String baseTest5Name = "T5_SinglePosition_SingleUnit_NoOptions_OptionsAddable_NotDiscounted";
        List<ProductDto> test5Products = new ArrayList<>() {{
            add(Product.builder()
                    .productType(ProductType.HOT_DOGS)
                    .positionName("Rösti Onion Hot Dog")

                    .isDiscounted(false)
                    .totalPrice(3.79)
                    .build());
        }};
        List<TestEnvironment> test5Environments = new ArrayList<>() {{
            add(new TestEnvironment(Platform.DESKTOP, Browser.GOOGLE_CHROME, AppLanguage.ENGLISH));
        }};
        testData.addAll(genDisCalcTestDataWithDifEnvironments(baseTest5Name, test5Products, test5Environments));

        String baseTest6Name = "T6_SinglePosition_SingleUnit_WithOptions_NotDiscounted";
        List<ProductDto> test6Products = new ArrayList<>() {{
            add(Product.builder()
                    .productType(ProductType.HOT_DOGS)
                    .positionName("Mexican Dog")

                    .toppingOptional("Jalapenos")
                    .toppingOptional("Jalapenos")
                    .toppingOptional("Jalapenos")

                    .isDiscounted(false)
                    .totalPrice(6.69)
                    .build());
        }};
        List<TestEnvironment> test6Environments = new ArrayList<>() {{
            add(new TestEnvironment(Platform.MOBILE, Browser.GOOGLE_CHROME, AppLanguage.DEUTSCH));
        }};
        testData.addAll(genDisCalcTestDataWithDifEnvironments(baseTest6Name, test6Products, test6Environments));

        String baseTest7Name = "T7_SinglePositionSet_SingleUnit_NoOptions";
        List<ProductDto> test7Products = new ArrayList<>() {{
            add(Set.builder()
                    .positionName("Long Ranch-Master Menu")
                    .part(Product.builder()
                            .positionName("Coca Cola Light")
                            .build())
                    .totalPrice(10.45)
                    .build());
        }};
        List<TestEnvironment> test7Environments = new ArrayList<>() {{
            add(new TestEnvironment(Platform.MOBILE, Browser.GOOGLE_CHROME, AppLanguage.CZECH));
        }};
        testData.addAll(genDisCalcTestDataWithDifEnvironments(baseTest7Name, test7Products, test7Environments));

        String baseTest8Name = "T8_SinglePositionSet_SingleUnit_WithOptions";
        List<ProductDto> test8Products = new ArrayList<>() {{
            add(Set.builder()
                    .positionName("Kiddy-Box Burger")

                    .part(Product.builder()
                            .positionName("Hamburger")
                            .toppingOptional("Ei-Patty")
                            .toppingOptional("Ei-Patty")
                            .toppingOptional("Ei-Patty")
                            .sideDish("Pommes + Helles Weizen")
                            .build())

                    .totalPrice(14.80)
                    .build());
        }};
        List<TestEnvironment> test8Environments = new ArrayList<>() {{
            add(new TestEnvironment(Platform.DESKTOP, Browser.GOOGLE_CHROME, AppLanguage.POLISH));
        }};
        testData.addAll(genDisCalcTestDataWithDifEnvironments(baseTest8Name, test8Products, test8Environments));

        return testData;
    }

    private List<AppDiscountCalculationTestData> genDisCalcSingleProductMultipleUnitsTestData() {
        List<AppDiscountCalculationTestData> testData = new ArrayList<>();

        String baseTest9Name = "T9_SinglePosition_MultipleUnits_NoOptions_OptionsNotAddable_Discounted";
        List<ProductDto> test9Products = new ArrayList<>() {{
            add(Product.builder()
                    .productType(ProductType.DIPS_AND_SAUCE)
                    .positionName("Mayonnaise")

                    .totalPrice(0.40)
                    .build());
            add(Product.builder()
                    .productType(ProductType.DIPS_AND_SAUCE)
                    .positionName("Mayonnaise")

                    .totalPrice(0.40)
                    .build());
            add(Product.builder()
                    .productType(ProductType.DIPS_AND_SAUCE)
                    .positionName("Mayonnaise")

                    .totalPrice(0.40)
                    .build());
        }};
        List<TestEnvironment> test9Environments = new ArrayList<>() {{
            add(new TestEnvironment(Platform.DESKTOP, Browser.GOOGLE_CHROME, AppLanguage.ENGLISH));
        }};
        testData.addAll(genDisCalcTestDataWithDifEnvironments(baseTest9Name, test9Products, test9Environments));

        String baseTest10Name = "T10_SinglePosition_MultipleUnits_NoOptions_OptionsAddable_Discounted";
        List<ProductDto> test10Products = new ArrayList<>() {{
            add(Product.builder()
                    .productType(ProductType.PIZZA_BREAD)
                    .positionName("Pizzabrot mit Knoblauchbutter")

                    .totalPrice(5.00)
                    .build());
            add(Product.builder()
                    .productType(ProductType.PIZZA_BREAD)
                    .positionName("Pizzabrot mit Knoblauchbutter")

                    .totalPrice(5.00)
                    .build());
        }};
        List<TestEnvironment> test10Environments = new ArrayList<>() {{
            add(new TestEnvironment(Platform.DESKTOP, Browser.GOOGLE_CHROME, AppLanguage.DEUTSCH));
        }};
        testData.addAll(genDisCalcTestDataWithDifEnvironments(baseTest10Name, test10Products, test10Environments));

        String baseTest11Name = "T11_SinglePosition_MultipleUnits_WithEqualOptions_Discounted";
        List<ProductDto> test11Products = new ArrayList<>() {{
            add(Product.builder()
                    .productType(ProductType.SALAD)
                    .positionName("Kartoffelsalat")

                    .size("groß")
                    .dressingRequired("Balsamicoessig")
                    .dressingsExtra("Frenchdressing")
                    .dressingsExtra("Frenchdressing")
                    .dressingsExtra("Joghurtdressing")

                    .totalPrice(7.20)
                    .build());
            add(Product.builder()
                    .productType(ProductType.SALAD)
                    .positionName("Kartoffelsalat")

                    .size("groß")
                    .dressingRequired("Balsamicoessig")
                    .dressingsExtra("Frenchdressing")
                    .dressingsExtra("Frenchdressing")
                    .dressingsExtra("Joghurtdressing")

                    .totalPrice(7.20)
                    .build());
        }};
        List<TestEnvironment> test11Environments = new ArrayList<>() {{
            add(new TestEnvironment(Platform.DESKTOP, Browser.GOOGLE_CHROME, AppLanguage.POLISH));
        }};
        testData.addAll(genDisCalcTestDataWithDifEnvironments(baseTest11Name, test11Products, test11Environments));

        String baseTest12Name = "T12_SinglePosition_MultipleUnits_WithDifferentOptions_Discounted";
        List<ProductDto> test12Products = new ArrayList<>() {{
            add(Product.builder()
                    .productType(ProductType.AMERICAN_BIG_BURGER)
                    .positionName("Big Mexican Burger")

                    .size("normal")
                    .toppingOptional("Extra Käse (2 Scheiben)")
                    .toppingOptional("Extra Turkey Bacon")
                    .toppingOptional("Extra Turkey Bacon")
                    .sideDish("Pommes + Pils")

                    .totalPrice(16.39)
                    .build());
            add(Product.builder()
                    .productType(ProductType.AMERICAN_BIG_BURGER)
                    .positionName("Big Mexican Burger")

                    .size("mit Käse")
                    .toppingOptional("Extra Käse (2 Scheiben)")
                    .toppingOptional("Extra Käse (2 Scheiben)")
                    .toppingOptional("Extra Käse (2 Scheiben)")

                    .totalPrice(13.49)
                    .build());
        }};
        List<TestEnvironment> test12Environments = new ArrayList<>() {{
            add(new TestEnvironment(Platform.MOBILE, Browser.GOOGLE_CHROME, AppLanguage.DEUTSCH));
        }};
        testData.addAll(genDisCalcTestDataWithDifEnvironments(baseTest12Name, test12Products, test12Environments));

        String baseTest13Name = "T13_SinglePosition_MultipleUnits_NoOptions_OptionsNotAddable_NotDiscounted";
        List<ProductDto> test13Products = new ArrayList<>() {{
            add(Product.builder()
                    .productType(ProductType.DESSERT)
                    .positionName("Simpsons Donut")

                    .isDiscounted(false)
                    .totalPrice(2.20)
                    .build());
            add(Product.builder()
                    .productType(ProductType.DESSERT)
                    .positionName("Simpsons Donut")

                    .isDiscounted(false)
                    .totalPrice(2.20)
                    .build());
            add(Product.builder()
                    .productType(ProductType.DESSERT)
                    .positionName("Simpsons Donut")

                    .isDiscounted(false)
                    .totalPrice(2.20)
                    .build());
        }};
        List<TestEnvironment> test13Environments = new ArrayList<>() {{
            add(new TestEnvironment(Platform.MOBILE, Browser.GOOGLE_CHROME, AppLanguage.POLISH));
        }};
        testData.addAll(genDisCalcTestDataWithDifEnvironments(baseTest13Name, test13Products, test13Environments));

        String baseTest14Name = "T14_SinglePosition_MultipleUnits_NoOptions_OptionsAddable_NotDiscounted";
        List<ProductDto> test14Products = new ArrayList<>() {{
            add(Product.builder()
                    .productType(ProductType.HOT_DOGS)
                    .positionName("Red Delight Dog")

                    .isDiscounted(false)
                    .totalPrice(3.79)
                    .build());
            add(Product.builder()
                    .productType(ProductType.HOT_DOGS)
                    .positionName("Red Delight Dog")

                    .isDiscounted(false)
                    .totalPrice(3.79)
                    .build());
        }};
        List<TestEnvironment> test14Environments = new ArrayList<>() {{
            add(new TestEnvironment(Platform.DESKTOP, Browser.GOOGLE_CHROME, AppLanguage.CZECH));
        }};
        testData.addAll(genDisCalcTestDataWithDifEnvironments(baseTest14Name, test14Products, test14Environments));

        String baseTest15Name = "T15_SinglePosition_MultipleUnits_WithEqualOptions_NotDiscounted";
        List<ProductDto> test15Products = new ArrayList<>() {{
            add(Product.builder()
                    .productType(ProductType.HOT_DOGS)
                    .positionName("American BBQ Hot Dog")

                    .toppingOptional("Extra Turkey Bacon")
                    .toppingOptional("Jalapenos")
                    .toppingOptional("extra Cheese")
                    .toppingOptional("extra Cheese")

                    .isDiscounted(false)
                    .totalPrice(7.59)
                    .build());
            add(Product.builder()
                    .productType(ProductType.HOT_DOGS)
                    .positionName("American BBQ Hot Dog")

                    .toppingOptional("Extra Turkey Bacon")
                    .toppingOptional("Jalapenos")
                    .toppingOptional("extra Cheese")
                    .toppingOptional("extra Cheese")

                    .isDiscounted(false)
                    .totalPrice(7.59)
                    .build());
            add(Product.builder()
                    .productType(ProductType.HOT_DOGS)
                    .positionName("American BBQ Hot Dog")

                    .toppingOptional("Extra Turkey Bacon")
                    .toppingOptional("Jalapenos")
                    .toppingOptional("extra Cheese")
                    .toppingOptional("extra Cheese")

                    .isDiscounted(false)
                    .totalPrice(7.59)
                    .build());
        }};
        List<TestEnvironment> test15Environments = new ArrayList<>() {{
            add(new TestEnvironment(Platform.MOBILE, Browser.GOOGLE_CHROME, AppLanguage.CZECH));
        }};
        testData.addAll(genDisCalcTestDataWithDifEnvironments(baseTest15Name, test15Products, test15Environments));

        String baseTest16Name = "T16_SinglePosition_MultipleUnits_WithDifferentOptions_NotDiscounted";
        List<ProductDto> test16Products = new ArrayList<>() {{
            add(Product.builder()
                    .productType(ProductType.HOT_DOGS)
                    .positionName("Danish Style Dog")

                    .toppingOptional("Extra Turkey Bacon")

                    .isDiscounted(false)
                    .totalPrice(4.69)
                    .build());
            add(Product.builder()
                    .productType(ProductType.HOT_DOGS)
                    .positionName("Danish Style Dog")

                    .toppingOptional("Jalapenos")

                    .isDiscounted(false)
                    .totalPrice(4.69)
                    .build());
            add(Product.builder()
                    .productType(ProductType.HOT_DOGS)
                    .positionName("Danish Style Dog")

                    .toppingOptional("extra Cheese")
                    .toppingOptional("extra Cheese")

                    .isDiscounted(false)
                    .totalPrice(5.59)
                    .build());
        }};
        List<TestEnvironment> test16Environments = new ArrayList<>() {{
            add(new TestEnvironment(Platform.DESKTOP, Browser.GOOGLE_CHROME, AppLanguage.ENGLISH));
        }};
        testData.addAll(genDisCalcTestDataWithDifEnvironments(baseTest16Name, test16Products, test16Environments));

        String baseTest17Name = "T17_SinglePositionSet_MultipleUnits_WithEqualOptions";
        List<ProductDto> test17Products = new ArrayList<>() {{
            add(Set.builder()
                    .positionName("Pizza für 2 Sparmenü 1")

                    .part(Product.builder()
                            .positionName("Rindersalami & Peperoni")
                            .build())
                    .part(Product.builder()
                            .positionName("Regina")
                            .build())
                    .part(Product.builder()
                            .positionName("Fanta")
                            .build())

                    .totalPrice(23.50)
                    .build());
            add(Set.builder()
                    .positionName("Pizza für 2 Sparmenü 1")

                    .part(Product.builder()
                            .positionName("Rindersalami & Peperoni")
                            .build())
                    .part(Product.builder()
                            .positionName("Regina")
                            .build())
                    .part(Product.builder()
                            .positionName("Fanta")
                            .build())

                    .totalPrice(23.50)
                    .build());
        }};
        List<TestEnvironment> test17Environments = new ArrayList<>() {{
            add(new TestEnvironment(Platform.DESKTOP, Browser.GOOGLE_CHROME, AppLanguage.DEUTSCH));
        }};
        testData.addAll(genDisCalcTestDataWithDifEnvironments(baseTest17Name, test17Products, test17Environments));

        String baseTest18Name = "T18_SinglePositionSet_MultipleUnits_WithEqualOptions";
        List<ProductDto> test18Products = new ArrayList<>() {{
            add(Set.builder()
                    .positionName("Pizza für 2 Sparmenü 1")

                    .part(Product.builder()
                            .positionName("Margherita")
                            .build())
                    .part(Product.builder()
                            .positionName("Hawaii")
                            .dipExtra("Sweet Chili-Dip")
                            .dipExtra("Sweet Chili-Dip")
                            .build())
                    .part(Product.builder()
                            .positionName("Fanta")
                            .build())

                    .totalPrice(25.90)
                    .build());
            add(Set.builder()
                    .positionName("Pizza für 2 Sparmenü 1")

                    .part(Product.builder()
                            .positionName("Margherita")
                            .ingredientExtra("Bacon")
                            .ingredientExtra("Bacon")
                            .ingredientExtra("Bacon")
                            .build())
                    .part(Product.builder()
                            .positionName("Tonno")
                            .ingredientExtra("Chili con Carne")
                            .build())
                    .part(Product.builder()
                            .positionName("Fanta")
                            .build())

                    .totalPrice(33.10)
                    .build());
        }};
        List<TestEnvironment> test18Environments = new ArrayList<>() {{
            add(new TestEnvironment(Platform.DESKTOP, Browser.GOOGLE_CHROME, AppLanguage.POLISH));
        }};
        testData.addAll(genDisCalcTestDataWithDifEnvironments(baseTest18Name, test18Products, test18Environments));

        return testData;
    }

    private List<AppDiscountCalculationTestData> genDisCalcMultipleProductsTestData() {
        List<AppDiscountCalculationTestData> testData = new ArrayList<>();

        String baseTest19Name = "T19_MultiplePositions_SingleUnit_NoOptions";
        List<ProductDto> test19Products = new ArrayList<>() {{
            add(Product.builder()
                    .productType(ProductType.PASTA)
                    .positionName("Napoli")

                    .pastaType("Tagliatelle")

                    .totalPrice(6.50)
                    .build());
            add(Product.builder()
                    .productType(ProductType.DIPS_AND_SAUCE)
                    .positionName("Ketchup")

                    .totalPrice(0.40)
                    .build());
            add(Product.builder()
                    .productType(ProductType.SPECIAL_BOX)
                    .positionName("21er Box")

                    .totalPrice(13.59)
                    .build());
            add(Product.builder()
                    .productType(ProductType.NACHOS)
                    .positionName("Nachos Chicken Style")

                    .totalPrice(6.20)
                    .build());
            add(Product.builder()
                    .productType(ProductType.HOT_DOGS)
                    .positionName("All American Dog")

                    .isDiscounted(false)
                    .totalPrice(3.79)
                    .build());
        }};
        List<TestEnvironment> test19Environments = new ArrayList<>() {{
            add(new TestEnvironment(Platform.MOBILE, Browser.GOOGLE_CHROME, AppLanguage.DEUTSCH));
        }};
        testData.addAll(genDisCalcTestDataWithDifEnvironments(baseTest19Name, test19Products, test19Environments));

        String baseTest20Name = "T20_MultiplePositions_SingleUnit_WithOptions";
        List<ProductDto> test20Products = new ArrayList<>() {{
            add(Product.builder()
                    .productType(ProductType.POPCORN)
                    .positionName("Popcorn, süß")

                    .size("jumbo")

                    .totalPrice(6.50)
                    .build());
            add(Product.builder()
                    .productType(ProductType.PIZZA_BREAD)
                    .positionName("Pizzabrot mit Kräuterbutter")

                    .size("normal")
                    .ingredientExtra("Mais")
                    .ingredientExtra("Paprika")
                    .ingredientExtra("Knoblauch")
                    .ingredientExtra("Bacon")

                    .totalPrice(10.40)
                    .build());
            add(Product.builder()
                    .productType(ProductType.AMERICAN_BIG_BURGER)
                    .positionName("Big American Barbecue Burger")

                    .size("mit Käse")
                    .toppingOptional("Extra Käse (2 Scheiben)")
                    .toppingOptional("Extra Käse (2 Scheiben)")
                    .sideDish("Pommes + Coca Cola")

                    .totalPrice(15.79)
                    .build());
            add(Product.builder()
                    .productType(ProductType.HOT_DOGS)
                    .positionName("Bacon Flavour Dog")

                    .toppingOptional("Extra Turkey Bacon")
                    .toppingOptional("Extra Turkey Bacon")
                    .toppingOptional("Extra Turkey Bacon")

                    .isDiscounted(false)
                    .totalPrice(6.69)
                    .build());
        }};
        List<TestEnvironment> test20Environments = new ArrayList<>() {{
            add(new TestEnvironment(Platform.MOBILE, Browser.GOOGLE_CHROME, AppLanguage.POLISH));
        }};
        testData.addAll(genDisCalcTestDataWithDifEnvironments(baseTest20Name, test20Products, test20Environments));

        String baseTest21Name = "T21_MultiplePositions_MultipleUnits";
        List<ProductDto> test21Products = new ArrayList<>() {{
            add(Product.builder()
                    .productType(ProductType.WRAPS)
                    .positionName("Mexican Wrap")

                    .totalPrice(6.09)
                    .build());
            add(Product.builder()
                    .productType(ProductType.WRAPS)
                    .positionName("Mexican Wrap")

                    .totalPrice(6.09)
                    .build());
            add(Product.builder()
                    .productType(ProductType.WRAPS)
                    .positionName("Mexican Wrap")

                    .totalPrice(6.09)
                    .build());
            add(Set.builder()
                    .positionName("Pizza für 2 Sparmenü 2")

                    .part(Product.builder()
                            .positionName("Rindersalami")
                            .dipExtra("Hot-Chili-Dip")
                            .dipExtra("Hot-Chili-Dip")
                            .build())
                    .part(Product.builder()
                            .positionName("Rindersalami & Champinions")
                            .ingredientExtra("Gorgonzola")
                            .build())
                    .part(Product.builder()
                            .positionName("Helles Bier")
                            .build())
                    .part(Product.builder()
                            .positionName("Pils")
                            .build())

                    .totalPrice(28.30)
                    .build());
            add(Set.builder()
                    .positionName("Pizza für 2 Sparmenü 2")

                    .part(Product.builder()
                            .positionName("Rindersalami")
                            .dipExtra("Hot-Chili-Dip")
                            .dipExtra("Hot-Chili-Dip")
                            .build())
                    .part(Product.builder()
                            .positionName("Rindersalami & Champinions")
                            .ingredientExtra("Gorgonzola")
                            .build())
                    .part(Product.builder()
                            .positionName("Helles Bier")
                            .build())
                    .part(Product.builder()
                            .positionName("Pils")
                            .build())

                    .totalPrice(28.30)
                    .build());
            add(Set.builder()
                    .positionName("Pizza für 2 Sparmenü 2")

                    .part(Product.builder()
                            .positionName("Funghi")
                            .build())
                    .part(Product.builder()
                            .positionName("Margherita")
                            .ingredientExtra("Chicken Strips")
                            .ingredientExtra("Chicken Strips")
                            .build())
                    .part(Product.builder()
                            .positionName("Dunkles Weizen")
                            .build())
                    .part(Product.builder()
                            .positionName("Helles Weizen")
                            .build())

                    .totalPrice(28.30)
                    .build());
            add(Product.builder()
                    .productType(ProductType.ICE_CREAM)
                    .positionName("Strawberry Cheesecake")

                    .isDiscounted(false)
                    .totalPrice(2.99)
                    .build());
            add(Product.builder()
                    .productType(ProductType.ICE_CREAM)
                    .positionName("Strawberry Cheesecake")

                    .isDiscounted(false)
                    .totalPrice(2.99)
                    .build());
            add(Product.builder()
                    .productType(ProductType.ICE_CREAM)
                    .positionName("Strawberry Cheesecake")

                    .isDiscounted(false)
                    .totalPrice(2.99)
                    .build());
            add(Product.builder()
                    .productType(ProductType.PIZZA)
                    .positionName("Margherita")

                    .totalPrice(5.60)
                    .build());
            add(Product.builder()
                    .productType(ProductType.PIZZA)
                    .positionName("Margherita")

                    .size("mittel")
                    .ingredientExtra("Ananas")

                    .totalPrice(9.90)
                    .build());
            add(Product.builder()
                    .productType(ProductType.PIZZA)
                    .positionName("Margherita")

                    .size("party")
                    .dipExtra("Aioli Dip")
                    .dipExtra("Aioli Dip")
                    .ingredientExtra("Artischocken")

                    .totalPrice(23.40)
                    .build());

            add(Product.builder()
                    .productType(ProductType.DESSERT)
                    .positionName("Filled Vanilla Donut")

                    .isDiscounted(false)
                    .totalPrice(3.20)
                    .build());
            add(Product.builder()
                    .productType(ProductType.DESSERT)
                    .positionName("Filled Vanilla Donut")

                    .isDiscounted(false)
                    .totalPrice(3.20)
                    .build());
        }};
        List<TestEnvironment> test21Environments = new ArrayList<>() {{
            add(new TestEnvironment(Platform.DESKTOP, Browser.GOOGLE_CHROME, AppLanguage.DEUTSCH));
            add(new TestEnvironment(Platform.MOBILE, Browser.GOOGLE_CHROME, AppLanguage.CZECH));
            add(new TestEnvironment(Platform.DESKTOP, Browser.FIREFOX, AppLanguage.ENGLISH));
            add(new TestEnvironment(Platform.MOBILE, Browser.FIREFOX, AppLanguage.DEUTSCH));
            add(new TestEnvironment(Platform.DESKTOP, Browser.OPERA, AppLanguage.POLISH));
            add(new TestEnvironment(Platform.MOBILE, Browser.OPERA, AppLanguage.DEUTSCH));
            add(new TestEnvironment(Platform.DESKTOP, Browser.MICROSOFT_EDGE, AppLanguage.POLISH));
            add(new TestEnvironment(Platform.MOBILE, Browser.MICROSOFT_EDGE, AppLanguage.CZECH));
            add(new TestEnvironment(Platform.MOBILE, Browser.APPLE_SAFARI, AppLanguage.ENGLISH));
            add(new TestEnvironment(Platform.MOBILE, Browser.APPLE_SAFARI, AppLanguage.CZECH));
        }};
        testData.addAll(genDisCalcTestDataWithDifEnvironments(baseTest21Name, test21Products, test21Environments));

        return testData;
    }

    /**
     * Gathers all the test cases in data holder together and returns only those that
     * runs on the specific browser
     */
    public Object[][] getDiscountCalculationSpecificBrowserTestData(Browser browser) {
        List<AppDiscountCalculationTestData> specificBrowserTestData = new ArrayList<>();
        List<AppDiscountCalculationTestData> allTestData = new ArrayList<>();
        allTestData.addAll(disCalcSingleProductSingleUnitTestData);
        allTestData.addAll(disCalcSingleProductMultipleUnitsTestData);
        allTestData.addAll(disCalcMultipleProductsTestData);

        for (AppDiscountCalculationTestData testData : allTestData) {
            if (testData.browser == browser) {
                specificBrowserTestData.add(testData);
            }
        }

        return testDataToObjectsArray(new ArrayList<>(specificBrowserTestData));
    }

    /**
     * Accepts data for one test case and generates several identical test cases with different
     * environment settings
     * @param baseTestName test case name
     * @param testProducts list of products for test case
     * @param testEnvironments list of different environment settings
     * @return generated test cases
     */
    private List<AppDiscountCalculationTestData> genDisCalcTestDataWithDifEnvironments(String baseTestName,
                                                                                       List<ProductDto> testProducts,
                                                                                   List<TestEnvironment> testEnvironments){
        List<AppDiscountCalculationTestData> testData = new ArrayList<>();
        for (TestEnvironment testEnvironment:testEnvironments){
            testData.add(new AppDiscountCalculationTestData(
                    String.format("%s_%s_%s_%s",
                            baseTestName,
                            testEnvironment.platform.getName(),
                            testEnvironment.browser.getWebDriverName(),
                            testEnvironment.appLanguage.getFullName()),
                    testEnvironment.platform, testEnvironment.browser, testEnvironment.appLanguage,
                    testProducts));
        }
        return testData;
    }
    @Getter
    @AllArgsConstructor
    public static class TestEnvironment {
        public final Platform platform;
        public final Browser browser;
        public final AppLanguage appLanguage;
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

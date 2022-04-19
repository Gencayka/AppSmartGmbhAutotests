package ru.Chayka.pageobjects;

import com.codeborne.selenide.*;
import com.codeborne.selenide.ex.ElementNotFound;
import lombok.Getter;
import lombok.NonNull;
import org.openqa.selenium.Dimension;
import ru.Chayka.enums.*;
import ru.Chayka.productdto.ProductDto;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

@Getter
public class MainPageDesktop implements MainPage {
    private AppLanguage currentLanguage;

    private final SelenideElement languageSwitcher;
    private final ElementsCollection switchLanguageButtons;
    private final ElementsCollection menuSectionsButtons;

    public MainPageDesktop(){
        //TODO
        currentLanguage = AppLanguage.DEUTSCH;

        languageSwitcher = $(byClassName("LanguageSwitcher"));
        switchLanguageButtons = languageSwitcher.$$(byClassName("LanguageSwitcher__item"));

        menuSectionsButtons =
                $(byId("category-menu-wrapper"))
                .$(byId("category-menu-desktop-hermes-theme"))
                .$$(byClassName("pointer"));
    }

    public static MainPageDesktop switchToBrowserVer(){
        WebDriverRunner.getWebDriver().manage().window().setSize(new Dimension(1400,800));
        return new MainPageDesktop();
    }

    public void closeWeHaveJustClosedDoc(){
        SelenideElement ourShopIsCurrentlyClosedLabel =
                $(byText(OurShopIsCurrentlyClosedLabel.TITLE.getText(currentLanguage)));
        SelenideElement viewTheMenuButton =
                $(byText(OurShopIsCurrentlyClosedLabel.BUTTON.getText(currentLanguage)));

        if(ourShopIsCurrentlyClosedLabel.isDisplayed()){
            viewTheMenuButton.click();
        }
    }

    public void switchLanguage(@NonNull String language){
        languageSwitcher.click();
        switchLanguageButtons.findBy(text(language)).click();
    }

    public void switchLanguage(@NonNull AppLanguage language){
        switchLanguage(language.getNormalButtonName());
        currentLanguage = language;
    }

    public void chooseMenuSection(@NonNull String menuSection){
        menuSectionsButtons.findBy(exactText(menuSection)).click();
    }

    public void chooseMenuSection(@NonNull ProductType productType){
        chooseMenuSection(productType.getText());
    }

    public void addProductToCart(@NonNull ProductDto product){
        chooseMenuSection(product.getProductType());

        ElementsCollection productsButtons =
                $(byId("products-view-wrapper"))
                        .$$(byClassName("product-small-picture-container"));
        productsButtons.findBy(text(product.getPositionName())).click();

        SelenideElement tellUsYourAddressBlock = $(byClassName("SimpleAddressPromptModal_SimpleAddressPromptModal__5nij8"));
        /*WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), Duration.ofSeconds(1));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(byClassName("SimpleAddressPromptModal_SimpleAddressPromptModal__5nij8")));
        } catch (ElementNotFound ignore){};*/

        try {
            tellUsYourAddressBlock.$(byText(TellUsYourAddressBlockLabel.PICKUP.getText(currentLanguage))).click();
            tellUsYourAddressBlock.$(byText(TellUsYourAddressBlockLabel.CONFIRM.getText(currentLanguage))).click();
        } catch (ElementNotFound ignore){};

        //SelenideElement tellUsYourAddressBlock = $(byClassName("SimpleAddressPromptModal_SimpleAddressPromptModal__5nij8"));
        //tellUsYourAddressBlock.shouldBe(enabled);
        //TODO
        /*System.out.println(tellUsYourAddressBlock);
        if(tellUsYourAddressBlock.isDisplayed()){
            tellUsYourAddressBlock.$(byText(TellUsYourAddressBlockLabel.PICKUP.getText(currentLanguage))).click();
            tellUsYourAddressBlock.$(byText(TellUsYourAddressBlockLabel.CONFIRM.getText(currentLanguage))).click();
        }*/

        SelenideElement productOptionsBlock = $(byClassName("ant-modal"));

        if(productOptionsBlock.isDisplayed()){
            chooseProductOptions(product, productOptionsBlock);
            SelenideElement confirmButton = productOptionsBlock.$(byClassName("positive-action"));
            confirmButton.click();
        }

        System.out.println();
    }

    public void addProductsToCart(@NonNull List<ProductDto> products){
        for(ProductDto product:products){
            addProductToCart(product);
        }
    }

    public void chooseProductOptions(@NonNull ProductDto product,
                                     @NonNull SelenideElement productOptionsBlock){
        ElementsCollection productOptions = productOptionsBlock.$$(byClassName("product-options__accordion__item"));

        /*SelenideElement sauceMenu = productOptions.find(text("Sauce"));
        if(!sauceMenu.has(cssClass("accordion-item_active"))){
            sauceMenu.click();
        }

        ElementsCollection sauceButtons = productOptionsBlock.$$(byClassName("button-ingredient"));
        SelenideElement sauceButton = sauceButtons.find(text("Honig-Senf-Dip"));
        sauceButton.click();*/

        for (ProductOption singularProductOption:ProductOption.singularOptions){
            if(product.getOption(singularProductOption) != null){
                SelenideElement productOptionMenu =
                        productOptions.find(text(singularProductOption.getText(currentLanguage)));
                productOptionMenu.shouldBe(visible);
                if(!productOptionMenu.has(cssClass("accordion-item_active"))){
                    productOptionMenu.click();
                }

                ElementsCollection productOptionButtons =
                        productOptionsBlock.$$(byClassName("button-size"));
                productOptionButtons.find(text(product.getOption(singularProductOption))).click();
            }
        }

        for (ProductOption pluralProductOption:ProductOption.pluralOptions){
            List<String>productOptionsAsString = product.getListOfOptions(pluralProductOption);
            if(!productOptionsAsString.isEmpty()){
                SelenideElement productOptionMenu =
                        productOptions.find(text(pluralProductOption.getText(currentLanguage)));
                productOptionMenu.shouldBe(visible);
                if(!productOptionMenu.has(cssClass("accordion-item_active"))){
                    productOptionMenu.click();
                }

                ElementsCollection productOptionButtons =
                        productOptionsBlock.$$(byClassName("button-ingredient"));
                for(String productOptionAsString:productOptionsAsString){
                    productOptionButtons.find(text(productOptionAsString)).click();
                }
            }
        }

    }

    public void checkDiscount(@NonNull List<ProductDto> products, Double discount){
        SelenideElement orderTotalInfo = $(byId("right-panel-wrapper")).$(byId("order-total"));
        orderTotalInfo.$(byClassName("discount")).shouldHave(text("10%"));

        Double expectedTotalPrice = 0.0;
        for(ProductDto product:products){
            expectedTotalPrice += product.getTotalPrice();
        }

        double expectedTotalPriceWithDiscount = expectedTotalPrice*discount;
        DecimalFormat formatter = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        formatter.setRoundingMode(RoundingMode.DOWN);
        String expectedTotalPriceWithDiscountAsString = formatter.format(expectedTotalPriceWithDiscount);
        expectedTotalPriceWithDiscountAsString = String.format("%.2f", expectedTotalPriceWithDiscount);
        orderTotalInfo.$(byClassName("total-price"))
                .shouldHave(text(expectedTotalPriceWithDiscountAsString));
    }
}

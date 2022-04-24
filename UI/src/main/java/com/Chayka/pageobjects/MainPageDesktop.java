package com.Chayka.pageobjects;

import com.Chayka.commons.Platform;
import com.Chayka.enums.AppLanguage;
import com.Chayka.enums.ProductType;
import com.Chayka.productdto.Product;
import com.Chayka.productdto.ProductDto;
import com.Chayka.productdto.Set;
import com.codeborne.selenide.*;
import com.codeborne.selenide.ex.ElementNotFound;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.Dimension;

import java.util.List;


import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.Chayka.enums.UiElement.*;

/**
 * Page Object that represents desktop version of main page of the application
 */
@Getter
public class MainPageDesktop implements MainPage {
    private AppLanguage currentLanguage;
    private boolean isPickUpOptionChosen;

    private final SelenideElement languageSwitcher;
    private final ElementsCollection menuSectionsButtons;

    public MainPageDesktop() {
        currentLanguage = AppLanguage.DEUTSCH;
        isPickUpOptionChosen = false;

        languageSwitcher = $(byClassName("LanguageSwitcher"));

        menuSectionsButtons =
                $(byId("category-menu-wrapper"))
                        .$(byId("category-menu-desktop-hermes-theme"))
                        .$$(byClassName("pointer"));
    }

    /**
     * Method changes dimensions of the currently opened window with application so the application
     * changes mode to desktop version
     *
     * @return Page Object of opened main page
     */
    public static MainPageDesktop switchToDesktopVer() {
        WebDriverRunner.getWebDriver().manage().window().setSize(new Dimension(1400, 800));
        return new MainPageDesktop();
    }

    /**
     * Closes doc that informs user that shop is currently closed if one is presented
     */
    public void closeWeHaveJustClosedDoc() {
        closeWeHaveJustClosedDoc(currentLanguage);
    }

    public void switchLanguage(@NonNull AppLanguage language) {
        languageSwitcher.click();
        ElementsCollection switchLanguageButtons = languageSwitcher.$$(byClassName("LanguageSwitcher__item"));
        switchLanguageButtons.findBy(text(language.getDesktopButtonName())).click();
        currentLanguage = language;
    }

    public void chooseMenuSection(@NonNull ProductType productType) {
        SelenideElement menuSectionButton = menuSectionsButtons.findBy(exactText(productType.getText()));
        menuSectionButton.scrollIntoView(true);
        menuSectionButton.click();
    }

    public void addProductToCart(@NonNull ProductDto product,
                                 @NonNull Platform platform) {
        chooseMenuSection(product.getProductType());

        if(product instanceof Set){
            ElementsCollection productsButtons =
                    //$(byId("main-offer-wrapper"))
                            //.
            $$(byClassName("product-wrapper"));
            SelenideElement productButton = productsButtons.findBy(text(product.getPositionName()));
            productButton.scrollIntoView(false);
            productButton.click();

            closeTellUsYourAddressBlockWithPickupOption(currentLanguage, platform);
            if(!isPickUpOptionChosen){
                isPickUpOptionChosen = true;
                productButton.click();
            }

            //chooseSetOption((Set)product, currentLanguage);
            chooseSet((Set) product, currentLanguage, platform);
        } else {
            ElementsCollection productsButtons =
                    //$(byId("products-view-wrapper"))
                            //.
            $$(byClassName("product-small-picture-container"));
            SelenideElement productButton = productsButtons.findBy(text(product.getPositionName()));
            productButton.scrollIntoView(false);
            productButton.click();

            closeTellUsYourAddressBlockWithPickupOption(currentLanguage, platform);
            if(!isPickUpOptionChosen){
                isPickUpOptionChosen = true;
            }
        }

        SelenideElement productOptionsBlock = $(byClassName(PRODUCT_OPTIONS_BLOCK.getClassName(platform)));
        launchDelay();
        if (productOptionsBlock.isDisplayed()) {
            SelenideElement confirmButton = productOptionsBlock
                    .$(byClassName(PRODUCT_OPTIONS_CONFIRM_BUTTON.getClassName(platform)));
            chooseProductOptions((Product) product, productOptionsBlock, currentLanguage, Platform.WINDOWS);
            confirmButton.click();
        }
    }

    public void checkDiscount(@NonNull List<ProductDto> products, @NotNull Double discountAsDecimal) {
        Integer discountAsPercents = (int) (discountAsDecimal * 100);
        SelenideElement orderTotalInfo = $(byId("right-panel-wrapper")).$(byId("order-total"));
        orderTotalInfo.scrollIntoView(false);
        orderTotalInfo.$(byClassName("discount")).shouldHave(text(discountAsPercents + "%"));

        Double expectedTotalPrice = 0.0;
        Double expectedDiscount = 0.0;
        for (ProductDto product : products) {
            expectedTotalPrice += product.getTotalPrice();
            if (product.isDiscounted()) {
                expectedDiscount += product.getTotalPrice();
            }
        }
        expectedDiscount = Math.floor(expectedDiscount * discountAsDecimal * 100.0) / 100.0;
        String expectedDiscountAsString = String.format("%.2f", expectedDiscount);
        orderTotalInfo
                .$(byClassName("discount"))
                .$(byClassName("value"))
                .shouldHave(exactText("-" + expectedDiscountAsString + " €"));

        double expectedTotalPriceWithDiscount =
                Math.floor((expectedTotalPrice - expectedDiscount) * 100.0) / 100.0;
        String expectedTotalPriceWithDiscountAsString = String.format("%.2f", expectedTotalPriceWithDiscount);
        orderTotalInfo
                .$(byClassName("total-price"))
                .$(byClassName("value"))
                .shouldHave(exactText(expectedTotalPriceWithDiscountAsString + " €"));
    }
}

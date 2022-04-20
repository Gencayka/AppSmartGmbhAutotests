package com.Chayka.pageobjects;

import com.Chayka.enums.AppLanguage;
import com.Chayka.enums.ProductType;
import com.Chayka.productdto.Set;
import com.codeborne.selenide.*;
import com.codeborne.selenide.ex.ElementNotFound;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.Dimension;
import com.Chayka.productdto.ProductDto;

import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/**
 * Page Object that represents mobile version of main page of the application
 */
@Getter
public class MainPageMobile implements MainPage {
    private AppLanguage currentLanguage;
    private boolean isPickUpOptionChosen;

    private final SelenideElement menuToggleButton;
    private final ElementsCollection menuSectionsButtons;

    public MainPageMobile() {
        currentLanguage = AppLanguage.DEUTSCH;

        menuToggleButton = $(byClassName("menu-toggle-btn"));
        menuSectionsButtons = $(byId("category-slider")).$(byClassName("swiper-wrapper")).$$(byClassName("swiper-slide"));
    }

    /**
     * Method changes dimensions of the currently opened window with application so the application
     * changes mode to mobile version
     *
     * @return Page Object of opened main page
     */
    public static MainPageMobile switchToMobileVer() {
        WebDriverRunner.getWebDriver().manage().window().setSize(new Dimension(700, 800));
        return new MainPageMobile();
    }

    /**
     * Closes doc that informs user that shop is currently closed if one is presented
     */
    public void closeWeHaveJustClosedDoc() {
        closeWeHaveJustClosedDoc(currentLanguage);
    }

    public void switchLanguage(@NonNull AppLanguage language) {
        if (menuToggleButton.$(byClassName("mdi")).has(cssClass("mdi-menu"))) {
            menuToggleButton.click();
        }
        SelenideElement switchLanguageMenuButton = $(byClassName("LanguageSwitcherMobile_LanguageSwitcherMobile__fPLX0"));
        switchLanguageMenuButton.shouldBe(visible);
        switchLanguageMenuButton.click();

        ElementsCollection switchLanguageButtons = $$(byClassName("LanguageSwitcherMobile_item__2bTMG"));
        switchLanguageButtons.findBy(text(language.getMobileButtonName())).click();
        currentLanguage = language;
    }

    public void chooseMenuSection(@NonNull ProductType productType) {
        Selenide.refresh();

        SelenideElement nextMenuSection = menuSectionsButtons.find(cssClass("swiper-slide-next"));
        String lastClickedMenuSectionText = menuSectionsButtons.find(cssClass("slider-link-active")).getText();
        while (!lastClickedMenuSectionText.equals(productType.getText())) {
            lastClickedMenuSectionText = nextMenuSection.getText();
            nextMenuSection.shouldBe(visible);
            nextMenuSection.click();
        }
    }

    public void addProductToCart(@NonNull ProductDto product) {
        chooseMenuSection(product.getProductType());

        if (product instanceof Set) {
            ElementsCollection productsButtons =
                    $(byId("main-products-wrapper"))
                            .$$(byClassName("product-wrapper"));
            SelenideElement productButton = productsButtons.findBy(text(product.getPositionName()));
            while (!productButton.exists()) {
                productsButtons.last().scrollIntoView(true);
            }
            productButton.scrollIntoView(true);
            productButton.click();

            closeTellUsYourAddressBlockWithPickupOption(currentLanguage);
            if (!isPickUpOptionChosen) {
                isPickUpOptionChosen = true;
                productButton.click();
            }

            chooseSetOption((Set) product, currentLanguage);
        } else {
            ElementsCollection productsButtons =
                    $(byId("main-products-wrapper"))
                            .$$(byClassName("product-small-picture-container"));
            SelenideElement productButton = productsButtons.findBy(text(product.getPositionName()));
            while (!productButton.exists()) {
                productsButtons.last().scrollIntoView(true);
            }
            productButton.scrollIntoView(true);
            productButton.click();

            closeTellUsYourAddressBlockWithPickupOption(currentLanguage);
            if(!isPickUpOptionChosen){
                isPickUpOptionChosen = true;
            }
        }

        SelenideElement productOptionsBlock = $(byClassName("ant-modal"));
        Configuration.timeout = 500;
        try {
            productOptionsBlock.exists();
        } catch (ElementNotFound ignore){}
        Configuration.timeout = 4000;

        if (productOptionsBlock.isDisplayed()) {
            SelenideElement confirmButton = productOptionsBlock.$(byClassName("positive-action"));
            chooseProductOptions(product, productOptionsBlock, currentLanguage);
            confirmButton.click();
        }
    }

    public void checkDiscount(@NonNull List<ProductDto> products, @NotNull Double discountAsDecimal) {
        SelenideElement shoppingCartBlock = $(byId("shopping-cart-panel"));
        shoppingCartBlock.click();

        Integer discountAsPercents = (int) (discountAsDecimal * 100);
        SelenideElement orderTotalInfo = $(byId("basket-component")).$(byId("order-total"));
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
                .shouldHave(text(expectedTotalPriceWithDiscountAsString + " €"));
    }
}

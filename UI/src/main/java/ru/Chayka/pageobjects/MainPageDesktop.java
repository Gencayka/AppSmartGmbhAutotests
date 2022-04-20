package ru.Chayka.pageobjects;

import com.codeborne.selenide.*;
import com.codeborne.selenide.ex.ElementNotFound;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.Dimension;
import ru.Chayka.enums.*;
import ru.Chayka.productdto.ProductDto;
import java.util.List;


import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

@Getter
public class MainPageDesktop implements MainPage {
    private AppLanguage currentLanguage;

    private final SelenideElement languageSwitcher;
    private final ElementsCollection menuSectionsButtons;

    public MainPageDesktop(){
        //TODO
        currentLanguage = AppLanguage.DEUTSCH;

        languageSwitcher = $(byClassName("LanguageSwitcher"));

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

    public void switchLanguage(@NonNull AppLanguage language){
        languageSwitcher.click();
        ElementsCollection switchLanguageButtons = languageSwitcher.$$(byClassName("LanguageSwitcher__item"));
        switchLanguageButtons.findBy(text(language.getDesktopButtonName())).click();
        currentLanguage = language;
    }

    public void chooseMenuSection(@NonNull ProductType productType){
        SelenideElement menuSectionButton = menuSectionsButtons.findBy(exactText(productType.getText()));
        menuSectionButton.scrollIntoView(true);
        menuSectionButton.click();
    }

    public void addProductToCart(@NonNull ProductDto product){
        chooseMenuSection(product.getProductType());

        ElementsCollection productsButtons =
                $(byId("products-view-wrapper"))
                        .$$(byClassName("product-small-picture-container"));
        SelenideElement productButton = productsButtons.findBy(text(product.getPositionName()));
        productButton.scrollIntoView(false);
        productButton.click();

        closeTellUsYourAddressBlockWithPickupOption(currentLanguage);

        SelenideElement productOptionsBlock = $(byClassName("ant-modal"));

        if(productOptionsBlock.isDisplayed()){
            chooseProductOptions(product, productOptionsBlock, currentLanguage);
            SelenideElement confirmButton = productOptionsBlock.$(byClassName("positive-action"));
            confirmButton.click();
        }

        System.out.println();
    }

    public void checkDiscount(@NonNull List<ProductDto> products, @NotNull Double discountAsDecimal){
        Integer discountAsPercents = (int)(discountAsDecimal*100);
        SelenideElement orderTotalInfo = $(byId("right-panel-wrapper")).$(byId("order-total"));
        orderTotalInfo.scrollIntoView(false);
        orderTotalInfo.$(byClassName("discount")).shouldHave(text(discountAsPercents + "%"));

        Double expectedTotalPrice = 0.0;
        Double expectedDiscount = 0.0;
        for(ProductDto product:products){
            expectedTotalPrice += product.getTotalPrice();
            if(product.isDiscounted()){
                expectedDiscount += product.getTotalPrice();
            }
        }
        expectedDiscount = expectedDiscount*discountAsDecimal;

        double expectedTotalPriceWithDiscount = expectedTotalPrice - expectedDiscount;
        String expectedTotalPriceWithDiscountAsString = String.format("%.2f", expectedTotalPriceWithDiscount);
        orderTotalInfo.$(byClassName("total-price"))
                .shouldHave(text(expectedTotalPriceWithDiscountAsString + " €"));
    }
}

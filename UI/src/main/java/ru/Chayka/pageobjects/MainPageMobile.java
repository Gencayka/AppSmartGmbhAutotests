package ru.Chayka.pageobjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.ex.ElementNotFound;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.Dimension;
import ru.Chayka.enums.*;
import ru.Chayka.productdto.ProductDto;

import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Getter
public class MainPageMobile implements MainPage {
    private AppLanguage currentLanguage;

    private final SelenideElement menuToggleButton;
    private final ElementsCollection menuSectionsButtons;

    public MainPageMobile(){
        menuToggleButton = $(byClassName("menu-toggle-btn"));
        menuSectionsButtons = $(byId("category-slider")).$(byClassName("swiper-wrapper")).$$(byClassName("swiper-slide"));
    }

    public static MainPageMobile switchToMobileVer(){
        WebDriverRunner.getWebDriver().manage().window().setSize(new Dimension(700,700));
        return new MainPageMobile();
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
        if(menuToggleButton.$(byClassName("mdi")).has(cssClass("mdi-menu"))){
            menuToggleButton.click();
        }
        SelenideElement switchLanguageMenuButton = $(byClassName("LanguageSwitcherMobile_LanguageSwitcherMobile__fPLX0"));
        switchLanguageMenuButton.shouldBe(visible);
        switchLanguageMenuButton.click();

        ElementsCollection switchLanguageButtons = $$(byClassName("LanguageSwitcherMobile_item__2bTMG"));
        switchLanguageButtons.findBy(text(language.getMobileButtonName())).click();
        //menuToggleButton.click();
        currentLanguage = language;
    }

    public void chooseMenuSection(@NonNull ProductType productType){
        Selenide.refresh();

        SelenideElement nextMenuSection = menuSectionsButtons.find(cssClass("swiper-slide-next"));
        String lastClickedMenuSectionText = menuSectionsButtons.find(cssClass("slider-link-active")).getText();
        while (!lastClickedMenuSectionText.equals(productType.getText())){
            lastClickedMenuSectionText = nextMenuSection.getText();
            nextMenuSection.shouldBe(visible);
            nextMenuSection.click();
        }
    }

    public void addProductToCart(@NonNull ProductDto product){
        chooseMenuSection(product.getProductType());

        ElementsCollection productsButtons =
                $(byId("main-products-wrapper"))
                        .$$(byClassName("product-small-picture-container"));
        SelenideElement productButton = productsButtons.findBy(text(product.getPositionName()));
        productButton.scrollIntoView(true);
        productButton.click();

        closeTellUsYourAddressBlockWithPickupOption(currentLanguage);

        SelenideElement productOptionsBlock = $(byClassName("ant-modal-body"));

        if(productOptionsBlock.isDisplayed()){
            chooseProductOptions(product, productOptionsBlock, currentLanguage);
            SelenideElement confirmButton = productOptionsBlock.$(byClassName("positive-action"));
            confirmButton.click();
        }

        System.out.println();
    }

    public void checkDiscount(@NonNull List<ProductDto> products, @NotNull Double discountAsDecimal){
        SelenideElement shoppingCartBlock = $(byId("shopping-cart-panel"));
        shoppingCartBlock.click();

        Integer discountAsPercents = (int)(discountAsDecimal*100);
        SelenideElement orderTotalInfo = $(byId("basket-component")).$(byId("order-total"));
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

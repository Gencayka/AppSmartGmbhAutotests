package com.Chayka.pageobjects;

import com.Chayka.AppUITestConfig;
import com.Chayka.commons.Platform;
import com.Chayka.enums.AppLanguage;
import com.Chayka.enums.ProductType;
import com.codeborne.selenide.*;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import com.Chayka.productdto.ProductDto;

import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.Chayka.enums.UiElement.*;

/**
 * Page Object that represents mobile version of main page of the application
 */
@Getter
public class MainPageMobile extends MainPage {
    private final SelenideElement menuToggleButton;
    private final ElementsCollection menuSectionsButtons;

    public MainPageMobile() {
        currentLanguage = AppLanguage.DEUTSCH;
        isPickUpOptionChosen = false;

        menuToggleButton = $(byClassName("menu-toggle-btn"));
        menuSectionsButtons =
                $(byId("category-slider"))
                        .$(byClassName("swiper-wrapper"))
                        .$$(byClassName("swiper-slide"));
    }

    /**
     * Method changes dimensions of the currently opened window with application so the application
     * changes mode to mobile version
     *
     * @return Page Object of opened main page
     */
    public static MainPageMobile switchToMobileVer() {
        WebDriverRunner.getWebDriver().manage().window()
                .setSize(AppUITestConfig.getUniqueInstance().getMobileVerWindowSize());
        return new MainPageMobile();
    }

    @Override
    public void switchLanguage(@NonNull AppLanguage language) {
        if (menuToggleButton.$(byClassName("mdi")).has(cssClass("mdi-menu"))) {
            menuToggleButton.click();
        }
        SelenideElement switchLanguageMenuButton =
                $(byClassName(LANGUAGE_SWITCHER.getClassName(Platform.MOBILE)));
        switchLanguageMenuButton.shouldBe(visible);
        switchLanguageMenuButton.click();

        ElementsCollection switchLanguageButtons =
                $$(byClassName(LANGUAGE_SWITCHER_BUTTON.getClassName(Platform.MOBILE)));
        switchLanguageButtons.findBy(text(language.getMobileButtonName())).click();
        currentLanguage = language;
    }

    @Override
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

    @Override
    public void addProductToCart(@NonNull ProductDto product) {
        addProductToCart(product, Platform.MOBILE);
    }

    @Override
    public void addProductsToCart(@NonNull List<ProductDto> products) {
        for (ProductDto product : products) {
            addProductToCart(product, Platform.MOBILE);
        }
    }

    @Override
    public void checkDiscount(@NonNull List<ProductDto> products, @NotNull Double discountAsDecimal) {
        checkDiscount(products, discountAsDecimal, Platform.MOBILE);
    }
}

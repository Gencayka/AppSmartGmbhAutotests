package com.Chayka.pageobjects;

import com.Chayka.AppUITestConfig;
import com.Chayka.commons.Platform;
import com.Chayka.enums.AppLanguage;
import com.Chayka.enums.ProductType;
import com.Chayka.productdto.ProductDto;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;
import static com.Chayka.enums.UiElement.*;

/**
 * Page Object that represents desktop version of main page of the application
 */
@Getter
public class MainPageDesktop extends MainPage {
    private final ElementsCollection menuSectionsButtons;

    public MainPageDesktop() {
        currentLanguage = AppLanguage.DEUTSCH;
        isPickUpOptionChosen = false;

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
        WebDriverRunner.getWebDriver().manage().window()
                .setSize(AppUITestConfig.getUniqueInstance().getDesktopVerWindowSize());
        return new MainPageDesktop();
    }

    @Override
    public void switchLanguage(@NonNull AppLanguage language) {
        SelenideElement languageSwitcher =
                $(byClassName(LANGUAGE_SWITCHER.getClassName(Platform.DESKTOP)));
        languageSwitcher.click();
        ElementsCollection switchLanguageButtons = languageSwitcher
                .$$(byClassName(LANGUAGE_SWITCHER_BUTTON.getClassName(Platform.DESKTOP)));
        switchLanguageButtons.findBy(text(language.getDesktopButtonName())).click();
        currentLanguage = language;
    }

    @Override
    public void chooseMenuSection(@NonNull ProductType productType) {
        SelenideElement menuSectionButton = menuSectionsButtons.findBy(exactText(productType.getText()));
        menuSectionButton.scrollIntoView(true);
        menuSectionButton.click();
    }

    @Override
    public void addProductToCart(@NonNull ProductDto product) {
        addProductToCart(product, Platform.DESKTOP);
    }

    @Override
    public void addProductsToCart(@NonNull List<ProductDto> products) {
        for (ProductDto product : products) {
            addProductToCart(product, Platform.DESKTOP);
        }
    }

    @Override
    public void checkDiscount(@NonNull List<ProductDto> products, @NotNull Double discountAsDecimal) {
        checkDiscount(products, discountAsDecimal, Platform.DESKTOP);
    }
}

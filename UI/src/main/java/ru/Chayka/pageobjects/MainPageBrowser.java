package ru.Chayka.pageobjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import lombok.Getter;
import lombok.NonNull;
import org.openqa.selenium.Dimension;
import ru.Chayka.ProductWithOptions;
import ru.Chayka.enums.AppLanguage;
import ru.Chayka.enums.OurShopIsCurrentlyClosedLabel;
import ru.Chayka.enums.ProductType;
import ru.Chayka.enums.TellUsYourAddressBlockLabel;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

@Getter
public class MainPageBrowser implements MainPage {
    private AppLanguage currentLanguage;

    //private final SelenideElement ourShopIsCurrentlyClosedLabel;
    //private final SelenideElement viewTheMenuButton;

    private final SelenideElement languageSwitcher;
    private final ElementsCollection switchLanguageButtons;
    private final ElementsCollection menuSectionsButtons;

    public MainPageBrowser(){
        //TODO
        currentLanguage = AppLanguage.DEUTSCH;

        //ourShopIsCurrentlyClosedLabel = $(byText("Wir haben gerade geschlossen."));
        //viewTheMenuButton = $(byText("Speisekarte ansehen"));

        languageSwitcher = $(byClassName("LanguageSwitcher"));
        switchLanguageButtons = languageSwitcher.$$(byClassName("LanguageSwitcher__item"));

        menuSectionsButtons =
                $(byId("category-menu-wrapper"))
                .$(byId("category-menu-desktop-hermes-theme"))
                .$$(byClassName("pointer"));
    }

    public static MainPageBrowser switchToBrowserVer(){
        WebDriverRunner.getWebDriver().manage().window().setSize(new Dimension(1400,800));
        return new MainPageBrowser();
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
        menuSectionsButtons.findBy(text(menuSection)).click();
    }

    public void chooseMenuSection(@NonNull ProductType productType){
        chooseMenuSection(productType.getText());
    }

    public void addProductToCart(@NonNull ProductWithOptions productWithOptions){
        chooseMenuSection(productWithOptions.getProductType());

        ElementsCollection productsButtons =
                $(byId("products-view-wrapper"))
                        .$$(byClassName("product-small-picture-container"));
        productsButtons.findBy(text(productWithOptions.getProductName())).click();

        SelenideElement tellUsYourAddressBlock = $(byClassName("SimpleAddressPromptModal_SimpleAddressPromptModal__5nij8"));
        //if(tellUsYourAddressBlock.isDisplayed()){
            tellUsYourAddressBlock.$(byText(TellUsYourAddressBlockLabel.PICKUP.getText(currentLanguage))).click();
            tellUsYourAddressBlock.$(byText(TellUsYourAddressBlockLabel.CONFIRM.getText(currentLanguage))).click();
        //}



        System.out.println();
    }

    public void chooseProductOptions(ProductWithOptions productWithOptions, SelenideElement productOptionsBlock){
        ElementsCollection productOptions = productOptionsBlock.$$(byClassName("product-options__accordion"));

    }
}

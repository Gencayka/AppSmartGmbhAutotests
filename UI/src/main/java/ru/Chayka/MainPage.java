package ru.Chayka;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

@Getter
public class MainPage {
    private final SelenideElement ourShopIsCurrentlyClosedLabel;
    private final SelenideElement viewTheMenuButton;
    private final SelenideElement fff;

    private final ElementsCollection switchLanguageNormalButtons;
    private final ElementsCollection switchLanguageMobileButtons;

    public MainPage(){
        ourShopIsCurrentlyClosedLabel = $(byText("Wir haben gerade geschlossen."));
        viewTheMenuButton = $(byText("Speisekarte ansehen"));
        fff = $(byText("fff"));

        switchLanguageNormalButtons = $$(byClassName("LanguageSwitcher__item"));
        switchLanguageMobileButtons = $$(byClassName("LanguageSwitcherMobile_item__2bTMG"));
    }

    public void closeWeHaveJustClosedDoc(){
        if(ourShopIsCurrentlyClosedLabel.isDisplayed()){
            viewTheMenuButton.click();
        }
    }

    public void switchLanguageNormal(String language){
        $(byClassName("LanguageSwitcher")).click();
        switchLanguageNormalButtons.findBy(text(language)).click();
    }

    public void switchLanguageNormal(AppLanguage language){
        switchLanguageNormal(language.getNormalButtonName());
    }

    public void switchLanguageMobile(String language){
        $(byClassName("menu-toggle-btn")).click();
        $(byClassName("LanguageSwitcherMobile_LanguageSwitcherMobile__fPLX0")).click();
        switchLanguageMobileButtons.findBy(text(language)).click();
    }

    public void switchLanguageMobile(AppLanguage language){
        switchLanguageMobile(language.getShortName());
    }
}

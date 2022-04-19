package ru.Chayka.pageobjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import lombok.Getter;
import org.openqa.selenium.Dimension;
import ru.Chayka.enums.AppLanguage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Getter
public class MainPageMobile {
    private final SelenideElement ourShopIsCurrentlyClosedLabel;
    private final SelenideElement viewTheMenuButton;

    private final ElementsCollection switchLanguageButtons;

    public MainPageMobile(){
        ourShopIsCurrentlyClosedLabel = $(byText("Wir haben gerade geschlossen."));
        viewTheMenuButton = $(byText("Speisekarte ansehen"));

        switchLanguageButtons = $$(byClassName("LanguageSwitcherMobile_item__2bTMG"));
    }

    public static MainPageMobile switchToMobileVer(){
        WebDriverRunner.getWebDriver().manage().window().setSize(new Dimension(400,800));
        return new MainPageMobile();
    }

    public void closeWeHaveJustClosedDoc(){
        if(ourShopIsCurrentlyClosedLabel.isDisplayed()){
            viewTheMenuButton.click();
        }
    }

    public void switchLanguage(String language){
        $(byClassName("menu-toggle-btn")).click();
        $(byClassName("LanguageSwitcherMobile_LanguageSwitcherMobile__fPLX0")).click();
        switchLanguageButtons.findBy(text(language)).click();
    }

    public void switchLanguage(AppLanguage language){
        switchLanguage(language.getShortName());
    }
}

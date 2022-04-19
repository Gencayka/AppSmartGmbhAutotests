package ru.Chayka.pageobjects;

import com.codeborne.selenide.SelenideElement;
import lombok.NonNull;
import ru.Chayka.ProductWithOptions;
import ru.Chayka.enums.AppLanguage;
import ru.Chayka.enums.ProductType;

public interface MainPage {
    void closeWeHaveJustClosedDoc();
    void switchLanguage(@NonNull String language);
    void switchLanguage(@NonNull AppLanguage language);
    void chooseMenuSection(@NonNull String menuSection);
    void chooseMenuSection(@NonNull ProductType productType);
    void addProductToCart(@NonNull ProductWithOptions productWithOptions);
    void chooseProductOptions(ProductWithOptions productWithOptions, SelenideElement productOptionsBlock);
}

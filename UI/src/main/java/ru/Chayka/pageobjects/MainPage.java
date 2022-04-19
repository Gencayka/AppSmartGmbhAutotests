package ru.Chayka.pageobjects;

import com.codeborne.selenide.SelenideElement;
import lombok.NonNull;
import ru.Chayka.productdto.ProductDto;
import ru.Chayka.productdto.ProductWithOptions;
import ru.Chayka.enums.AppLanguage;
import ru.Chayka.enums.ProductType;

import java.util.List;

public interface MainPage {
    void closeWeHaveJustClosedDoc();
    void switchLanguage(@NonNull String language);
    void switchLanguage(@NonNull AppLanguage language);
    void chooseMenuSection(@NonNull String menuSection);
    void chooseMenuSection(@NonNull ProductType productType);
    void addProductToCart(@NonNull ProductDto product);
    void chooseProductOptions(@NonNull ProductDto product, @NonNull SelenideElement productOptionsBlock);
    void addProductsToCart(@NonNull List<ProductDto> products);
    void checkDiscount(@NonNull List<ProductDto> products, Double discount);
}

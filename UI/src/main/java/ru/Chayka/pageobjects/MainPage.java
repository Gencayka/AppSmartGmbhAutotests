package ru.Chayka.pageobjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import lombok.NonNull;
import ru.Chayka.enums.ProductOption;
import ru.Chayka.enums.TellUsYourAddressBlockLabel;
import ru.Chayka.productdto.ProductDto;
import ru.Chayka.productdto.ProductWithOptions;
import ru.Chayka.enums.AppLanguage;
import ru.Chayka.enums.ProductType;

import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public interface MainPage {
    void closeWeHaveJustClosedDoc();
    void switchLanguage(@NonNull AppLanguage language);
    void chooseMenuSection(@NonNull ProductType productType);
    void addProductToCart(@NonNull ProductDto product);
    void checkDiscount(@NonNull List<ProductDto> products, @NonNull Double discountAsDecimal);

    default void addProductsToCart(@NonNull List<ProductDto> products){
        for(ProductDto product:products){
            addProductToCart(product);
        }
    };

    default void closeTellUsYourAddressBlockWithPickupOption(@NonNull AppLanguage currentLanguage){
        SelenideElement tellUsYourAddressBlock = $(byClassName("SimpleAddressPromptModal_SimpleAddressPromptModal__5nij8"));
        try {
            tellUsYourAddressBlock.$(byText(TellUsYourAddressBlockLabel.PICKUP.getText(currentLanguage))).click();
            tellUsYourAddressBlock.$(byText(TellUsYourAddressBlockLabel.CONFIRM.getText(currentLanguage))).click();
        } catch (ElementNotFound ignore){};
    }

    default void chooseProductOptions(@NonNull ProductDto product,
                                     @NonNull SelenideElement productOptionsBlock,
                                      @NonNull AppLanguage currentLanguage){
        ElementsCollection productOptions = productOptionsBlock.$$(byClassName("product-options__accordion__item"));

        for (ProductOption singularProductOption:ProductOption.singularOptions){
            if(product.getOption(singularProductOption) != null){
                SelenideElement productOptionMenu =
                        productOptions.find(text(singularProductOption.getText(currentLanguage)));
                productOptionMenu.shouldBe(visible);
                if(!productOptionMenu.has(cssClass("accordion-item_active"))){
                    productOptionMenu.click();
                }

                ElementsCollection productOptionButtons =
                        productOptionsBlock.$$(byClassName("button-size"));
                productOptionButtons.find(text(product.getOption(singularProductOption))).click();
            }
        }

        for (ProductOption pluralProductOption:ProductOption.pluralOptions){
            List<String>productOptionsAsString = product.getListOfOptions(pluralProductOption);
            if(!productOptionsAsString.isEmpty()){
                SelenideElement productOptionMenu =
                        productOptions.find(text(pluralProductOption.getText(currentLanguage)));
                productOptionMenu.shouldBe(visible);
                if(!productOptionMenu.has(cssClass("accordion-item_active"))){
                    productOptionMenu.click();
                }

                ElementsCollection productOptionButtons =
                        productOptionsBlock.$$(byClassName("button-ingredient"));
                for(String productOptionAsString:productOptionsAsString){
                    productOptionButtons.find(text(productOptionAsString)).click();
                }
            }
        }
    }
}

package com.Chayka.pageobjects;

import com.Chayka.enums.*;
import com.Chayka.productdto.Set;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import lombok.NonNull;
import com.Chayka.productdto.ProductDto;

import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

/**
 * Interface for Page Object that represents main page of the application
 */
public interface MainPage {
    /**
     * Closes doc that informs user that shop is currently closed if one is presented
     */
    void closeWeHaveJustClosedDoc();

    /**
     * Switches language of the application
     * <br>Fails the test if unable to do it
     * @param language required language
     */
    void switchLanguage(@NonNull AppLanguage language);

    /**
     * Chooses menu section of required food type
     * @param productType required food type
     */
    void chooseMenuSection(@NonNull ProductType productType);

    /**
     * Adds product to shopping card with all the options that are described in the ProductDTO.
     * Adds only non null options, ignores all the nulls
     * @param product productDTO
     */
    void addProductToCart(@NonNull ProductDto product);

    /**
     * Checks that discount is showed and calculated properly, compares expected price
     * (calculated with input productDTO list) with real price
     * @param products list of products, that are supposed to be in the shopping cart
     * @param discountAsDecimal discount presented as decimal (for example 0.1 represents 10% discount)
     */
    void checkDiscount(@NonNull List<ProductDto> products, @NonNull Double discountAsDecimal);

    /**
     * Adds products to shopping card with all the options that are described in the ProductDTO.
     * Adds only non null options, ignores all the nulls
     * @param products list of productDTOs
     */
    default void addProductsToCart(@NonNull List<ProductDto> products){
        for(ProductDto product:products){
            addProductToCart(product);
        }
    }

    /**
     * Checks if shop is opened, fails the test otherwise
     */
    default void openingHoursCheck(){
        $(byClassName("opening-time-message")).shouldNotHave(cssClass("closed"));
    }

    /**
     * Closes doc that informs user that shop is currently closed if one is presented
     */
    default void closeWeHaveJustClosedDoc(@NonNull AppLanguage currentLanguage){
        SelenideElement ourShopIsCurrentlyClosedLabel =
                $(byText(OurShopIsCurrentlyClosedLabel.TITLE.getText(currentLanguage)));
        SelenideElement viewTheMenuButton =
                $(byText(OurShopIsCurrentlyClosedLabel.BUTTON.getText(currentLanguage)));

        if(ourShopIsCurrentlyClosedLabel.isDisplayed()){
            viewTheMenuButton.click();
        }
    }

    /**
     * Closes doc that asks user to enter their address, chooses the pickup option that
     * doesn't require address
     * @param currentLanguage current language of the application
     */
    default void closeTellUsYourAddressBlockWithPickupOption(@NonNull AppLanguage currentLanguage){
        SelenideElement tellUsYourAddressBlock = $(byClassName("SimpleAddressPromptModal_SimpleAddressPromptModal__5nij8"));
        Configuration.timeout = 500;
        try {
            tellUsYourAddressBlock.$(byText(TellUsYourAddressBlockLabel.PICKUP.getText(currentLanguage))).click();
            tellUsYourAddressBlock.$(byText(TellUsYourAddressBlockLabel.CONFIRM.getText(currentLanguage))).click();
        } catch (ElementNotFound ignore){}
        Configuration.timeout = 4000;
    }

    /**
     * Considering that block with different product options is opened chooses all the required
     * options that are described in the ProductDTO.
     * Adds only non null options, ignores all the nulls
     * @param product productDTO
     * @param productOptionsBlock block for choosing product options
     * @param currentLanguage current language of the application
     */
    default void chooseProductOptions(@NonNull ProductDto product,
                                      @NonNull SelenideElement productOptionsBlock,
                                      @NonNull AppLanguage currentLanguage){
        ElementsCollection productOptions = productOptionsBlock.$$(byClassName("accordion-item"));

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
            if(productOptionsAsString != null && !productOptionsAsString.isEmpty()){
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

    /**
     * Considering that block with set options is opened chooses set option that is described
     * in the Set
     */
    default void chooseSetOption(@NonNull Set set, @NonNull AppLanguage currentLanguage){
        SelenideElement setOptionsBlock = $(byClassName("ant-modal"));
        ElementsCollection setOptions = setOptionsBlock.$$(byClassName("offer-product-item"));
        for(String setRequiredOption:set.getSetOptions()){
            SelenideElement setOption = setOptions.find(text(setRequiredOption));
            setOption.$(byClassName("button-accent")).click();
            SelenideElement nextButton = setOptionsBlock.$(byClassName("button-order"));
            nextButton.click();
            if(nextButton.has(exactText(ProductOptionsBlockLabel.NEXT_STEP.getText(currentLanguage)))){
                nextButton.click();
            }
        }
    }
}

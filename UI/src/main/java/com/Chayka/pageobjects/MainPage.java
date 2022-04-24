package com.Chayka.pageobjects;

import com.Chayka.AppUITestConfig;
import com.Chayka.commons.Platform;
import com.Chayka.enums.*;
import com.Chayka.productdto.Product;
import com.Chayka.productdto.Set;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.NonNull;
import com.Chayka.productdto.ProductDto;

import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.Chayka.enums.UiElement.*;

/**
 * Interface for Page Object that represents main page of the application
 */
public interface MainPage {
    /**
     * Launches a delay (haven't found a method like this in Selenide)
     * @param millis delay length in milliseconds
     */
    default void launchDelay(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignore){}
    }

    /**
     * Launches a delay with default length (haven't found a method like this in Selenide)
     */
    default void launchDelay(){
        launchDelay(AppUITestConfig.getUniqueInstance().getDefaultDelayLength());
    }

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
    void addProductToCart(@NonNull ProductDto product, @NonNull Platform platform);

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
    default void addProductsToCart(@NonNull List<ProductDto> products,
                                   @NonNull Platform platform){
        for(ProductDto product:products){
            addProductToCart(product, platform);
        }
    }

    /**
     * Checks if shop is opened, fails the test otherwise
     */
    default void openingHoursCheck(@NonNull Platform platform){
        $(byClassName(OPENING_TIME_DIV.getClassName(platform))).shouldNotHave(cssClass("closed"));
    }

    /**
     * Closes doc that informs user that shop is currently closed if one is presented
     */
    default void closeWeHaveJustClosedDoc(@NonNull AppLanguage currentLanguage){
        SelenideElement ourShopIsCurrentlyClosedLabel =
                $(byText(OurShopIsCurrentlyClosedLabel.TITLE.getText(currentLanguage)));
        SelenideElement viewTheMenuButton =
                $(byText(OurShopIsCurrentlyClosedLabel.BUTTON.getText(currentLanguage)));

        launchDelay();
        if(ourShopIsCurrentlyClosedLabel.isDisplayed()){
            viewTheMenuButton.click();
        }
    }

    /**
     * Closes doc that asks user to enter their address, chooses the pickup option that
     * doesn't require address
     * @param currentLanguage current language of the application
     */
    default void closeTellUsYourAddressBlockWithPickupOption(@NonNull AppLanguage currentLanguage,
                                                             @NonNull Platform platform){
        SelenideElement tellUsYourAddressBlock =
                $(byClassName(TELL_US_YOUR_ADDRESS_BLOCK.getClassName(platform)));
        launchDelay();
        if(tellUsYourAddressBlock.exists()){
            tellUsYourAddressBlock.$(byText(TellUsYourAddressBlockLabel.PICKUP.getText(currentLanguage))).click();
            tellUsYourAddressBlock.$(byText(TellUsYourAddressBlockLabel.CONFIRM.getText(currentLanguage))).click();
        }
    }

    /**
     * Considering that block with different product options is opened chooses all the required
     * options that are described in the ProductDTO.
     * Adds only non null options, ignores all the nulls
     * @param product productDTO
     * @param productOptionsBlock block for choosing product options
     * @param currentLanguage current language of the application
     */
    default void chooseProductOptions(@NonNull Product product,
                                      @NonNull SelenideElement productOptionsBlock,
                                      @NonNull AppLanguage currentLanguage,
                                      @NonNull Platform platform){
        ElementsCollection productOptions = productOptionsBlock
                .$$(byClassName(PRODUCT_OPTION_HEADER.getClassName(platform)));

        for (ProductOption singularProductOption:ProductOption.singularOptions){
            if(product.getOption(singularProductOption) != null){
                SelenideElement productOptionHeader =
                        productOptions.find(text(singularProductOption.getText(currentLanguage)));
                productOptionHeader.shouldBe(visible);
                if(!productOptionHeader
                        .has(cssClass(PRODUCT_OPTIONS_HEADER_ACTIVE.getClassName(platform)))){
                    productOptionHeader.click();
                }

                ElementsCollection productOptionButtons =
                        productOptionsBlock.$$(byClassName(PRODUCT_SINGULAR_OPTION_BUTTON.getClassName(platform)));
                productOptionButtons.find(text(product.getOption(singularProductOption))).click();
            }
        }

        for (ProductOption pluralProductOption:ProductOption.pluralOptions){
            List<String>productOptionsAsString = product.getListOfOptions(pluralProductOption);
            if(productOptionsAsString != null && !productOptionsAsString.isEmpty()){
                SelenideElement productOptionMenu =
                        productOptions.find(text(pluralProductOption.getText(currentLanguage)));
                productOptionMenu.shouldBe(visible);
                if(!productOptionMenu.has(cssClass(PRODUCT_OPTIONS_HEADER_ACTIVE.getClassName(platform)))){
                    productOptionMenu.click();
                }

                ElementsCollection productOptionButtons = productOptionsBlock
                                .$$(byClassName(PRODUCT_PLURAL_OPTION_BUTTON.getClassName(platform)));
                for(String productOptionAsString:productOptionsAsString){
                    productOptionButtons.find(text(productOptionAsString)).click();
                }}
        }
    }

    default void chooseProductOptions(@NonNull Product product,
                                      @NonNull AppLanguage currentLanguage,
                                      @NonNull Platform platform){
        chooseProductOptions(product, $(byClassName(PRODUCT_OPTIONS_BLOCK.getClassName(platform))), currentLanguage, platform);
    }

    default void chooseSet(@NonNull Set set,
                           @NonNull AppLanguage currentLanguage,
                           @NonNull Platform platform){
        SelenideElement setBlock = $(byClassName(SET_BLOCK.getClassName(platform)));
        ElementsCollection setPartVariants = setBlock.$$(byClassName(SET_PART_VARIANT_DIV.getClassName(platform)));
        SelenideElement nextButton = setBlock.$(byClassName(SET_NEXT_BUTTON.getClassName(platform)));
        for (Product setPart: set.getParts()){
            String firstSetPartVariantName = setPartVariants.first()
                    .$(byClassName(SET_PART_VARIANT_LABEL.getClassName(platform))).getText();
            SelenideElement rightPartVariant = setPartVariants.find(text(setPart.getPositionName()));
            if(!firstSetPartVariantName.equals(setPart.getPositionName())){
                rightPartVariant.preceding(0).scrollIntoView(true);
            }

            rightPartVariant.$(byClassName(SET_PART_VARIANT_BUTTON.getClassName(platform))).click();
            nextButton.click();

            launchDelay();
            if(setBlock.exists() && !setBlock.find(byClassName("offer-step-1")).exists()){
                chooseProductOptions(setPart, currentLanguage, platform);
                nextButton.click();
            }
        }
    }
}

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
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;
import static com.Chayka.enums.UiElement.*;
import static com.codeborne.selenide.Selenide.$$;

/**
 * Page Object that represents main page of the application
 * <br>Each child class represents main page on the specific platform
 */
public abstract class MainPage {
    protected AppLanguage currentLanguage;
    protected Boolean isPickUpOptionChosen;

    /**
     * Switches language of the application
     * <br>Fails the test if unable to do it
     *
     * @param language required language
     */
    abstract public void switchLanguage(@NonNull AppLanguage language);

    /**
     * Chooses menu section of required food type
     *
     * @param productType required food type
     */
    abstract public void chooseMenuSection(@NonNull ProductType productType);

    /**
     * Adds product to shopping card with all the options that are described in the ProductDTO.
     * Adds only non null options, ignores all the nulls
     *
     * @param product productDTO
     */
    abstract public void addProductToCart(@NonNull ProductDto product);

    /**
     * Checks that discount is showed and calculated properly, compares expected price
     * (calculated with input productDTO list) with real price
     *
     * @param products          list of products, that are supposed to be in the shopping cart
     * @param discountAsDecimal discount presented as decimal (for example 0.1 represents 10% discount)
     */
    abstract public void checkDiscount(@NonNull List<ProductDto> products, @NotNull Double discountAsDecimal);

    /**
     * Adds products to shopping card with all the options that are described in the ProductDTO.
     * Adds only non null options, ignores all the nulls
     *
     * @param products list of productDTOs
     * @param platform test platform
     */
    public void addProductsToCart(@NonNull List<ProductDto> products,
                                  @NonNull Platform platform) {
        for (ProductDto product : products) {
            addProductToCart(product, platform);
        }
    }

    /**
     * Adds products to shopping card with all the options that are described in the ProductDTO.
     * Adds only non null options, ignores all the nulls
     *
     * @param products list of productDTOs
     */
    public void addProductsToCart(@NonNull List<ProductDto> products) {
        for (ProductDto product : products) {
            addProductToCart(product);
        }
    }

    /**
     * Checks if shop is opened, fails the test otherwise
     *
     * @param platform test platform
     */
    public void openingHoursCheck(@NonNull Platform platform) {
        $(byClassName(OPENING_TIME_DIV.getClassName(platform))).shouldNotHave(cssClass("closed"));
    }

    /**
     * Closes doc that informs user that shop is currently closed if one is presented
     */
    public void closeWeHaveJustClosedDoc() {
        SelenideElement ourShopIsCurrentlyClosedLabel =
                $(byText(OurShopIsCurrentlyClosedLabel.TITLE.getText(currentLanguage)));
        SelenideElement viewTheMenuButton =
                $(byText(OurShopIsCurrentlyClosedLabel.BUTTON.getText(currentLanguage)));

        launchDelay();
        if (ourShopIsCurrentlyClosedLabel.isDisplayed()) {
            viewTheMenuButton.click();
        }
    }

    /**
     * Closes doc that asks user to enter their address, chooses the pickup option that
     * doesn't require address
     *
     * @param platform test platform
     */
    public void closeTellUsYourAddressBlockWithPickupOption(@NonNull Platform platform) {
        SelenideElement tellUsYourAddressBlock =
                $(byClassName(TELL_US_YOUR_ADDRESS_BLOCK.getClassName(platform)));
        launchDelay();
        if (tellUsYourAddressBlock.exists()) {
            tellUsYourAddressBlock.$(byText(TellUsYourAddressBlockLabel.PICKUP.getText(currentLanguage))).click();
            tellUsYourAddressBlock.$(byText(TellUsYourAddressBlockLabel.CONFIRM.getText(currentLanguage))).click();
        }
    }

    /**
     * Adds product to shopping card with all the options that are described in the ProductDTO.
     * Adds only non null options, ignores all the nulls
     *
     * @param product  productDTO
     * @param platform test platform
     */
    public void addProductToCart(@NonNull ProductDto product,
                                 @NonNull Platform platform) {
        chooseMenuSection(product.getProductType());

        if (product instanceof Set) {
            ElementsCollection productsButtons =
                    $$(byClassName(SET_POSITION_BUTTON.getClassName(platform)));
            SelenideElement productButton = productsButtons.findBy(text(product.getPositionName()));
            if (platform == Platform.DESKTOP) {
                productButton.scrollIntoView(false);
            } else if (platform == Platform.MOBILE) {
                while (!productButton.exists()) {
                    productsButtons.last().scrollIntoView(true);
                }
                productButton.scrollIntoView(true);
            }
            productButton.click();

            closeTellUsYourAddressBlockWithPickupOption(platform);
            if (!isPickUpOptionChosen) {
                isPickUpOptionChosen = true;
                productButton.click();
            }

            chooseSet((Set) product, platform);
        } else if (product instanceof Product) {
            ElementsCollection productsButtons =
                    $$(byClassName(PRODUCT_POSITION_BUTTON.getClassName(platform)));
            SelenideElement productButton = productsButtons.findBy(text(product.getPositionName()));
            if (platform == Platform.DESKTOP) {
                productButton.scrollIntoView(false);
            } else if (platform == Platform.MOBILE) {
                while (!productButton.exists()) {
                    productsButtons.last().scrollIntoView(true);
                }
                productButton.scrollIntoView(true);
            }
            productButton.click();

            closeTellUsYourAddressBlockWithPickupOption(platform);
            if (!isPickUpOptionChosen) {
                isPickUpOptionChosen = true;
            }

            SelenideElement productOptionsBlock = $(byClassName(PRODUCT_OPTIONS_BLOCK.getClassName(platform)));
            launchDelay();
            if (productOptionsBlock.isDisplayed()) {
                SelenideElement confirmButton = productOptionsBlock
                        .$(byClassName(PRODUCT_OPTIONS_CONFIRM_BUTTON.getClassName(platform)));
                chooseProductOptions((Product) product, productOptionsBlock, platform);
                confirmButton.click();
            }
        }
    }

    /**
     * Considering that block with different product options is opened chooses all the required
     * options that are described in the ProductDTO.
     * Adds only non null options, ignores all the nulls
     *
     * @param product             productDTO
     * @param productOptionsBlock block for choosing product options
     * @param platform            test platform
     */
    public void chooseProductOptions(@NonNull Product product,
                                     @NonNull SelenideElement productOptionsBlock,
                                     @NonNull Platform platform) {
        ElementsCollection productOptions = productOptionsBlock
                .$$(byClassName(PRODUCT_OPTION_HEADER.getClassName(platform)));

        for (ProductOption singularProductOption : ProductOption.singularOptions) {
            if (product.getOption(singularProductOption) != null) {
                SelenideElement productOptionHeader =
                        productOptions.find(text(singularProductOption.getText(currentLanguage)));
                productOptionHeader.shouldBe(visible);
                if (!productOptionHeader
                        .has(cssClass(PRODUCT_OPTIONS_HEADER_ACTIVE.getClassName(platform)))) {
                    productOptionHeader.click();
                }

                ElementsCollection productOptionButtons =
                        productOptionsBlock.$$(byClassName(PRODUCT_SINGULAR_OPTION_BUTTON.getClassName(platform)));
                productOptionButtons.find(text(product.getOption(singularProductOption))).click();
            }
        }

        for (ProductOption pluralProductOption : ProductOption.pluralOptions) {
            List<String> productOptionsAsString = product.getListOfOptions(pluralProductOption);
            if (productOptionsAsString != null && !productOptionsAsString.isEmpty()) {
                SelenideElement productOptionMenu =
                        productOptions.find(text(pluralProductOption.getText(currentLanguage)));
                productOptionMenu.shouldBe(visible);
                if (!productOptionMenu.has(cssClass(PRODUCT_OPTIONS_HEADER_ACTIVE.getClassName(platform)))) {
                    productOptionMenu.click();
                }

                ElementsCollection productOptionButtons = productOptionsBlock
                        .$$(byClassName(PRODUCT_PLURAL_OPTION_BUTTON.getClassName(platform)));
                for (String productOptionAsString : productOptionsAsString) {
                    productOptionButtons.find(text(productOptionAsString)).click();
                }
            }
        }
    }

    /**
     * Considering that block with different product options is opened chooses all the required
     * options that are described in the Product object.
     * Adds only non null options, ignores all the nulls
     *
     * @param product  product DTO
     * @param platform test platform
     */
    public void chooseProductOptions(@NonNull Product product,
                                     @NonNull Platform platform) {
        chooseProductOptions(product, $(byClassName(PRODUCT_OPTIONS_BLOCK.getClassName(platform))), platform);
    }

    /**
     * Considering that set assembling block is opened chooses all the required
     * parts of the set that are described in the Set object with all of their options.
     * Parts are being added in the order they are in the list
     *
     * @param set      set DTO
     * @param platform test platform
     */
    public void chooseSet(@NonNull Set set,
                          @NonNull Platform platform) {
        SelenideElement setBlock = $(byClassName(SET_BLOCK.getClassName(platform)));
        ElementsCollection setPartVariants = setBlock.$$(byClassName(SET_PART_VARIANT_DIV.getClassName(platform)));
        SelenideElement nextButton = setBlock.$(byClassName(SET_NEXT_BUTTON.getClassName(platform)));
        for (Product setPart : set.getParts()) {
            String firstSetPartVariantName = setPartVariants.first()
                    .$(byClassName(SET_PART_VARIANT_LABEL.getClassName(platform))).getText();
            SelenideElement rightPartVariant = setPartVariants.find(text(setPart.getPositionName()));
            if (!firstSetPartVariantName.equals(setPart.getPositionName())) {
                rightPartVariant.preceding(0).scrollIntoView(true);
            }

            rightPartVariant.$(byClassName(SET_PART_VARIANT_BUTTON.getClassName(platform))).click();
            nextButton.click();

            launchDelay();
            if (setBlock.exists() && !setBlock.find(byClassName("offer-step-1")).exists()) {
                chooseProductOptions(setPart, platform);
                nextButton.click();
            }
        }
    }

    /**
     * Checks that discount is showed and calculated properly, compares expected price
     * (calculated with input productDTO list) with real price
     *
     * @param products          list of products, that are supposed to be in the shopping cart
     * @param discountAsDecimal discount presented as decimal (for example 0.1 represents 10% discount)
     * @param platform          test platform
     */
    public void checkDiscount(@NonNull List<ProductDto> products,
                              @NotNull Double discountAsDecimal,
                              @NonNull Platform platform) {
        if (platform == Platform.MOBILE) {
            SelenideElement shoppingCartBlock = $(byId("shopping-cart-panel"));
            shoppingCartBlock.click();
        }

        Integer discountAsPercents = (int) (discountAsDecimal * 100);
        SelenideElement orderTotalInfo = $(byId("order-total"));
        orderTotalInfo.scrollIntoView(false);
        orderTotalInfo
                .$(byClassName(DISCOUNT_DIV.getClassName(platform)))
                .shouldHave(text(discountAsPercents + "%"));

        Double expectedTotalPrice = 0.0;
        Double expectedDiscount = 0.0;
        for (ProductDto product : products) {
            expectedTotalPrice += product.getTotalPrice();
            if (product.isDiscounted()) {
                expectedDiscount += product.getTotalPrice();
            }
        }
        expectedDiscount = Math.floor(expectedDiscount * discountAsDecimal * 100.0) / 100.0;
        String expectedDiscountAsString = String.format("%.2f", expectedDiscount);
        orderTotalInfo
                .$(byClassName(DISCOUNT_DIV.getClassName(platform)))
                .$(byClassName(DISCOUNT_VALUE.getClassName(platform)))
                .shouldHave(exactText("-" + expectedDiscountAsString + " €"));

        double expectedTotalPriceWithDiscount =
                Math.floor((expectedTotalPrice - expectedDiscount) * 100.0) / 100.0;
        String expectedTotalPriceWithDiscountAsString = String.format("%.2f", expectedTotalPriceWithDiscount);
        orderTotalInfo
                .$(byClassName(TOTAL_PRICE_DIV.getClassName(platform)))
                .$(byClassName(TOTAL_PRICE_VALUE.getClassName(platform)))
                .shouldHave(exactText(expectedTotalPriceWithDiscountAsString + " €"));
    }

    /**
     * Launches a delay (haven't found a method like this in Selenide)
     *
     * @param millis delay length in milliseconds
     */
    protected void launchDelay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignore) {
        }
    }

    /**
     * Launches a delay with default length (haven't found a method like this in Selenide)
     */
    protected void launchDelay() {
        launchDelay(AppUITestConfig.getUniqueInstance().getDefaultDelayLength());
    }
}

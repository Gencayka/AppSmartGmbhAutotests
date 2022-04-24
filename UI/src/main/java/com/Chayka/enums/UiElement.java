package com.Chayka.enums;

import com.Chayka.commons.Platform;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

/**
 * Enum holds classes names for most of the UI elements participating in testing
 */
@Getter
@AllArgsConstructor
public enum UiElement {
    PRODUCT_POSITION_BUTTON("product-small-picture-container"),
    PRODUCT_OPTIONS_BLOCK("ant-modal"),
    PRODUCT_OPTION_HEADER("accordion-item"),
    PRODUCT_OPTIONS_HEADER_ACTIVE("accordion-item_active"),
    PRODUCT_SINGULAR_OPTION_BUTTON("button-size"),
    PRODUCT_PLURAL_OPTION_BUTTON("button-ingredient"),
    PRODUCT_OPTIONS_CONFIRM_BUTTON("positive-action"),

    SET_POSITION_BUTTON("product-wrapper"),
    SET_BLOCK("ant-modal"),
    SET_PART_VARIANT_DIV("offer-product-item"),
    SET_PART_VARIANT_LABEL("product-name"),
    SET_PART_VARIANT_BUTTON("button-accent"),
    SET_NEXT_BUTTON("button-order"),

    TELL_US_YOUR_ADDRESS_BLOCK("SimpleAddressPromptModal_SimpleAddressPromptModal__5nij8"),

    OPENING_TIME_DIV("opening-time-message"),

    LANGUAGE_SWITCHER("LanguageSwitcher", "LanguageSwitcherMobile_LanguageSwitcherMobile__fPLX0"),
    LANGUAGE_SWITCHER_BUTTON("LanguageSwitcher__item", "LanguageSwitcherMobile_item__2bTMG"),

    DISCOUNT_DIV("discount"),
    DISCOUNT_VALUE("value"),
    TOTAL_PRICE_DIV("total-price"),
    TOTAL_PRICE_VALUE("value");

    private final String windowsVerClassName;
    private final String mobileVerClassName;

    UiElement(String className){
        this.windowsVerClassName = className;
        this.mobileVerClassName = className;
    }

    public String getClassName(@NonNull Platform platform) {
        if (platform == Platform.DESKTOP) {
            return windowsVerClassName;
        } else if (platform == Platform.MOBILE) {
            return mobileVerClassName;
        }
        return windowsVerClassName;
    }
}

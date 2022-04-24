package com.Chayka.enums;

import com.Chayka.commons.Platform;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public enum UiElement {
    PRODUCT_BUTTON("product-wrapper"),

    PRODUCT_OPTIONS_BLOCK("ant-modal"),
    PRODUCT_OPTION_HEADER("accordion-item"),
    PRODUCT_OPTIONS_HEADER_ACTIVE("accordion-item_active"),
    PRODUCT_SINGULAR_OPTION_BUTTON("button-size"),
    PRODUCT_PLURAL_OPTION_BUTTON("button-ingredient"),
    PRODUCT_OPTIONS_CONFIRM_BUTTON("positive-action"),

    SET_BLOCK("ant-modal"),
    SET_PART_VARIANT_DIV("offer-product-item"),
    SET_PART_VARIANT_LABEL("product-name"),
    SET_PART_VARIANT_BUTTON("button-accent"),
    SET_NEXT_BUTTON("button-order"),

    TELL_US_YOUR_ADDRESS_BLOCK("SimpleAddressPromptModal_SimpleAddressPromptModal__5nij8"),

    OPENING_TIME_DIV("opening-time-message");


    private final String windowsVerClassName;
    private final String mobileVerClassName;

    UiElement(String className){
        this.windowsVerClassName = className;
        this.mobileVerClassName = className;
    }

    public String getClassName(@NonNull Platform platform) {
        if (platform == Platform.WINDOWS) {
            return windowsVerClassName;
        } else if (platform == Platform.MOBILE) {
            return mobileVerClassName;
        }
        return windowsVerClassName;
    }
}

package ru.Chayka.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductType {
    AMERICAN_BURGER("American Burger"),
    AMERICAN_BIG_BURGER("American Big Burger"),
    BURGER("Burger"),
    CHICKEN_BURGER("Chicken-Burger"),
    VEGETARIAN_BURGER("Vegetarian Burger"),
    FINGER_FOOD("Finger Food"),
    SPECIAL_BOX("Special Box"),
    FRIES_AND_SIDES("Fries & Sides"),
    DIPS_AND_SAUCE("Dips & Sauce"),
    HOT_DOGS("Hot Dogs"),
    WRAPS("Wraps"),
    NACHOS("Nachos"),
    PIZZA("Pizza"),
    DRINKS("Getränke");

    private final String text;
}

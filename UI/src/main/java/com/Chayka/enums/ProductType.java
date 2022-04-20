package com.Chayka.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum that holds all the product types with corresponding menu sections names
 */
@Getter
@AllArgsConstructor
public enum ProductType {
    SET("Angebote"),
    AMERICAN_BURGER("American Burger"),
    AMERICAN_BIG_BURGER("American Big Burger"),
    BURGER("Burger"),
    CHICKEN_BURGER("Chicken-Burger" ),
    VEGETARIAN_BURGER("Vegetarian Burger"),
    FINGER_FOOD("Finger Food"),
    SPECIAL_BOX("Special Box"),
    FRIES_AND_SIDES("Fries & Sides"),
    DIPS_AND_SAUCE("Dips & Sauce"),
    HOT_DOGS("Hot Dogs"),
    WRAPS("Wraps"),
    NACHOS("Nachos"),
    CORN("Maiskolben"),
    PIZZA_BREAD("Pizzabrot"),
    PIZZA("Pizza"),
    PASTA("Pasta"),
    POPCORN("Popcorn"),
    DESSERT("Dessert"),
    SALAD("Salate"),
    MUFFINS("Muffins"),
    ICE_CREAM("Eis Cream"),
    DRINKS("Getränke");

    private final String text;
}

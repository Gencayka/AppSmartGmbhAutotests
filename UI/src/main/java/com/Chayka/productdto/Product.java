package com.Chayka.productdto;

import lombok.*;
import com.Chayka.enums.ProductOption;
import com.Chayka.enums.ProductType;

import java.util.List;

/**
 * DTO that describes product with its price and all options
 */
@Value
@Builder
@Getter
public class Product implements ProductDto{
    @NonNull ProductType productType;
    @NonNull String positionName;
    @NonNull Double totalPrice;
    @Builder.Default
    boolean isDiscounted = true;
    String size;
    String toppingRequired;
    @Singular("toppingOptional")
    List<String> toppingsOptional;
    String sideDish;
    @Singular("dipRequired")
    List<String> dipsRequired;
    @Singular("dipOptional")
    List<String> dipsOptional;
    @Singular("dipExtra")
    List<String> dipsExtra;
    @Singular("sauce")
    List<String> sauces;
    @Singular("sauceExtra")
    List<String> saucesExtra;
    @Singular("ingredientExtra")
    List<String> ingredientsExtra;
    String pastaType;
    String dressingRequired;
    @Singular("dressingsExtra")
    List<String> dressingsExtra;

    public String getOption(ProductOption productOption){
        return switch (productOption) {
            case SIZE -> size;
            case TOPPING_REQUIRED -> toppingRequired;
            case SIDE_DISH -> sideDish;
            case PASTA_TYPE -> pastaType;
            case DRESSING_REQUIRED -> dressingRequired;
            default -> null;
        };
    }

    public List<String> getListOfOptions(ProductOption productOption){
        return switch (productOption){
            case TOPPINGS_OPTIONAL->toppingsOptional;
            case DIPS_REQUIRED -> dipsRequired;
            case DIPS_OPTIONAL -> dipsOptional;
            case DIPS_EXTRA -> dipsExtra;
            case SAUCES -> sauces;
            case SAUCES_EXTRA -> saucesExtra;
            case INGREDIENTS_EXTRA -> ingredientsExtra;
            case DRESSINGS_EXTRA -> dressingsExtra;
            default -> null;
        };
    }
}

package com.Chayka.productdto;

import com.Chayka.enums.ProductOption;
import com.Chayka.enums.ProductType;
import lombok.*;

import java.util.List;

/**
 * DTO that describes set with its price and all options
 */
@Value
@Builder
@Getter
public class Set implements ProductDto{
    @Builder.Default
    @NonNull ProductType productType = ProductType.SET;
    @NonNull String positionName;
    @NonNull Double totalPrice;
    @Builder.Default
    boolean isDiscounted = false;
    @Singular("setOption")
    List<String> setOptions;
    String size;
    @Singular("toppingOptional")
    List<String> toppingsOptional;
    String sideDish;
    @Singular("dipExtra")
    List<String> dipsExtra;
    @Singular("ingredientExtra")
    List<String> ingredientsExtra;

    public String getOption(ProductOption productOption){
        return switch (productOption) {
            case SIZE -> size;
            case SIDE_DISH -> sideDish;
            default -> null;
        };
    }

    public List<String> getListOfOptions(ProductOption productOption){
        return switch (productOption){
            case TOPPINGS_OPTIONAL->toppingsOptional;
            default -> null;
        };
    }
}

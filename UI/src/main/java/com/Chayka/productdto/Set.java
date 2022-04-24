package com.Chayka.productdto;

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
    ProductType productType = ProductType.SET;
    @NonNull String positionName;
    @NonNull Double totalPrice;
    @Builder.Default
    boolean isDiscounted = false;
    @Singular
    List<Product> parts;
}

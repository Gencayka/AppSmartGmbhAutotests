package com.Chayka.productdto;

import com.Chayka.enums.ProductType;

public interface ProductDto {
    ProductType getProductType();
    String getPositionName();
    Double getTotalPrice();
    boolean isDiscounted();
}

package com.Chayka.productdto;

import com.Chayka.enums.ProductOption;
import com.Chayka.enums.ProductType;

import java.util.List;

public interface ProductDto {
    ProductType getProductType();
    String getPositionName();
    Double getTotalPrice();
    boolean isDiscounted();
    String getOption(ProductOption productOption);
    List<String> getListOfOptions(ProductOption productOption);
}

package ru.Chayka.productdto;

import ru.Chayka.enums.ProductOption;
import ru.Chayka.enums.ProductType;

import java.util.List;

public interface ProductDto {
    ProductType getProductType();
    String getPositionName();
    Double getTotalPrice();
    boolean isDiscounted();
    String getOption(ProductOption productOption);
    List<String> getListOfOptions(ProductOption productOption);
}

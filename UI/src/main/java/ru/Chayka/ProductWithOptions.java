package ru.Chayka;

import lombok.*;
import ru.Chayka.enums.ProductType;

import java.util.List;

@Value
@Builder
@Getter
public class ProductWithOptions {
    @NonNull ProductType productType;
    @NonNull String productName;
    @NonNull Double totalCost;
    String size;
    String toppingRequired;
    @Singular("toppingOptional")
    List<String> toppingsOptional;
    String sideDish;
    String dipRequired;
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
}

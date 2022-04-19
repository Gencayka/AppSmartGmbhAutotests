package ru.Chayka.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public enum ProductOption {
    SIZE("Please choose size", "Bitte wählen Sie eine Größe","Proszę wybrać rozmiar","Prosím vyberte velikost"),
    TOPPING_REQUIRED("Nach Wahl","Nach Wahl","Nach Wahl","Nach Wahl"),
    TOPPINGS_OPTIONAL("Auf Wunsch", "Auf Wunsch","Auf Wunsch","Auf Wunsch"),
    SIDE_DISH("Beilage auf Wunsch", "Beilage auf Wunsch","Beilage auf Wunsch","Beilage auf Wunsch"),
    DIPS_REQUIRED("Dip nach Wahl", "Dip nach Wahl","Dip nach Wahl","Dip nach Wahl"),
    DIPS_OPTIONAL("Dip auf Wunsch","Dip auf Wunsch","Dip auf Wunsch","Dip auf Wunsch"),
    DIPS_EXTRA("Extra Dips auf Wunsch","Extra Dips auf Wunsch","Extra Dips auf Wunsch","Extra Dips auf Wunsch"),
    SAUCES("Sauce (0)", "Sauce (0)","Sauce (0)","Sauce (0)"),
    SAUCES_EXTRA("Extrasauce auf Wunsch","Extrasauce auf Wunsch","Extrasauce auf Wunsch","Extrasauce auf Wunsch"),
    INGREDIENTS_EXTRA("Extrazutaten","Extrazutaten","Extrazutaten","Extrazutaten"),
    PASTA_TYPE("Nudelsorte nach Wahl","Nudelsorte nach Wahl","Nudelsorte nach Wahl","Nudelsorte nach Wahl"),
    DRESSING_REQUIRED("Dressing nach Wahl","Dressing nach Wahl","Dressing nach Wahl","Dressing nach Wahl"),
    DRESSINGS_EXTRA("Extra Dressing auf Wunsch","Extra Dressing auf Wunsch","Extra Dressing auf Wunsch","Extra Dressing auf Wunsch");

    public static final List<ProductOption> singularOptions;
    public static final List<ProductOption> pluralOptions;

    static {
        singularOptions = new ArrayList<>();
        singularOptions.add(SIZE);
        singularOptions.add(TOPPING_REQUIRED);
        singularOptions.add(SIDE_DISH);
        singularOptions.add(PASTA_TYPE);
        singularOptions.add(DRESSING_REQUIRED);

        pluralOptions = new ArrayList<>();
        pluralOptions.add(TOPPINGS_OPTIONAL);
        pluralOptions.add(DIPS_REQUIRED);
        pluralOptions.add(DIPS_OPTIONAL);
        pluralOptions.add(DIPS_EXTRA);
        pluralOptions.add(SAUCES);
        pluralOptions.add(SAUCES_EXTRA);
        pluralOptions.add(INGREDIENTS_EXTRA);
        pluralOptions.add(DRESSINGS_EXTRA);
    }

    private final String textEN;
    private final String textDE;
    private final String textPL;
    private final String textCZ;

    public String getText(@NonNull AppLanguage language){
        switch (language){
            case DEUTSCH -> {return textDE;}
            case POLISH -> {return textPL;}
            case CZECH -> {return textCZ;}
            default -> {return textEN;}
        }
    }
}

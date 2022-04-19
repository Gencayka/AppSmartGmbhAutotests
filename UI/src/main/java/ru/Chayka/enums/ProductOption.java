package ru.Chayka.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public enum ProductOption {
    SIZE("Please choose size", "Bitte wählen Sie eine Größe","Proszę wybrać rozmiar","Prosím vyberte velikost"),
    TOPPING_REQUIRED("Nach Wahl","Nach Wahl","Nach Wahl","Nach Wahl"),
    TOPPINGS_OPTIONAL("Auf Wunsch", "Auf Wunsch","Auf Wunsch","Auf Wunsch"),
    SIDE_DISH("Beilage auf Wunsch", "Beilage auf Wunsch","Beilage auf Wunsch","Beilage auf Wunsch"),
    DIP_REQUIRED("Dip nach Wahl", "Dip nach Wahl","Dip nach Wahl","Dip nach Wahl"),
    DIPS_OPTIONAL("Dip auf Wunsch","Dip auf Wunsch","Dip auf Wunsch","Dip auf Wunsch"),
    DIPS_EXTRA("Extra Dips auf Wunsch","Extra Dips auf Wunsch","Extra Dips auf Wunsch","Extra Dips auf Wunsch"),
    SAUCES("Sauce", "Sauce","Sauce","Sauce"),
    SAUCES_EXTRA("Extrasauce auf Wunsch","Extrasauce auf Wunsch","Extrasauce auf Wunsch","Extrasauce auf Wunsch"),
    INGREDIENTS_EXTRA("Extrazutaten","Extrazutaten","Extrazutaten","Extrazutaten"),
    PASTA_TYPE("Nudelsorte nach Wah","Nudelsorte nach Wah","Nudelsorte nach Wah","Nudelsorte nach Wah"),
    DRESSING_REQUIRED("Dressing nach Wahl","Dressing nach Wahl","Dressing nach Wahl","Dressing nach Wahl"),
    DRESSINGS_EXTRA("Extra Dressing auf Wunsch","Extra Dressing auf Wunsch","Extra Dressing auf Wunsch","Extra Dressing auf Wunsch");

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

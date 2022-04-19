package ru.Chayka.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import ru.Chayka.enums.AppLanguage;

@Getter
@AllArgsConstructor
public enum TellUsYourAddressBlockLabel {
    TITLE("Please tell us your address", "Lieferadresse","",""),
    DELIVERY("Delivery", "Liefern","Dostawa","Dodávka"),
    PICKUP("Pickup", "Abholen","Odbiór", "Recepce"),
    CANCEL("Cancel", "Abbrechen","",""),
    CONFIRM("Confirm", "Bestätigen","","");

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

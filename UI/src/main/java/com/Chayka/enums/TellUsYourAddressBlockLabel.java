package com.Chayka.enums;

import lombok.AllArgsConstructor;
import lombok.NonNull;

/**
 * Enum holds text of labels on "Tell us your address" block in different languages
 */
@AllArgsConstructor
public enum TellUsYourAddressBlockLabel {
    DELIVERY("Delivery", "Liefern","Dostawa","Dodávka"),
    PICKUP("Pickup", "Abholen","Odbiór", "Recepce"),
    CANCEL("Cancel", "Abbrechen","Anuluj","Zrušení"),
    CONFIRM("Confirm", "Bestätigen","Potwierdź","Potvrdit");

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

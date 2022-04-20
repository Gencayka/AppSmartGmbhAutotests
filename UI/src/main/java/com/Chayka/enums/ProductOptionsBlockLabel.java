package com.Chayka.enums;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public enum ProductOptionsBlockLabel {
    CANCEL("Cancel", "Abbrechen","Anuluj","Zrušení"),
    NEXT_STEP("Next step", "Nächster Schritt","Następny krok", "Další krok"),
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

package ru.Chayka.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public enum OurShopIsCurrentlyClosedLabel {
    TITLE("Our shop is currently closed.", "Wir haben gerade geschlossen.", "Nasza restauracja jest obecnie zamknięta.","Naše restaurace je momentálně zavřená."),
    BUTTON("Browse the menu","Speisekarte ansehen","Przeglądaj menu","Procházejte nabídku");

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

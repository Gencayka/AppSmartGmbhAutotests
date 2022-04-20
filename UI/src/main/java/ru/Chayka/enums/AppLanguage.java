package ru.Chayka.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppLanguage {
    ENGLISH("English", "EN"),
    DEUTSCH("Deutsch", "DE"),
    POLISH("Polski", "PL"),
    CZECH("Česky", "CS");

    private final String fullName;
    private final String shortName;
    private final String desktopButtonName;
    private final String mobileButtonName;

    AppLanguage(String fullName, String shortName){
        this.fullName = fullName;
        this.shortName = shortName;
        this.desktopButtonName = fullName + " " + shortName;
        this.mobileButtonName = shortName + " - " + fullName;
    }
}

package ru.Chayka;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppLanguage {
    DEUTSCH("Deutsch", "DE"),
    ENGLISH("English", "EN"),
    POLISH("Polski", "PL"),
    CZECH("Česky", "CS");

    private final String fullName;
    private final String shortName;
    private final String normalButtonName;
    private final String mobileButtonName;

    AppLanguage(String fullName, String shortName){
        this.fullName = fullName;
        this.shortName = shortName;
        this.normalButtonName = fullName + " " + shortName;
        this.mobileButtonName = shortName + " - " + fullName;
    }
}

package ru.Chayka.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Browser {
    GOOGLE_CHROME("chrome"),
    FIREFOX("firefox"),
    OPERA("opera"),
    APPLE_SAFARI("safari"),
    MICROSOFT_EDGE("edge");

    private final String webDriverName;
}

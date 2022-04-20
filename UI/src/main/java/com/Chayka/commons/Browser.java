package com.Chayka.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum holds different browsers to run the tests on with corresponding web drivers names
 */
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

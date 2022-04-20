package com.Chayka.commons;

/**
 * Enum holds different platforms to run the tests on
 */
public enum Platform {
    WINDOWS,
    MOBILE;

    public String getName(){
        String platformAsString = this.toString();
        return platformAsString.substring(0, 1).toUpperCase() + platformAsString.substring(1).toLowerCase();
    }
}

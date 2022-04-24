package com.Chayka;

import com.codeborne.selenide.Configuration;
import lombok.Getter;
import org.openqa.selenium.Dimension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Component
@Scope("singleton")
@PropertySource("classpath:appUIConfig.properties")
public class AppUITestConfig {
    private final String baseUrl;
    private final long defaultTimeoutLength;
    private final long defaultDelayLength;
    private final Dimension desktopVerWindowSize;
    private final Dimension mobileVerWindowSize;

    @Getter
    private static AppUITestConfig uniqueInstance;

    private AppUITestConfig(@Value("${baseUrl}") String baseUrl,
                            @Value("${defaultTimeoutLength}") long defaultTimeoutLength,
                            @Value("${defaultDelayLength}") long defaultDelayLength,
                            @Value("${desktopVerWindowXSize}") int desktopVerWindowXSize,
                            @Value("${desktopVerWindowYSize}") int desktopVerWindowYSize,
                            @Value("${mobileVerWindowYSize}") int mobileVerWindowYSize,
                            @Value("${mobileVerWindowXSize}") int mobileVerWindowXSize){
        this.baseUrl = baseUrl;
        Configuration.baseUrl = baseUrl;
        this.defaultTimeoutLength = defaultTimeoutLength;
        this.defaultDelayLength = defaultDelayLength;
        this.desktopVerWindowSize = new Dimension(desktopVerWindowXSize, desktopVerWindowYSize);
        this.mobileVerWindowSize = new Dimension(mobileVerWindowXSize, mobileVerWindowYSize);
        uniqueInstance = this;
    }
}

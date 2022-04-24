package com.Chayka;

import com.codeborne.selenide.Configuration;
import lombok.Getter;
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

    @Getter
    private static AppUITestConfig uniqueInstance;

    private AppUITestConfig(@Value("${baseUrl}") String baseUrl,
                            @Value("${defaultTimeoutLength}") long defaultTimeoutLength,
                            @Value("${defaultDelayLength}") long defaultDelayLength){
        this.baseUrl = baseUrl;
        this.defaultTimeoutLength = defaultTimeoutLength;
        this.defaultDelayLength = defaultDelayLength;
        Configuration.baseUrl = baseUrl;
        uniqueInstance = this;
    }
}

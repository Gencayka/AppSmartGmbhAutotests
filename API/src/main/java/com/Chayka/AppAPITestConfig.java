package com.Chayka;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Component
@Scope("singleton")
@PropertySource("classpath:appAPIConfig.properties")
public class AppAPITestConfig {
    private final String baseUrl;

    @Getter
    private static AppAPITestConfig uniqueInstance;

    private AppAPITestConfig(@Value("${baseUrl}") String baseUrl){
        this.baseUrl = baseUrl;
        uniqueInstance = this;
    }
}

package ru.Chayka;

import com.codeborne.selenide.Configuration;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Component
@Scope("singleton")
@PropertySource("classpath:appConfig.properties")
public class AppConfig {
    private final String baseUrl;

    @Getter
    private static AppConfig uniqueInstance;

    private AppConfig(@Value("${baseUrl}") String baseUrl){
        this.baseUrl = baseUrl;
        Configuration.baseUrl = baseUrl;
        uniqueInstance = this;
    }
}

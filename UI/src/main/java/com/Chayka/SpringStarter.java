package com.Chayka;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SpringStarter {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringStarter.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}

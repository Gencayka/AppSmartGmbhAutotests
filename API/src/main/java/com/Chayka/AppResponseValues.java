package com.Chayka;

import com.Chayka.commons.ResponseValues;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppResponseValues implements ResponseValues {
    OK(200,200);

    private final Integer httpCode;
    private final Integer statusCode;
}

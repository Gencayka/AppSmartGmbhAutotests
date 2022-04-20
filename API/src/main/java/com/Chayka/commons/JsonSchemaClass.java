package com.Chayka.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Class for storing JSON-scheme for requests and responses bodies validation
 */
@Getter
@AllArgsConstructor
public class JsonSchemaClass {
    private final String name;
    private final String text;
}

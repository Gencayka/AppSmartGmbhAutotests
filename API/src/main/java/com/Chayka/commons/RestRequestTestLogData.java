package com.Chayka.commons;

import io.restassured.response.Response;
import lombok.Value;

import java.util.Map;

/**
 * DTO that holds test case data for logging
 */
@Value
public class RestRequestTestLogData {
    String testName;
    Map<String ,String> requestHeaders;
    String requestBodyAsString;
    Response restAssuredResponse;
}

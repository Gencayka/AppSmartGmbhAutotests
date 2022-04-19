package ru.Chayka.commons;

import io.restassured.response.Response;
import lombok.Value;

import java.util.Map;

/**
 * DTO для хранения данных о тест-кейсе для логирования
 */
@Value
public class RestRequestTestLogData {
    String testName;
    Map<String ,String> requestHeaders;
    String requestBodyAsString;
    Response restAssuredResponse;
}

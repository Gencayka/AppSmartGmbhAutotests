package com.Chayka.commons;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.NonNull;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Class for testing services by sending REST-requests
 * <br>Abstract class, each service has its own child class
 * <br>In the beginning of each test clearSoftAssert() method must be called
 */
public abstract class RestRequestTester {
    protected final Logger logger;

    protected final RequestSpecification baseRequestSpecification;
    protected final Map<String, String> defaultHeaders;
    protected final ObjectMapper mapper;
    protected SoftAssert softAssert;

    protected RestRequestTester(@NonNull Logger logger) {
        this.logger = logger;

        baseRequestSpecification = RestAssured.given();
        defaultHeaders = new HashMap<>();
        mapper = new ObjectMapper();
        softAssert = new SoftAssert();
    }

    /**
     * Creates new SoftAssert object, needs to be called in the beginning of each test
     */
    protected final void clearSoftAssert() {
        softAssert = new SoftAssert();
    }

    /**
     * Sends GET request to service and returns response
     *
     * @return response as Response object
     */
    protected Response sendGetRequest(Map<String, String> requestHeaders,
                                      RequestSpecification requestSpecification) {
        RequestSpecification requestSpecificationWithHeaders = RestAssured.given().spec(requestSpecification);
        for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
            if (header.getValue() != null) {
                requestSpecificationWithHeaders.header(header.getKey(), header.getValue());
            }
        }

        return requestSpecificationWithHeaders
                .and()
                .when()
                .get()
                .then()
                .extract().response();
    }

    /**
     * Sends GET request to service and returns response
     *
     * @return response as Response object
     */
    protected Response sendGetRequest() {
        return sendGetRequest(defaultHeaders, baseRequestSpecification);
    }

    /**
     * Deserializes response body from String to Java object
     *
     * @param responseBodyClass class to deserialize response body, implements ResponseBody
     * @param testLogData       test case data for logging
     * @return deserialized response body
     */
    protected <T extends ResponseBody> T deserializeResponseBody(Class<T> responseBodyClass, RestRequestTestLogData testLogData) {
        try {
            return mapper.readValue(testLogData.getRestAssuredResponse().asString(), responseBodyClass);
        } catch (Exception exception) {
            softAssert.fail(String.format("Failed to deserialize response body\n%s\n%s", exception.getMessage(), exception));
            finalAssertAll(testLogData);
            return null;
        }
    }

    /**
     * Checks HTTP-code of response to the test request. If it fails the whole test fails
     *
     * @param realResponseHttpCode real HTTP-code of response
     * @param responseValues       expected response values
     * @param testLogData          test case data for logging
     */
    protected void checkResponseHttpCode(Integer realResponseHttpCode, ResponseValues responseValues, RestRequestTestLogData testLogData) {
        softAssert.assertEquals(realResponseHttpCode, responseValues.getHttpCode(),
                "Response Http code check failed:");
        assertAll(testLogData);

        logger.debug("Response Http code check succeed");
    }

    /**
     * Checks status-code of response to the test request. If it fails the whole test fails
     *
     * @param responseBody   response body as java object
     * @param responseValues expected response values
     * @param testLogData    test case data for logging
     */
    protected void checkResponseStatusCode(ResponseBody responseBody, ResponseValues responseValues, RestRequestTestLogData testLogData) {
        softAssert.assertEquals(responseBody.getStatusCode(), responseValues.getStatusCode(),
                "Response status code check failed:");
        assertAll(testLogData);

        logger.debug("Response status code check succeed");
    }

    /**
     * Checks response/request body correspondence to the JSON-schema
     * If it fails the validation error is written in the softAssert
     *
     * @param jsonBodyAsString JSON-body of request/response
     * @param jsonSchema       JSON-schema
     * @param expectedValid    should the body pass the validation?
     */
    protected void validateJsonBody(String jsonBodyAsString, JsonSchemaClass jsonSchema, boolean expectedValid) {
        logger.debug(String.format("Starting %s body validation", jsonSchema.getName().toLowerCase(Locale.ROOT)));
        try {
            Schema schema = SchemaLoader.load(new JSONObject(jsonSchema.getText()));
            schema.validate(new JSONObject(jsonBodyAsString));
        } catch (JSONException ex) {
            softAssert.fail(String.format("Failed to validate %s body\n%s", jsonSchema.getName().toLowerCase(Locale.ROOT),
                    ex.getMessage()));
            return;
        } catch (ValidationException validationException) {
            if (expectedValid) {
                softAssert.fail(String.format("%s body validation failed\n%s", jsonSchema.getName().toLowerCase(Locale.ROOT),
                        validationException.getMessage()));
            } else {
                logger.info(String.format("Validation of %s failed successfully\n%s", jsonSchema.getName().toLowerCase(Locale.ROOT),
                        validationException.getMessage()));
            }
            logger.debug(String.format("Validation of %s completed", jsonSchema.getName().toLowerCase(Locale.ROOT)));
            return;
        }
        if (!expectedValid) {
            softAssert.fail(String.format("%s body validation succeed for some reason", jsonSchema.getName().toLowerCase(Locale.ROOT)));
        }
    }

    /**
     * Checks response/request body correspondence to the JSON-schema
     * If it fails the validation error is written in the softAssert
     *
     * @param jsonBodyAsString JSON-body of request/response
     * @param jsonSchema       JSON-schema
     */
    protected void validateJsonBody(String jsonBodyAsString, JsonSchemaClass jsonSchema) {
        validateJsonBody(jsonBodyAsString, jsonSchema, true);
    }

    /**
     * Calls intermediate test errors assertion. If it fails the whole test fails
     * and list of found errors and final log (request and response parameters) are written
     * with logger
     */
    protected void assertAll(String testName,
                             Map<String, String> requestHeaders,
                             String requestBodyAsString,
                             Response restAssuredResponse) {
        try {
            softAssert.assertAll();
        } catch (AssertionError assertionError) {
            logger.error(formFinalLog(testName, requestHeaders, requestBodyAsString, restAssuredResponse, false));
            logger.error("CRITICAL DISCREPANCY DETECTED");
            logger.error(assertionError.getMessage());
            throw assertionError;
        }
    }

    /**
     * Calls intermediate test errors assertion. If it fails the whole test fails
     * and list of found errors and final log (request and response parameters) are written
     * with logger
     *
     * @param testLogData test case data for logging
     */
    protected void assertAll(RestRequestTestLogData testLogData) {
        assertAll(testLogData.getTestName(), testLogData.getRequestHeaders(), testLogData.getRequestBodyAsString(), testLogData.getRestAssuredResponse());
    }

    /**
     * Calls final test errors assertion. Must be called in the end of test
     * instead of softAssert.assertAll() method. In result list of found errors and final log
     * (request and response parameters) are written with logger
     */
    protected void finalAssertAll(String testName,
                                  Map<String, String> requestHeaders,
                                  String requestBodyAsString,
                                  Response restAssuredResponse) {
        try {
            softAssert.assertAll();
        } catch (AssertionError assertionError) {
            logger.error(formFinalLog(testName, requestHeaders, requestBodyAsString, restAssuredResponse, false));
            logger.error(assertionError.getMessage());
            throw assertionError;
        }

        logger.info(formFinalLog(testName, requestHeaders, requestBodyAsString, restAssuredResponse, true));
    }

    /**
     * Calls final test errors assertion. Must be called in the end of test
     * instead of softAssert.assertAll() method. In result list of found errors and final log
     * (request and response parameters) are written with logger
     *
     * @param testLogData test case data for logging
     */
    protected void finalAssertAll(RestRequestTestLogData testLogData) {
        finalAssertAll(testLogData.getTestName(), testLogData.getRequestHeaders(), testLogData.getRequestBodyAsString(), testLogData.getRestAssuredResponse());
    }

    /**
     * Forms final log about test case results
     * <br>Log contains test case name, test case successfulness, request and response headers
     * and bodies
     */
    protected String formFinalLog(String testName,
                                  Map<String, String> requestHeaders,
                                  String requestBodyAsString,
                                  Response restAssuredResponse,
                                  boolean isSuccessful) {
        StringBuilder finalLog = new StringBuilder();
        if (isSuccessful) {
            finalLog.append(String.format("\nTest %s succeeded\n\n", testName));
        } else {
            finalLog.append(String.format("\nTest %s failed\n\n", testName));
        }

        finalLog.append("Request headers: \n");
        for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
            finalLog.append(String.format("%s: %s\n", header.getKey(), header.getValue()));
        }


        if (requestBodyAsString != null) {
            finalLog.append(String.format("Request body:\n%s\n\n", prettyPrintJson(requestBodyAsString)));
        }

        finalLog.append("Response headers\n");
        Headers responseHeaders = restAssuredResponse.headers();
        for (Header header : responseHeaders.asList()) {
            finalLog.append(String.format("%s: %s\n", header.getName(), header.getValue()));
        }

        finalLog.append(String.format("Response body\n%s\n\n", prettyPrintJson(restAssuredResponse.asString())));
        return finalLog.toString();
    }

    /**
     * Formats JSON, makes it pretty
     */
    private String prettyPrintJson(String uglyJson) {
        ObjectMapper localMapper = new ObjectMapper();

        try {
            Object jsonBuffer = localMapper.readValue(uglyJson, Object.class);
            return localMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonBuffer);
        } catch (IOException ignore) {
            return uglyJson;
        }
    }
}

package ru.Chayka.commons;

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
 * Класс предназначен для тестирования сервисов посредством отправки на сервис REST-запросов
 * <br>Абстрактный класс, для каждого сервиса используется отдельный класс-наследник
 * <br>В начале теста должен вызваться метод clearSoftAssert
 */
public abstract class RestRequestTester {
    protected final Logger logger;

    protected final RequestSpecification baseRequestSpecification;
    protected final Map<String, String> defaultHeaders;
    protected final ObjectMapper mapper;
    protected SoftAssert softAssert;

    protected RestRequestTester(@NonNull Logger logger){
        this.logger = logger;

        baseRequestSpecification = RestAssured.given();
        defaultHeaders = new HashMap<>();
        mapper = new ObjectMapper();
        softAssert = new SoftAssert();
    }

    /**
     * Метод создает новый объект SoftAssert, необходимо вызывать в начале теста
     */
    protected final void clearSoftAssert(){
        softAssert = new SoftAssert();
    }

    /**
     * Метод используется для десериализации тела ответа из строки в Java-объект
     * @param responseBodyClass класс, в который десериализуется тело ответа, должен реализовывать интерфейс ResponseBody
     * @param testLogData данные о тест-кейсе для логирования
     * @return десериализованное тело ответа
     */
    protected <T extends ResponseBody> T deserializeResponseBody(Class<T> responseBodyClass, RestRequestTestLogData testLogData){
        try {
            return mapper.readValue(testLogData.getRestAssuredResponse().asString(), responseBodyClass);
        } catch (Exception exception) {
            softAssert.fail(String.format("Failed to deserialize response body\n%s\n%s", exception.getMessage(), exception));
            finalAssertAll(testLogData);
            return null;
        }
    }

    /**
     * Метод проверяет Http-код ответа на тестовый запрос. При неудачной проверке падает весь тест-кейс
     * @param realResponseHttpCode реальный Http-код ответа на тестовый запрос
     * @param responseValues ожидаемые значения ответа на тестовый запрос
     * @param testLogData данные о тест-кейсе для логирования
     */
    protected void checkResponseHttpCode(Integer realResponseHttpCode, ResponseValues responseValues, RestRequestTestLogData testLogData){
        softAssert.assertEquals(realResponseHttpCode, responseValues.getHttpCode(),
                "Response Http code check failed:");
        assertAll(testLogData);

        logger.debug("Response Http code check succeed");
    }

    /**
     * Метод проверяет код статуса ответа на тестовый запрос. При неудачной проверке падает весь тест-кейс
     * @param responseBody тело ответа на тестовый запрос
     * @param responseValues ожидаемые значения ответа на тестовый запрос
     * @param testLogData данные о тест-кейсе для логирования
     */
    protected void checkResponseStatusCode(ResponseBody responseBody, ResponseValues responseValues, RestRequestTestLogData testLogData){
        softAssert.assertEquals(responseBody.getStatusCode(), responseValues.getStatusCode(),
                "Response status code check failed:");
        assertAll(testLogData);

        logger.debug("Response status code check succeed");
    }

    /**
     * Метод проверяет соответствие JSON-тела запроса/ответа схеме валидации
     * <br>Если результат валидации не соответствует ожидаемому, в SoftAssert записывается соответствующая ошибка
     * @param jsonBodyAsString JSON-тело запроса/ответа
     * @param jsonSchema схема валидации JSON-тела запроса/ответа
     * @param expectedValid должно ли тело успешно пройти валидацию
     */
    protected void validateJsonBody(String jsonBodyAsString, JsonSchemaClass jsonSchema, boolean expectedValid){
        logger.debug(String.format("Starting %s body validation", jsonSchema.getName().toLowerCase(Locale.ROOT)));
        try {
            Schema schema = SchemaLoader.load(new JSONObject(jsonSchema.getText()));
            schema.validate(new JSONObject(jsonBodyAsString));
        } catch (JSONException ex){
            softAssert.fail(String.format("Failed to validate %s body\n%s", jsonSchema.getName().toLowerCase(Locale.ROOT),
                    ex.getMessage()));
            return;
        } catch (ValidationException validationException){
            if(expectedValid){
                softAssert.fail(String.format("%s body validation failed\n%s",jsonSchema.getName().toLowerCase(Locale.ROOT),
                        validationException.getMessage()));
            } else {
                logger.info(String.format("Validation of %s failed successfully\n%s", jsonSchema.getName().toLowerCase(Locale.ROOT),
                        validationException.getMessage()));
            }
            logger.debug(String.format("Validation of %s completed", jsonSchema.getName().toLowerCase(Locale.ROOT)));
            return;
        }
        if(!expectedValid){
            softAssert.fail(String.format("%s body validation succeed for some reason",jsonSchema.getName().toLowerCase(Locale.ROOT)));
        }
    }

    /**
     * Метод проверяет соответствие JSON-тела запроса/ответа схеме валидации
     * <br>Если JSON-тело не прошло валидацию, в SoftAssert записывается соответствующая ошибка
     * @param jsonBodyAsString JSON-тело запроса/ответа
     * @param jsonSchema схема валидации JSON-тела запроса/ответа
     */
    protected void validateJsonBody(String jsonBodyAsString, JsonSchemaClass jsonSchema){
        validateJsonBody(jsonBodyAsString, jsonSchema, true);
    }

    /**
     * Метод вызывает промежуточную проверку наличия ошибок в SoftAssert.
     * В случае обнаружения ошибок тест-кейс завершается, а в логгер записываются список ошибок
     * и финальный лог (параметры запроса и ответа)
     * @param testName название тест-кейса
     * @param requestHeaders заголовки запроса
     * @param requestBodyAsString JSON-тело запроса
     * @param restAssuredResponse ответ типа Response
     */
    protected void assertAll(String testName,
                           Map<String ,String> requestHeaders,
                           String requestBodyAsString,
                           Response restAssuredResponse){
        try {
            softAssert.assertAll();
        } catch (AssertionError assertionError){
            logger.error(formFinalLog(testName, requestHeaders, requestBodyAsString, restAssuredResponse, false));
            logger.error("CRITICAL DISCREPANCY DETECTED");
            logger.error(assertionError.getMessage());
            throw assertionError;
        }
    }

    /**
     * Метод вызывает промежуточную проверку наличия ошибок в SoftAssert.
     * В случае обнаружения ошибок тест-кейс завершается, а в логгер записываются список ошибок
     * и финальный лог (параметры запроса и ответа)
     * @param testLogData данные о тест-кейсе для логирования
     */
    protected void assertAll(RestRequestTestLogData testLogData){
        assertAll(testLogData.getTestName(), testLogData.getRequestHeaders(), testLogData.getRequestBodyAsString(), testLogData.getRestAssuredResponse());
    }

    /**
     * Метод вызывает финальную проверку наличия ошибок в SoftAssert.
     * Метод следует вызывать в конце тестового метода ВМЕСТО softAssert.assertAll()
     * В результате в логгер записываются список ошибок и финальный лог (параметры запроса и ответа)
     * @param testName название тест-кейса
     * @param requestHeaders заголовки запроса
     * @param requestBodyAsString JSON-тело запроса
     * @param restAssuredResponse ответ типа Response
     */
    protected void finalAssertAll(String testName,
                                Map<String ,String> requestHeaders,
                                String requestBodyAsString,
                                Response restAssuredResponse){
        try {
            softAssert.assertAll();
        } catch (AssertionError assertionError){
            logger.error(formFinalLog(testName, requestHeaders, requestBodyAsString, restAssuredResponse, false));
            logger.error(assertionError.getMessage());
            throw assertionError;
        }

        logger.info(formFinalLog(testName, requestHeaders, requestBodyAsString, restAssuredResponse, true));
    }

    /**
     * Метод вызывает финальную проверку наличия ошибок в SoftAssert.
     * Метод следует вызывать в конце тестового метода ВМЕСТО softAssert.assertAll()
     * В результате в логгер записываются список ошибок и финальный лог (параметры запроса и ответа)
     * @param testLogData данные о тест-кейсе для логирования
     */
    protected void finalAssertAll(RestRequestTestLogData testLogData){
        finalAssertAll(testLogData.getTestName(), testLogData.getRequestHeaders(), testLogData.getRequestBodyAsString(), testLogData.getRestAssuredResponse());
    }

    /**
     * Метод формирует лог результатов тест-кейса
     * <br>Лог содержит название тест-кейса, успешность тест-кейса, заголовки и тело запроса,
     * заголовки и тело ответа
     * @param testName название тест-кейса
     * @param requestHeaders заголовки запроса
     * @param requestBodyAsString JSON-тело запроса
     * @param restAssuredResponse ответ типа Response
     * @param isSuccessful пройден ли кейс успешно
     * @return лог результатов тест-кейса
     */
    protected String formFinalLog(String testName,
                                Map<String ,String> requestHeaders,
                                String requestBodyAsString,
                                Response restAssuredResponse,
                                boolean isSuccessful){
        StringBuilder finalLog = new StringBuilder();
        if(isSuccessful){
            finalLog.append(String.format("\nTest %s succeeded\n\n", testName));
        } else {
            finalLog.append(String.format("\nTest %s failed\n\n", testName));
        }

        finalLog.append("Request headers: \n");
        for (Map.Entry<String, String> header : requestHeaders.entrySet()){
            finalLog.append(String.format("%s: %s\n", header.getKey(), header.getValue()));
        }
        finalLog.append(String.format("Request body:\n%s\n\n", prettyPrintJson(requestBodyAsString)));

        finalLog.append("Response headers\n");
        Headers responseHeaders = restAssuredResponse.headers();
        for(Header header:responseHeaders.asList()){
            finalLog.append(String.format("%s: %s\n", header.getName(), header.getValue()));
        }

        finalLog.append(String.format("Response body\n%s\n\n", prettyPrintJson(restAssuredResponse.asString())));
        return finalLog.toString();
    }

    /**
     * Метод форматирует JSON, делая его читабельным
     * @param uglyJson неотформатированный JSON
     * @return отформатированный JSON
     */
    private String prettyPrintJson(String uglyJson){
        ObjectMapper localMapper = new ObjectMapper();

        try {
            Object jsonBuffer = localMapper.readValue(uglyJson, Object.class);
            return localMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonBuffer);
        } catch (IOException ignore){
            return uglyJson;
        }
    }
}

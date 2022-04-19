package ru.Chayka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.Chayka.commons.RestRequestTestLogData;
import ru.Chayka.responsebody.AppResponseBody;

import java.util.HashMap;

@SpringBootTest(classes = SpringStarter.class)
public class TestClass extends AbstractTestNGSpringContextTests {
    @Test
    public void debugTest() throws JsonProcessingException {
        Response response = RestAssured.given()
                .baseUri("https://dev.delivery-app.app-smart.services/api3/1/products/159")
                .and()
                .when()
                .get()
                .then()
                .extract().response();

        String str = response.asPrettyString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        AppResponseBody responseBody = mapper.readValue(response.asString(), AppResponseBody.class);
        Assert.assertEquals(response.statusCode(), 200);
    }
}

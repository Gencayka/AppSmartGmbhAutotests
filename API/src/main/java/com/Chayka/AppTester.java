package com.Chayka;

import com.Chayka.commons.RestRequestTestLogData;
import com.Chayka.commons.RestRequestTester;
import com.Chayka.responsebody.AppResponseBody;
import com.Chayka.responsebody.keys.ContentAppKey;
import com.fasterxml.jackson.databind.DeserializationFeature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Class for testing the App application
 */
@Component
public class AppTester extends RestRequestTester {
    public AppTester(){
        super(LoggerFactory.getLogger(AppTester.class.getSimpleName()));

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        baseRequestSpecification
                .baseUri(AppAPITestConfig.getUniqueInstance().getBaseUrl())
                .urlEncodingEnabled(false)
                .contentType(ContentType.JSON);
    }

    public void baseTest(String testName,
                         AppResponseValues responseValues) {
        clearSoftAssert();
        logger.debug("Starting test " + testName);

        List<Integer> dbProductIds = Arrays.asList(25448, 25453, 25456, 25464, 25470, 25474, 25478, 25485, 25489, 25498, 25504, 25508, 25512, 25521, 25523, 25528, 25535, 25546, 25548, 25549, 25550, 25552, 25554, 25555, 25557, 25559, 25561, 25562, 25563, 25578, 25580, 25583, 25587, 25590, 25593, 25596, 25601, 25603, 25605, 25606, 25607, 25608, 25609, 25610, 25613, 25619, 25622, 25629, 25645, 25648, 25650, 25652, 25654, 25679, 25680, 25681, 25682, 25684, 25685, 25688, 25689, 25690, 25693, 25696, 25703, 25709, 25713, 25782, 25798, 25806, 25816, 25821, 25827, 25835, 25837, 25842, 25844, 25847, 25877, 25880, 25883, 25885, 25888, 25889, 25890, 25891, 25892, 25895, 25903, 25908, 25924, 25927, 25929, 25932, 25935, 25937, 25939, 25942, 25944, 25945, 25947, 25951, 25956, 25962, 25971, 25975, 25979, 25983, 25989, 25992, 25996, 26001, 26010, 26068, 26071, 26078, 26082, 26083, 26084, 26089, 26093, 26106, 26107, 26109, 26110, 26117, 26133, 26134, 26136, 26138, 26157, 26215, 26224, 26226, 26227, 26228, 26230, 26232, 26234, 26235, 26237, 26238, 26239, 26240, 26241, 26244, 26245, 26247, 26248, 26249, 26290, 26293, 26294, 26295, 27118, 30726, 30757, 30836, 30885, 30887, 30891, 30985, 31067, 31210, 31211, 31214, 31244, 31246, 31409, 31412, 33144, 34921, 34922, 34923, 34924, 34925, 34926, 34927, 34928, 34929, 57269, 57271, 57272, 57275, 57277, 57278, 57279, 57280, 57282, 57283, 57284, 57285, 57286, 57287, 66648, 66649, 93075, 93116, 93123, 93124, 93126, 93128, 93130, 93131, 93143, 93145, 93156, 93163, 93166, 93172, 93173, 93174, 93175, 93180, 93181, 93182, 93183, 93207, 93209, 93211, 163626, 163660);

        Response restAssuredResponse = sendGetRequest();
        RestRequestTestLogData testLogData =
                new RestRequestTestLogData(testName, defaultHeaders, null, restAssuredResponse);

        checkResponseHttpCode(restAssuredResponse.statusCode(), responseValues, testLogData);

        AppResponseBody responseBody = deserializeResponseBody(AppResponseBody.class, testLogData);
        checkResponseStatusCode(responseBody, responseValues, testLogData);

        if(responseValues == AppResponseValues.OK){
            checkIfReturnedAllProducts(responseBody, dbProductIds);
        }

        finalAssertAll(testLogData);
    }

    /**
     * Checks that service returns all the products DB contains
     * @param responseBody response body as java object
     * @param dbProductIds list of DB products Ids
     */
    private void checkIfReturnedAllProducts(AppResponseBody responseBody, List<Integer> dbProductIds){
        List<Integer> localDbProductIds = new ArrayList<>(dbProductIds);
        List<ContentAppKey> localResponseProducts = new ArrayList<>(responseBody.getContent());

        ListIterator<Integer> dbListIterator = localDbProductIds.listIterator();
        ListIterator<ContentAppKey> responseListIterator = localResponseProducts.listIterator();

        while (dbListIterator.hasNext()) {
            Integer dbListEntryId = dbListIterator.next();
            while (responseListIterator.hasNext()) {
                if (responseListIterator.next().getId().equals(dbListEntryId)) {
                    dbListIterator.remove();
                    responseListIterator.remove();
                    break;
                }
            }
            responseListIterator = localResponseProducts.listIterator();
        }

        if (!localDbProductIds.isEmpty()) {
            for (Integer dbListEntryId : localDbProductIds) {
                softAssert.fail(String.format("%s product found in Response",
                        dbListEntryId));
            }
        }

        if (!localResponseProducts.isEmpty()) {
            for (ContentAppKey responseListEntry : localResponseProducts) {
                softAssert.fail(String.format("Unexpected %s product found in Response (%s)",
                        responseListEntry.getId(), responseListEntry.getName()));
            }
        }
    }
}

package com.Chayka.commons;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for generating and holding test data for different test cases
 * <br>Abstract class, each service/application has its own child class
 */
public abstract class TestDataHolder {
    /**
     * Converts test data for pack of test cases into two-dimensional array of Object that can be
     * accepted by TestNG test with @DataProvider
     * @param testDataList test data for pack of test cases in list of Object form
     * @return test data for pack of test cases in two-dimensional array of Object
     */
    protected Object[][] testDataToObjectsArray(List<Object> testDataList){
        List<Object[]> testDataObjects = new ArrayList<>();
        Field[] fields = testDataList.get(0).getClass().getDeclaredFields();
        try {
            for(Object testData:testDataList){
                List<Object> testDataObject = new ArrayList<>();
                for(int i = 0; i < fields.length; i++){
                    testDataObject.add(fields[i].get(testData));
                }
                testDataObjects.add(testDataObject.toArray(new Object[0]));
            }
            return testDataObjects.toArray(new Object[0][]);
        } catch (IllegalAccessException illegalAccessException){
            //TODO
            throw new RuntimeException(illegalAccessException.getMessage());
        }
    }
}

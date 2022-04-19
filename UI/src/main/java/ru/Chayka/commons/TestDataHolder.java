package ru.Chayka.commons;

import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс предназначен для формирования и хранения тестовых данных для тестирования данного сервиса
 * <br>Абстрактный класс, для каждого сервиса используется отдельный класс-наследник
 */
public abstract class TestDataHolder {
    /**
     * Метод преобразует тестовые данные для группы тест-кейсов в двумерный массив объектов,
     * который может принимать тест TestNG
     * @param testDataList тестовые данные для группы тест-кейсов в виде списка объектов типа Odject
     * @return тестовые данные для группы тест-кейсов в виде двумерного массива объектов
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

                System.out.println("");
            }
            return testDataObjects.toArray(new Object[0][]);
        } catch (IllegalAccessException illegalAccessException){
            //TODO
            throw new RuntimeException(illegalAccessException.getMessage());
        }
    }
}

package com.Chayka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@SpringBootTest
public class TestClass extends AbstractTestNGSpringContextTests {
    @Autowired
    private AppTester tester;

    @Test
    public void baseTest(){
        tester.baseTest("CheckIfReturnedAllProducts", AppResponseValues.OK);
    }
}

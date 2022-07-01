# AppSmartGmbhAutotests
Test assignment to apply for a QA Automation Engineer job. Assignment included writing UI autotests, one API autotests as well as test cases for them 
("API Test Case.docx" and "UI Test Case.docx") and reports for found bugs ("Bug Reports.docx") 
<br>Project is written to test some features of the following website: https://hermes-dev.devteam.win/american-burger-pizza-house-augsburg/159 

# API tests
To launch tests, run the TestClass class

Tech Stack:
Name | Purpose
--- | --- 
Spring Boot | Dependecy Injections + .yml config files parsing + Downloading JSON-schemas from .json files
TestNG | Making test checks
REST Assured | Requests sending
Lombok | Reducing amount of junk code
Logback | Logging

Short Description:
<br>API autotest is a little mess since I don’t have any specification nor the connection to DB and there’s only one test case.
<br>I use pretty simplified structure I was using on my first job. All the assertions are described in the RestRequestTester abstract class and its child class AppTester. Test Data was supposed to be stored in the TestDataHolder kind of class but I decided not to use it here to simplify the code (it’s actively used in UI autotests though).
<br>AppTester sends request to the application server and deserialize response body to java object. Usually before deserializtion goes responce validation with JSON-schema but I don’t have one :( so no validation, but I left the validation methods in the code.
<br>Currently test method checks response HTTP and status codes and also check that response contains all the products from DB (data from DB is replaced with manually created list of expected codes, with DB connection there would be compering with DB data with Hibernate and JpaRepository to work with it).
<br>Some more info in the javadocs.

# UI tests
To launch tests in chosen browser, run the corresponded test class (for example, to run tests in Google Chrome, run UIChromeTests)

Tech Stack:
Name | Purpose
--- | --- 
Spring Boot | Dependecy Injections + .yml config files parsing
Selenide | User's actions emulation + Making test checks
JUnit | Making test checks (where Selenide can't)
Lombok | Reducing amount of junk code
Logback | Logging

Short Description:
<br><br>Code is written as assumed part of the bigger project with other UI tests, that is why it’s structure is a bit overcomplicated for a single scenario tests code. 
<br><br>Classes in “commons” folder are supposed to be pretty universal and probably be used for other application testing.
<br><br>Tests are launched with UI*nameofbrowser*Tests classes. Each browser have their own test class since this is the easiest way I’ve found to run different tests on different browsers with closing web driver after each browser tests bunch rather then after every test case.
<br>All the actions with Selenide are described in the Page Object classes. Desktop and Mobile versions each have their own Page Object classes that are implement MainPage interface so the test classes can run both the Desktop and Mobile tests with one method.
<br>Test Data (currently only test name, environment and the list of products that must be put in the shopping cart) are all described in the AppTestDataHolder class. Test Cases are described as objects of AppDiscountCalculationTestData and are collected in lists. Each list has their own generator method where all the Test Data are described. To run tests with different environments and conditions (Desktop/Mobile, browser, language) the genDisCalcTestDataWithDifEnvironments method generates additional test cases. @DataProvider is used to run multiple test cases with only one test method.
<br>Product that supposed to be added in the shopping cart are described as DTOs. Product class holds the name and type of position, expected price (better be requested from DB, but since I don’t have access to it the price field is filled manually), discount presence and all the possible options. Since there’s so many fields DTOs is created with lombok builder. Product class contains universal getters to significally reduce the amount of code in the chooseProductOptions method.
<br>Many UI elements are searched by text to simulate users experience more accurately. Labels text and lots of other info is described with enums.
<br>AppTestConfig is a singleton that holds some of the universal test data and reads this data from properties file with Spring feature.
<br>Some more info in the javadocs.

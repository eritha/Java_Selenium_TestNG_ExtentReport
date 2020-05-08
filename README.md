# Automation Page Object Model Framework - Using Java, Selenium, Maven, TestNG, Extent Report and Monte for screen recorder
## Overview 

```
This project is to provide a good starting structure point for those looking to use Java, Selenium and some other extensions. 
It is also intended to demonstrate how to implement design patterns in a test framework (Page Object Model), Where many test frameworks 
will give some solution demonstrates like scalable, maintainable, readable and repeatable.
```

**Resources**
- [Selenium](http://www.seleniumhq.org/)
- [TestNg](https://testng.org) 
- [Extent Report](https://extentreports.com/) 

## Examples Demo with:

/*
 * SCENARIO: TC01_create_new_user_guru99_successfully
 * Precondition: An email valid
 * #1: Goes to guru99 Signup page
 * #2: Verify UI of Signup page
 * #3: Input valid email & submit
 * #4: Verify user create with username & password
 * #5: Get user info for next suite inneed
 */
 
/*
 * SCENARIO: TC02_login_user_guru99_successfully
 * Precondition: Acc guru99 created from TestSignUp
 * #1: Goes to Login page
 * #2: Verify UI of Login page
 * #3: Input valid Account & submit
 * #4: Verify user logged successfully with Home Page display
 */
 
 /*
 * SCENARIO: TC03_create_new_customer_successfully
 * Precondition: Acc guru99 created from TestSignUp
 * #1: Login guru99/v4 by guru99 Account
 * #2: Goes to Create Customer page
 * #3: Verify UI of Create Customer page
 * #4: Input valid data & submit
 * #5: Verify Customer created successfully
 * #6: Get Customer ID, Name & Email for next suite run
 */

/*
 * SCENARIO: TC04_create_new_account_successfully
 * Precondition: Acc guru99 created from TestSignUp & Customer cusId from TestCustomer suite "cusId"
 * #1: Login guru99/v4 by guru99 Account
 * #2: Goes to Create Account page
 * #3: Verify UI of Create Account page
 * #3: Input valid data & submit
 * #4: Verify Account created successfully
 * #5: Get Account ID for next suite
 */
 
 /*
 * SCENARIO: TC05_add_new_deposit_successfully
 * Precondition: Acc guru99 created from TestSignUp & Account ID from TestAccount suite "cusId"
 * #1: Login guru99/v4 by guru99 Account
 * #2: Goes to Deposit page
 * #3: Verify UI of Deposit page
 * #3: Input valid data & submit
 * #4: Verify Deposit is added successfully
 */

## Pre-requisites

#### Condition: Extract "WebContent.rar" to drive C"

#### Tools & Libs:

* Java 6 or Above
* Right-click "Pom.xml" > Maven > Reimport

### Intellij

Import Maven Project

## Running Tests

Edit testng.xml to run suite, parallel or st else

Build
```
$ mvn clean compile
```

### Command Line

#### Run the test from file "RunProject.bat"

#### Run all tests at .sln directory
```
$ mvn install or mvn test
```

## Result

Report Overview
![GitHub Logo](/test-reports/Overview.PNG)
Format: ![Alt Text](url)
https://erit-ha.tinytake.com/tt/NDI3ODgwMV8xMzQyNTAwNQ

Report Result
![GitHub Logo](/test-reports/Result.PNG)
Format: ![Alt Text](url)
https://erit-ha.tinytake.com/tt/NDI3ODgwNl8xMzQyNTA0MA

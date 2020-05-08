//
// Copyright 2019 (C) by Phuoc.Ha
//
// Created on : 10-03-2019
// Author     : phuoc.ha
//
//-----------------------------------------------------------------------------
// Revision History (Release 1.0.0.0)
//-----------------------------------------------------------------------------
// VERSION     AUTHOR/      DESCRIPTION OF CHANGE
// OLD/NEW     DATE                RFC NO
//-----------------------------------------------------------------------------
// --/1.0  | phuoc.ha      | Initial Create.
//         | 10-03-2019    |
//---------|---------------|---------------------------------------------------

package Customer;

import initEnvironement.BaseTest;
import initEnvironement.Constants;
import org.testng.annotations.*;
import screenObjects.CustomerPage;
import screenObjects.HomePage;
import screenObjects.LoginPage;
import utility.LogUtils;
import utility.entity.Customer;
import utility.helper.DateTime;

import java.lang.reflect.Method;
import java.util.Random;

import static SignUp.TestSignUp.guruUserName;
import static SignUp.TestSignUp.guruPassword;
import static utility.PropertiesUtils.urlWebsiteLoginPro;


public class TestCustomer extends BaseTest {
    String className = this.getClass().getSimpleName();
    private LoginPage loginPage;
    private HomePage homePage;
    private CustomerPage customerPage;
    private Customer customer;
    public static String cusId;
    public static String cusName;
    public static String cusEmail;
    String testCaseName;

    @BeforeClass
    public void beforeClass() {
        super.setUp(urlWebsiteLoginPro);
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        customerPage = new CustomerPage(driver);
        LogUtils.info("------------------------------Test Create New Customer-----------------------------");
    }

    @BeforeMethod
    public void setUp(Method method) {
        test = extent.startTest(method.getName()).assignCategory(
                getClass().getSimpleName());
    }

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
    @Test
    public void TC003_CreateNewCustomerSuccessfully() throws InterruptedException {
        testCaseName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        super.startRecordVideoAfterMethod(className, testCaseName);

        loginPage.Login(guruUserName, guruPassword);
        homePage.ClickLinkNewCustomer();
        customerPage.VerifyCustomerPageAllElements();
        customer = createCustomerData();
        customerPage.CreateNewCustomer(customer);
        Thread.sleep(2000);
        customerPage.TheTableHeaderTextShouldBe("Customer Registered Successfully!!!");
        customerPage.TheTableContentTextShouldBe("Registered Customer details are as follows:");
        customerPage.VerifyCustomerCreatedSuccessfully(customer);
        cusId = customerPage.GetCustomerId();
        cusName = customer.name;
        cusEmail = customer.email;

    }

    public Customer createCustomerData() {
        customer = new Customer();
        customer.name = ("Customer ".concat(getHelper().randomString()));
        String[] genders = {"m", "f"};
        customer.gender = getRandomStringArray(genders);
        customer.dateOfBirth = DateTime.getCurrentTime("MM/dd/yyyy");
        customer.address = getHelper().randomChars(10);
        String[] cities = {"Ho Chi Minh", "Ha Noi", "Da Nang"};
        customer.city = getRandomStringArray(cities);
        String[] states = {"District A", "District B", "District C"};
        customer.state = getRandomStringArray(states);
        customer.pin = getHelper().randomNumber(6);
        customer.telephone = "09".concat(getHelper().randomNumber(8));
        customer.email = "pha" + System.currentTimeMillis() + "@yopmail.com";
        customer.passWord = "123456";
        return customer;
    }

    public String getRandomStringArray(String[] strings) {
        Random ran = new Random();
        return strings[ran.nextInt(strings.length)];
    }

    @AfterMethod
    public void tearDown() {
        Constants.sClassName = className;
        super.stopRecordVideoAfterMethod();
        super.screenShotAfterMethod(className, testCaseName);
    }

    @AfterClass
    public void afterClass() {
        super.afterClass();
    }
}
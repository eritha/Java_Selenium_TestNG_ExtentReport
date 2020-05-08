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

package Account;

import initEnvironement.BaseTest;
import initEnvironement.Constants;
import org.testng.annotations.*;
import screenObjects.AccountPage;
import screenObjects.HomePage;
import screenObjects.LoginPage;
import utility.LogUtils;
import utility.entity.Account;
import utility.helper.DateTime;

import java.lang.reflect.Method;

import static Customer.TestCustomer.*;
import static SignUp.TestSignUp.guruUserName;
import static SignUp.TestSignUp.guruPassword;
import static utility.PropertiesUtils.urlWebsiteLoginPro;


public class TestAccount extends BaseTest {
    String className = this.getClass().getSimpleName();
    private LoginPage loginPage;
    private HomePage homePage;
    private AccountPage accountPage;
    public static String accId;
    private Account account;

    String testCaseName;

    @BeforeClass
    public void beforeClass() {
        super.setUp(urlWebsiteLoginPro);
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        accountPage = new AccountPage(driver);
        LogUtils.info("------------------------------Test Create New Account-----------------------------");
    }

    @BeforeMethod
    public void setUp(Method method) {
        test = extent.startTest(method.getName()).assignCategory(
                getClass().getSimpleName());
    }

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
    @Test
    public void TC004_CreateNewAccountSuccessfully() throws InterruptedException {
        testCaseName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        super.startRecordVideoAfterMethod(className, testCaseName);

        loginPage.Login(guruUserName, guruPassword);
        homePage.ClickLinkNewAccount();

        accountPage.VerifyAccountPageAllElements();
        accountPage.TheAccountFormTitleShouldBe("Add new account form");
        account = createAccountData();
        accountPage.CreateNewAccount(account);
        Thread.sleep(2000);
        accountPage.TheAccountFormTitleShouldBe("Account Generated Successfully!!!");
        accountPage.TheTableContentTextShouldBe("Created Account details are as follows:");
        accId = accountPage.GetAccountId();
        account.accId = accId;
        System.out.println("DEBUG >>> ".concat(account.toString()));
        System.out.println("DEBUG >>> ".concat(account.dateOpening));
//        Thread.sleep(3000);
        accountPage.VerifyAccountCreatedSuccessfully(account);
    }


    public Account createAccountData() {
        account = new Account();
        account.cusId = cusId;
        account.cusName = cusName;
        account.cusEmail = cusEmail;
        String[] accTypes = {"Savings", "Current"};
        account.accType = getHelper().getRandomStringArray(accTypes);
        account.dateOpening = DateTime.getCurrentTime("yyyy-MM-dd");
        account.currentAmount = getHelper().randomNumber(5);
        return account;
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
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

package Deposit;

import initEnvironement.BaseTest;
import initEnvironement.Constants;
import org.testng.annotations.*;
import screenObjects.DepositPage;
import screenObjects.HomePage;
import screenObjects.LoginPage;
import utility.LogUtils;
import utility.entity.Deposit;

import java.lang.reflect.Method;

import static Account.TestAccount.accId;
import static SignUp.TestSignUp.guruUserName;
import static SignUp.TestSignUp.guruPassword;
import static utility.PropertiesUtils.urlWebsiteLoginPro;


public class TestDeposit extends BaseTest {
    String className = this.getClass().getSimpleName();
    private LoginPage loginPage;
    private HomePage homePage;
    private DepositPage depositPage;
    public static String transId;
    private Deposit deposit;

    String testCaseName;

    @BeforeClass
    public void beforeClass() {
        super.setUp(urlWebsiteLoginPro);
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        depositPage = new DepositPage(driver);
        LogUtils.info("------------------------------Test Add New Deposit-----------------------------");
    }

    @BeforeMethod
    public void setUp(Method method) {
        test = extent.startTest(method.getName()).assignCategory(
                getClass().getSimpleName());
    }

    /*
     * SCENARIO: TC05_add_new_deposit_successfully
     * Precondition: Acc guru99 created from TestSignUp & Account ID from TestAccount suite "cusId"
     * #1: Login guru99/v4 by guru99 Account
     * #2: Goes to Deposit page
     * #3: Verify UI of Deposit page
     * #3: Input valid data & submit
     * #4: Verify Deposit is added successfully
     */
    @Test
    public void TC005_AddNewDepositSuccessfully() throws InterruptedException {
        testCaseName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        super.startRecordVideoAfterMethod(className, testCaseName);

        loginPage.Login(guruUserName, guruPassword);
//        Thread.sleep(5000);
        homePage.ClickLinkDeposit();

        depositPage.VerifyDepositPageAllElements();
        depositPage.TheDepositFormTitleShouldBe("Amount Deposit Form");

        deposit = createDepositData();
        depositPage.AddNewDeposit(deposit);
        Thread.sleep(2000);
        depositPage.TheDepositFormTitleShouldBe("Transaction details of Deposit for Account ".concat(deposit.accId));
        transId = depositPage.GetTransactionId();
        deposit.transId = transId;
        depositPage.VerifyDepositAddedSuccessfully(deposit);

    }


    public Deposit createDepositData() {
        deposit = new Deposit();
        deposit.accId = accId;
        deposit.amount = getHelper().randomNumber(5);
        deposit.typeOfTransaction = "Deposit";
        deposit.description = getHelper().randomString();
        return deposit;
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
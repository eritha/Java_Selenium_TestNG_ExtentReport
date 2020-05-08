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

package Login;

import initEnvironement.BaseTest;
import initEnvironement.Constants;
import org.testng.annotations.*;
import screenObjects.HomePage;
import screenObjects.LoginPage;
import utility.LogUtils;

import java.lang.reflect.Method;

import static SignUp.TestSignUp.guruUserName;
import static SignUp.TestSignUp.guruPassword;
import static utility.PropertiesUtils.urlWebsiteLoginPro;

public class TestLogin extends BaseTest {
    String className = this.getClass().getSimpleName();
    private LoginPage loginPage;
    private HomePage homePage;
    String testCaseName;

    @BeforeClass
    public void beforeClass() {
        super.setUp(urlWebsiteLoginPro);
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        LogUtils.info("------------------------------Test Login-----------------------------");
    }

    @BeforeMethod
    public void setUp(Method method) {
        test = extent.startTest(method.getName()).assignCategory(
                getClass().getSimpleName());
    }

    /*
     * SCENARIO: TC02_login_user_guru99_successfully
     * Precondition: Acc guru99 created from TestSignUp
     * #1: Goes to Login page
     * #2: Verify UI of Login page
     * #3: Input valid Account & submit
     * #4: Verify user logged successfully with Home Page display
     */
    @Test
    public void TC002_LoginUserSuccessfully() {
        testCaseName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        super.startRecordVideoAfterMethod(className, testCaseName);

        loginPage.VerifyLoginPageAllElements();
        loginPage.Login(guruUserName, guruPassword);

        homePage.VerifyHomePageAllElements();
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

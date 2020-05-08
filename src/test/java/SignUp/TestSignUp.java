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

package SignUp;

import initEnvironement.BaseTest;
import initEnvironement.Constants;
import org.testng.annotations.*;
import screenObjects.SignupPage;
import screenObjects.SignupSuccessfulPage;
import utility.LogUtils;
import utility.Result;

import java.lang.reflect.Method;

import static utility.PropertiesUtils.urlWebsiteSignupPro;


public class TestSignUp extends BaseTest {
    String className = this.getClass().getSimpleName();
    private SignupPage signupPage;
    private SignupSuccessfulPage signupSuccessfulPage;
    public static String guruUserName = "";
    public static String guruPassword = "";
    String testCaseName;

    @BeforeClass
    public void beforeClass() {
        super.setUp(urlWebsiteSignupPro);
        signupPage = new SignupPage(driver);
        signupSuccessfulPage = new SignupSuccessfulPage(driver);
        LogUtils.info("------------------------------Test SignUp-----------------------------");
    }

    @BeforeMethod
    public void setUp(Method method) {
        test = extent.startTest(method.getName()).assignCategory(
                getClass().getSimpleName());
    }

    /*
     * SCENARIO: TC01_create_new_user_guru99_successfully
     * Precondition: An email valid
     * #1: Goes to guru99 Signup page
     * #2: Verify UI of Signup page
     * #3: Input valid email & submit
     * #4: Verify user create with username & password
     * #5: Get user info for next suite inneed
     */
    @Test
    public void TC001_CreateNewUserSuccessfully() {
        testCaseName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        super.startRecordVideoAfterMethod(className, testCaseName);

        signupPage.VerifySignUpPageAllElements();
        signupPage.TheBarTextShouldBe("Guru99 Bank");
        signupPage.TheTableTextShouldBe("Enter your email address to get\naccess details to demo site");
        signupPage.TheEmailIdTextShouldBe("Email ID");
//        signupPage.InputEmail(accountEmail);
        signupPage.InputEmail("pha" + System.currentTimeMillis() + "@yopmail.com");
        signupPage.ClickButtonSubmit();
        signupSuccessfulPage.VerifyCreatedAccountSuccessfulPageAllElements();
        // Verify all element text on this page
        signupSuccessfulPage.TheBarTextShouldBe("Guru99 Bank");
        signupSuccessfulPage.TheTableTextShouldBe("Access details to demo site.");
        signupSuccessfulPage.TheUserIdTextShouldBe("User ID :");
        signupSuccessfulPage.ThePassWordTextShouldBe("Password :");
        signupSuccessfulPage.TheNoteTextShouldBe("This access is valid only for 20 days.");
        // Verify Account create successfully
        guruUserName = signupSuccessfulPage.GetAccountName();
        guruPassword = signupSuccessfulPage.GetAccountPassword();
        signupSuccessfulPage.VerifyAccount(guruUserName, guruPassword);
    }

    @AfterMethod
    public void tearDown() {
        Constants.sClassName = className;
        super.stopRecordVideoAfterMethod();
        super.screenShotAfterMethod(className, testCaseName);
//        if(Result.bResult)
//            test.pass("Passed");
    }

    @AfterClass
    public void afterClass() {
        super.afterClass();
    }
}

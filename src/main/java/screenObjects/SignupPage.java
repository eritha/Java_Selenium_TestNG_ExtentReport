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
package screenObjects;

import models.SignupElement;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import utility.Result;

public class SignupPage extends CommonPage {
    SignupElement signupElement;
    SignupSuccessfulPage signupSuccessfulPage;

    String email = "t" + System.currentTimeMillis() + "@yopmail.com";

    public SignupPage(WebDriver driver) {
        super(driver);
        signupElement = new SignupElement();
        signupSuccessfulPage = new SignupSuccessfulPage(driver);
    }

    /*
     * Input email after to create new Account
     */
    public void InputEmail(String email) {
        try {
            getHelper().waitForElementVisible(signupElement.ele_input_email);
            getHelper().findElement(signupElement.ele_input_email).sendKeys(email);
        } catch (Exception e) {
            Result.checkFail("Class SignupPage | Method InputEmail | Exception desc : " + e.getMessage());
        }
    }

    /*
     * Click "Submit" button to create new Account
     * Return SignupSuccessfulPage
     */
    public SignupSuccessfulPage ClickButtonSubmit() {
        try {
            getHelper().findElement(signupElement.ele_btn_submit).click();
        } catch (Exception e) {
            Result.checkFail("Class SignupPage | Method ClickButtonLogin | Exception desc : " + e.getMessage());
        }
        return new SignupSuccessfulPage(driver);
    }

    /*
     * Verify All elements in SignUp Page
     */
    public void VerifySignUpPageAllElements() {
        try {
            getHelper().waitForElementVisible(signupElement.ele_label_form_title);
            Assert.assertTrue(getHelper().isElementPresent(signupElement.ele_label_guru_bar));
            Assert.assertTrue(getHelper().isElementPresent(signupElement.ele_label_form_title));
            Assert.assertTrue(getHelper().isElementPresent(signupElement.ele_label_email));
            Assert.assertTrue(getHelper().isElementPresent(signupElement.ele_input_email));
            Assert.assertTrue(getHelper().isElementPresent(signupElement.ele_btn_submit));
        } catch (Exception e) {
            Result.checkFail("Class SignupPage | Method VerifySignUpPageAllElements | Exception desc : " + e.getMessage());
        }
    }

    /*
     * Verify the bar text in SignUp Page
     */
    public void TheBarTextShouldBe(String guruBarText) {
        try {
            Assert.assertTrue(getHelper().elementTextShoudlBe(signupElement.ele_label_guru_bar, guruBarText));
        } catch (Exception e) {
            Result.checkFail("Class SignupPage | Method TheBarTextShouldBe | Exception desc : " + e.getMessage());
        }
    }

    /*
     * Verify the table text in SignUp Page
     */
    public void TheTableTextShouldBe(String guruTableText) {
        try {
            Assert.assertTrue(getHelper().elementTextShoudlBe(signupElement.ele_label_form_title, guruTableText));
        } catch (Exception e) {
            Result.checkFail("Class SignupPage | Method TheTableTextShouldBe | Exception desc : " + e.getMessage());
        }
    }

    /*
     * Verify the email cusId text in SignUp Page
     */
    public void TheEmailIdTextShouldBe(String guruEmailIdText) {
        try {
            Assert.assertTrue(getHelper().elementTextShoudlBe(signupElement.ele_label_email, guruEmailIdText));
        } catch (Exception e) {
            Result.checkFail("Class SignupPage | Method TheEmailIdTextShouldBe | Exception desc : " + e.getMessage());
        }
    }
}

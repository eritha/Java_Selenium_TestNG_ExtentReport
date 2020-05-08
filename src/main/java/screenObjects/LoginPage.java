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

import models.LoginElement;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import utility.Result;

public class LoginPage extends CommonPage {
    LoginElement loginElement;
    HomePage homePage;

    public LoginPage(WebDriver driver) {
        super(driver);
        loginElement = new LoginElement();
    }

    /*
     * Input Account name
     */
    public void InputAccount(String accName) {
        try {
            getHelper().waitForElementVisible(loginElement.ele_input_user_id);
            getHelper().findElement(loginElement.ele_input_user_id).sendKeys(accName);
        } catch (Exception e) {
            Result.checkFail("Class LoginPage | Method InputAccount | Exception desc : " + e.getMessage());
        }
    }

    /*
     * Input password
     */
    public void InputPassword(String passWord) {
        try {
            getHelper().waitForElementVisible(loginElement.ele_input_password);
            getHelper().findElement(loginElement.ele_input_password).sendKeys(passWord);
        } catch (Exception e) {
            Result.checkFail("Class LoginPage | Method InputPassword | Exception desc : " + e.getMessage());
        }
    }

    /*
     * Click "Login" button to login
     * Return HomePage
     */
    public HomePage ClickButtonLogin() {
        try {
            getHelper().findElement(loginElement.ele_btn_login).click();
        } catch (Exception e) {
            Result.checkFail("Class LoginPage | Method ClickButtonLogin | Exception desc : " + e.getMessage());
        }
        return new HomePage(driver);
    }

    /*
     * Click "Reset" button clear all text fields of login page
     */
    public void ClickButtonReset() {
        try {
            getHelper().findElement(loginElement.ele_btn_reset).click();
        } catch (Exception e) {
            Result.checkFail("Class LoginPage | Method ClickButtonReset | Exception desc : " + e.getMessage());
        }
    }

    /*
     * Method Login to login guru99 V4
     * Return HomePage
     */
    public HomePage Login(String accName, String passWord) {
        try {
            InputAccount(accName);
            InputPassword(passWord);
            ClickButtonLogin();
        } catch (Exception e) {
            Result.checkFail("Class LoginPage | Method Login | Exception desc : " + e.getMessage());
        }
        return new HomePage(driver);
    }

    /*
     * Verify All elements in Login Page
     */
    public void VerifyLoginPageAllElements() {
        try {
            getHelper().waitForElementVisible(loginElement.ele_label_user_id);
            Assert.assertTrue(getHelper().isElementPresent(loginElement.ele_label_user_id));
            Assert.assertTrue(getHelper().isElementPresent(loginElement.ele_input_user_id));
            Assert.assertTrue(getHelper().isElementPresent(loginElement.ele_label_password));
            Assert.assertTrue(getHelper().isElementPresent(loginElement.ele_input_password));
            Assert.assertTrue(getHelper().isElementPresent(loginElement.ele_btn_login));
            Assert.assertTrue(getHelper().isElementPresent(loginElement.ele_btn_reset));
        } catch (Exception e) {
            Result.checkFail("Class LoginPage | Method VerifySignUpPageAllElements | Exception desc : " + e.getMessage());
        }
    }
}

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

package utility.helper;

import initEnvironement.BaseTest;
import initEnvironement.CommonElements;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import screenObjects.CommonPage;
import utility.LogUtils;
import utility.Result;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;

public class Helper extends CommonPage {
    public Helper(WebDriver driver) {
        super(driver);
    }

    protected Wait<WebDriver> wait;

    public enum enumOsType {
        Windows, Linux, MacOS, Unknown
    }

    private String jsColorElementBorderBlue = "arguments[0].style='border: 1px solid; border-color: blue'";
    private String jsColorElementBorderBlueRemove = "arguments[0].style='border: 0px solid; border-color: blue'";
    private String jsColorElementBorderGreen = "arguments[0].style='border: 2px solid; border-color: #008000'"; // Highlight when asserting #008000/#34B171/#91CB7B

    public class Constant {
        public static final int TIME_WAIT = 30;
        public static final int TIME_SLEEP = 5000;
    }

    /*
     * By locator
     */
    private By byLocator(String selectorTypeStr, String value) {
        By e = null;
        UISelectorType selector = UISelectorType.fromString(selectorTypeStr);
        switch (selector) {
            case ID:
                e = By.id(value);
                break;
            case TEXT_CONTAINS:
                e = By.xpath("//*[contains(@text, '" + value + "']");
                break;
            case TEXT:
                e = By.xpath("//*[@text = '" + value + "']");
                break;
            case TEXT_START_WITH:
                e = By.xpath("//*[starts-with(@text, '" + value + "']");
                break;
            case CLASS_NAME:
                e = By.className(value);
                break;
            case XPATH:
                e = By.xpath(value);
                break;
            case NAME:
                e = By.name(value);
                break;
            case LINK_TEXT:
                e = By.partialLinkText(value);
                break;
        }
        return e;
    }

    private By byLocator(String locator) {
        try {
            return byLocator(split(locator)[0], split(locator)[1]);
        } catch (Exception ex) {
            Result.checkFail("Class Helper | Method byLocator | Exception desc : " + ex.getMessage());
            return null;
        }
    }

    public String[] split(String str) {
        try {
            return str.split(":::");
        } catch (Exception ex) {
            Result.checkFail("Class Helper | Method split | Exception desc : " + ex.getMessage());
            return null;
        }
    }

    /*
     * Find Element
     */
    public WebElement findElement(String locator) {
        try {
            WebElement e = driver.findElement(byLocator(locator));
            ((JavascriptExecutor) driver).executeScript(jsColorElementBorderBlue, e);
            return e;
        } catch (Exception ex) {
            Result.checkFail("Class Helper | Method findElement | Exception desc : " + ex.getMessage()
                    + " \n Unable found locator: " + locator);
            return null;
        }
    }

    /*
     * Find Elements
     */
    public List<WebElement> findElements(String locator) {
        try {
            List<WebElement> e = driver.findElements(byLocator(locator));
            for (WebElement el : e) {
                ((JavascriptExecutor) driver).executeScript(jsColorElementBorderBlue, el);
            }
            return e;
        } catch (Exception ex) {
            Result.checkFail("Class Helper | Method findElements | Exception desc : " + ex.getMessage());
            return null;
        }
    }

    /*
     * Find Element to Assert
     */
    public WebElement findElementAssert(String locator) {
        try {
            WebElement e = driver.findElement(byLocator(locator));
            ((JavascriptExecutor) driver).executeScript(jsColorElementBorderBlue, e);
            Thread.sleep(300);
            ((JavascriptExecutor) driver).executeScript(jsColorElementBorderGreen, e); // Assert data
            return e;
        } catch (Exception ex) {
            Result.checkFail("Class Helper | Method findElement | Exception desc : " + ex.getMessage()
                    + " \n Unable found locator: " + locator);
            return null;
        }
    }

    /*
     * Find Elements to Assert
     */
    public List<WebElement> findElementsAssert(String locator) {
        try {
            List<WebElement> e = driver.findElements(byLocator(locator));
            for (WebElement el : e) {
                ((JavascriptExecutor) driver).executeScript(jsColorElementBorderBlue, el);
                Thread.sleep(300);
                ((JavascriptExecutor) driver).executeScript(jsColorElementBorderBlueRemove, e);
            }
            return e;
        } catch (Exception ex) {
            Result.checkFail("Class Helper | Method findElements | Exception desc : " + ex.getMessage());
            return null;
        }
    }

    /*
     * Click Element
     */
    public void clickElement(String locator) {
        try {
            WebElement e = driver.findElement(byLocator(locator));
            ((JavascriptExecutor) driver).executeScript(jsColorElementBorderBlue, e);
            if (e.isDisplayed() && e.isEnabled()) {
                e.click();
            }
        } catch (Exception ex) {
            Result.checkFail("Class Helper | Method clickElement | Exception desc : " + ex.getMessage()
                    + " \n Unable click locator: " + locator);
        }
    }

    /*
     * Click Element by Java Script
     */
    public void clickElementByJavascript(String locator)
    {
        WebElement e = driver.findElement(byLocator(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", e);
//        JavascriptExecutor js = ((JavascriptExecutor)(driver));
//        js.executeScript("arguments[0].click();", e);
    }

    /*
     * Navigate back of browser
     */
    public void backButton() {
        try {
            driver.navigate().back();
        } catch (Exception ex) {
            Result.checkFail("Class Helper | Method backButton | Exception desc : " + ex.getMessage());
        }
    }

    /*
     * Refresh current page
     */
    public void refreshPage() {
        try {
            driver.navigate().refresh();
        } catch (Exception ex) {
            Result.checkFail("Class Helper | Method refreshPage | Exception desc : " + ex.getMessage());
        }
    }

    /*
     * Wait element by ID
     */
    public Boolean waitElementByID(String locationID) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(locationID)));
        return element.isEnabled();
    }

    /*
     * Wait until element clickable
     */
    public void waitElementIsDisplayed(String locator) {
        WebElement el = findElement(locator);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(el));
    }

    /**
     * Verify element text
     *
     * @param locator
     * @return
     */
    public Boolean elementTextShoudlBe(String locator, String value) {
        Boolean isTextExists = false;
        try {
            WebElement el = findElementAssert(locator);
            String actualValue = el.getText();
            isTextExists = actualValue.equals(value);
        } catch (NoSuchElementException e) {
            Result.checkFail("Class Helper | Method elementTextShoudlBe | Actual = '" + findElementAssert(locator).getText() + "' ### Expected: '" + value + "'Exception desc : " + e.getMessage());
            return Result.bResult = false;
        }
        Result.bResult = isTextExists;
        return isTextExists;
    }

    /**
     * get exactly element text
     *
     * @param locator
     * @return
     */
    public String getTextElement(String locator) {
        WebElement el = findElement(locator);
        return el.getText();
    }

    /**
     * get exactly value of text field (txt/input)
     *
     * @param locator
     * @return
     */
    public String getValue(String locator) {
        WebElement el = findElement(locator);
        return el.getAttribute("value");
    }

    /**
     * get exactly value of text field (txt/input)
     *
     * @param locator
     * @param attribute
     * @return
     */
    public String getTextElementAttribute(String locator, String attribute) {
        WebElement el = findElement(locator);
        return el.getAttribute(attribute);
    }

    /*
     * Get Os Type
     */
    protected enumOsType getOsType() {
        enumOsType osType = enumOsType.Unknown;
        String osname = System.getProperty("os.name").toLowerCase();
        if (osname.contains("unix") || osname.contains("linux")) {
            osType = enumOsType.Linux;
        } else if (osname.contains("windows")) {
            osType = enumOsType.Windows;
        } else if (osname.contains("mac os")) {
            osType = enumOsType.MacOS;
        }
        return osType;
    }

    public WebDriver getDriver() {
        return driver;
    }

    /*
     * Add Log
     */
    public void addLog(String logmsg) {
        Reporter.log(logmsg + "</br>", true);

    }

    /*
     * Random a string with length = 6
     */
    public String randomString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    /*
     * Random a random number in size
     */
    public int getRandomIndex(int size) {
        int random = 0;
        try {
            random = new Random().nextInt(size);
        } catch (Exception e) {
            Result.checkFail("Class Helper | Method getRandomIndex | Exception desc : " + e.getMessage());
            e.printStackTrace();
        }
        return random;
    }

    /*
     * Random string number length input
     */
    public String randomNumber(int numberChars) {
        String SALTCHARS = "1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < numberChars) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    /*
     * Random a char with length input
     */
    public String randomChars(int numberChars) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < numberChars) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    /*
     * Wait until Jquery active
     */
    public void waitForAjax() {
        System.err.println("Checking active ajax calls by calling jquery.active ...");
        try {
            if (driver instanceof JavascriptExecutor) {
                JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
                for (int i = 0; i < Constant.TIME_WAIT; i++) {
                    Object numberOfAjaxConnections = jsDriver.executeScript("return jQuery.active");
                    // return should be a number
                    if (numberOfAjaxConnections instanceof Long) {
                        Long n = (Long) numberOfAjaxConnections;
                        System.err.println("Number of active jquery ajax calls: " + n);
                        if (n.longValue() == 0L)
                            break;
                    }
                    Thread.sleep(3000);
                }
            } else {
                System.err.println("Web driver: " + driver + " cannot execute javascript");
            }
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }

    /*
     * Check element present or not
     */
    public Boolean isElementPresent(String locator) {
        Boolean isPresent = Boolean.FALSE;
        try {
            isPresent = findElementsAssert(locator).size() > 0;
            Result.bResult = isPresent;
            return isPresent;
        } catch (NoSuchElementException ex) {
            Result.checkFail("Class Helper | Method isElementPresent | Exception desc : " + ex.getMessage());
            Result.bResult = isPresent;
            return isPresent;
        }
    }

    /*
     * Scroll visible element
     */
    public void scrollByVisibleElement(WebElement element) {
        try {
            JavascriptExecutor je = (JavascriptExecutor) driver;
            // now execute query which actually will scroll until that element is not
            // appeared on page.
            je.executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception ex) {
            Result.checkFail("Class Helper | Method scrollByVisibleElement | Exception desc : " + ex.getMessage());
        }
    }

    /*
     * Scroll element by Pixel
     */
    public void scrollByPixel(int xPixel, int yPixel) {
        try {
            // Create instance of Javascript executor
            JavascriptExecutor je = (JavascriptExecutor) driver;
            // This will scroll down the page by 1000 pixel vertical
            je.executeScript("window.scrollBy(" + xPixel, yPixel + ")");
        } catch (Exception ex) {
            Result.checkFail("Class Helper | Method scrollByPixel | Exception desc : " + ex.getMessage());
        }
    }

    /*
     * Hover element
     */
    public void hover(String locator) {
        Actions action = new Actions(driver);
        WebElement e = findElement(locator);
        try {
            action.moveToElement(e).build().perform();
        } catch (NoSuchElementException ex) {
            addLog("\n[######### HOVER ELEMENT FAILED =========> The element NOT EXIST or NOT CLICKABLE\n] "
                    + ex.getMessage());
        }
    }

    /*
     * Wait for a element is visible
     */
    public void waitForElementVisible(String locator) {
        WebDriverWait waitVisible = new WebDriverWait(driver, 30);
        waitVisible.until(ExpectedConditions.visibilityOfElementLocated(byLocator(locator)));
//        waitVisible.until(ExpectedConditions.invisibilityOfElementLocated(byLocator(locator)));
    }

    /*
     * Wait for a element show in limit (s) time.
     */
    public void waitForElementDisplay(String element, int limit) {
        int count = 0;
        boolean found = false;
        do {
            try {
                findElement(element);
                found = true;
            } catch (Exception ex) {
                count++;
                System.out.println("Element with locator: " + element + " does not show");
            }
        } while (count <= limit && !found);
    }

    /*
     * Confirm the alert of browser
     */
    public void confirmAlert() {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (Exception ex) {
            Result.checkFail("Class Helper | Method ConfirmAlert | Exception desc : " + ex.getMessage());
        }
    }

    /*
     * Get random a value from String[]
     */
    public String getRandomStringArray(String[] strings) {
        Random ran = new Random();
        return strings[ran.nextInt(strings.length)];
    }

    /*#####

    public Boolean isElementEnabled(String locator) {
        WebElement element = findElement(locator);
        return element.isEnabled();
    }

    public void waitForControlVisible(String locator) {
        WebDriverWait waitVisible = new WebDriverWait(driver, 30);
        waitVisible.until(ExpectedConditions.visibilityOfElementLocated(byLocator(locator)));
    }
    public boolean isControlDisplayed(String locator) {
        try {
            WebElement element = findElement(locator);
            return element.isDisplayed();
        } catch (final Exception e) {
            return false;
        }
    }
    public void selectDropDownListbByValue(String locatorDrpBtn, String locatorDrpOption, String nameCate)
            throws InterruptedException {
        try {
            findElement(locatorDrpBtn).click();
            List<WebElement> els = findElements(locatorDrpOption);

            for (WebElement element : els)
                if (element.getText().equalsIgnoreCase(nameCate)) {
                    element.click();
                    break;
                }
            System.out.println("Select main categories " + nameCate);

        } catch (NoSuchElementException ex) {
            addLog("\n[######### SELECT FAILED =========> The dropdown [" + nameCate + "] locator with option = ["
                    + nameCate + "] NOT EXIST or NOT CLICKABLE\n] " + ex.getMessage());
        }
    }

    public void executeJavascriptToElement(String locator) {
        WebElement element = findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public void executeJavascript(String javascript) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(javascript);
    }

    public void deleteValue(String locator) {
        WebElement element = findElement(locator);
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
    }



	*//*public class

	{
	public static final int TIME_WAIT = 30;
	public static final int TIME_SLEEP = 5000;

	public Helper(WebDriver driver) {
		super(driver);
	}*//*

    private By byLocator(String selectorTypeStr, String value) {
        By e = null;
        UISelectorType selector = UISelectorType.fromString(selectorTypeStr);
        switch (selector) {
            case ID:
                e = By.id(value);
                break;
            case TEXT_CONTAINS:
                e = By.xpath("//*[contains(@text, '" + value + "']");
                break;
            case TEXT:
                e = By.xpath("//*[@text = '" + value + "']");
                break;
            case TEXT_START_WITH:
                e = By.xpath("//*[starts-with(@text, '" + value + "']");
                break;
            case CLASS_NAME:
                e = By.className(value);
                break;
            case XPATH:
                e = By.xpath(value);
                break;
            case NAME:
                e = By.name(value);
                break;
            case LINK_TEXT:
                e = By.partialLinkText(value);
                break;
        }
        return e;
    }

    public By byLocator(String locator) {
        try {
            By e = byLocator(split(locator)[0], split(locator)[1]);
            return e;
        } catch (Exception ex) {
            Result.checkFail("Class Helper | Method byLocator | Exception desc : " + ex.getMessage());
            return null;
        }
    }

    public String[] split(String str) {
        try {
            String[] parts = str.split(":::");
            return parts;
        } catch (Exception ex) {
            Result.checkFail("Class Helper | Method split | Exception desc : " + ex.getMessage());
            return null;
        }
    }

    public WebElement findElement(String locator) {
        try {
            // waitForElementClickable(locator);
            WebElement e = driver.findElement(byLocator(locator));
            ((JavascriptExecutor) driver).executeScript(jsColorBorderElement, e);
            return e;
        } catch (Exception ex) {
            Result.checkFail("Class Helper | Method findElement | Exception desc : " + ex.getMessage()
                    + " \n Unable found locator: " + locator);
            return null;
        }
    }

    public List<WebElement> findElements(String locator) {
        try {
            List<WebElement> e = driver.findElements(byLocator(locator));
            ((JavascriptExecutor) driver).executeScript(jsColorBorderElement, e);
            return e;
        } catch (Exception ex) {
            Result.checkFail("Class Helper | Method findElements | Exception desc : " + ex.getMessage());
            return null;
        }
    }

    public boolean isElementsPresent(String arrString) {
        Boolean isElements = Boolean.FALSE;
        try {
            String[] parts = arrString.split(",\n");
            for (String locator : parts) {
                // get array
                // get identify
                String identify = Objects.requireNonNull(split(locator))[2];
                // get element on each array & higlight
                jsColorBorderElement = "arguments[0].style='border: 2px solid; border-color: green'";
                WebElement element = findElement(locator);
                switch (identify) {
                    case "checkExist":
                        LogUtils.info("");
                        isElements = findElements(locator).size() > 0;
                        if (!isElements) {
                            Result.checkFail(
                                    "Class Helper | Method isElementsPresent | Not able to found checkExist | Exception desc : "
                                            + locator);
                            break;
                        } else
                            continue;
                    case "checkSelected":
                        LogUtils.info("");
                        isElements = element.isSelected();
                        if (!isElements) {
                            Result.checkFail(
                                    "Class Helper | Method isElementsPresent | Not able to found checkSelected | Exception desc : "
                                            + locator);
                            break;
                        } else
                            continue;
                    case "checkText":
                        LogUtils.info("");
                        String text = Objects.requireNonNull(split(locator))[3];
                        System.out.println(text);
                        System.out.println("Text of this element: " + element.getText());
                        isElements = element.getText().trim().equalsIgnoreCase(text);
                        if (!isElements) {
                            Result.checkFail(
                                    "Class Helper | Method isElementsPresent | Not able to found checkText | Exception desc : "
                                            + locator + " &&& text is: " + text);
                            break;
                        } else
                            continue;
                    case "checkURL":
                        LogUtils.info("");
                        isElements = driver.getCurrentUrl().equalsIgnoreCase(Objects.requireNonNull(split(locator))[3]);
                        if (!isElements) {
                            Result.checkFail(
                                    "Class Helper | Method isElementsPresent | Not able to found checkURL | Exception desc : "
                                            + locator);
                            break;
                        } else
                            continue;
                }
            }
            return Result.bResult = isElements;
        } catch (NoSuchElementException e) {
            Result.checkFail("Class Helper | Method isElementsPresent | Exception desc : " + e.getMessage());
            return isElements;
        }
    }

    public boolean isElementsPresent(String arrString, Object object) {
        Boolean isElements = Boolean.FALSE;
        try {
            String[] parts = arrString.split(",\n");
            for (String locator : parts) {
                // get array
                // get identify
                String identify = Objects.requireNonNull(split(locator))[2];
                // get element on each array & higlight
                jsColorBorderElement = "arguments[0].style='border: 2px solid; border-color: green'";
                WebElement element = findElement(locator);
                switch (identify) {
                    case "checkExist":
                        LogUtils.info("");
                        isElements = findElements(locator).size() > 0;
                        if (!isElements) {
                            Result.checkFail(
                                    "Class Helper | Method isElementsPresent | Not able to found checkExist | Exception desc : "
                                            + locator);
                            break;
                        } else
                            continue;
                    case "checkSelected":
                        LogUtils.info("");
                        isElements = element.isSelected();
                        if (!isElements) {
                            Result.checkFail(
                                    "Class Helper | Method isElementsPresent | Not able to found checkSelected | Exception desc : "
                                            + locator);
                            break;
                        } else
                            continue;
                    case "checkText":
                        LogUtils.info("");
                        String newText = invokeToMethod(object, Objects.requireNonNull(split(locator))[3]);
                        System.out.println(newText);
                        System.out.println("Text of this element: " + element.getText());
                        isElements = element.getText().trim().equalsIgnoreCase(newText);
                        if (!isElements) {
                            Result.checkFail(
                                    "Class Helper | Method isElementsPresent | Not able to found checkText | Exception desc : "
                                            + locator + " &&& text is: " + newText);
                            break;
                        } else
                            continue;
                    case "checkURL":
                        LogUtils.info("");
                        isElements = driver.getCurrentUrl().equalsIgnoreCase(Objects.requireNonNull(split(locator))[3]);
                        if (!isElements) {
                            Result.checkFail(
                                    "Class Helper | Method isElementsPresent | Not able to found checkURL | Exception desc : "
                                            + locator);
                            break;
                        } else
                            continue;
                    case "checkPlaceHolder":
                        LogUtils.info("");
                        String expectedText = invokeToMethod(object, Objects.requireNonNull(split(locator))[3]);
                        System.out.println(expectedText);
                        System.out.println("Placeholder of this element: " + element.getAttribute("placeholder"));
                        isElements = element.getAttribute("placeholder").trim().equalsIgnoreCase(expectedText);
                        if (!isElements) {
                            Result.checkFail(
                                    "Class Helper | Method isElementsPresent | Not able to found checkPlaceHolder | Exception desc : "
                                            + locator + " &&& text is: " + expectedText);
                            break;
                        } else
                            continue;
                }
            }
            return Result.bResult = isElements;
        } catch (NoSuchElementException e) {
            Result.checkFail("Class Helper | Method isElementsPresent | Exception desc : " + e.getMessage());
            return isElements;
        }
    }

    // tìm đến method checkMultiLanguage để chạy cái method đó
    public String invokeToMethod(Object object, String compareText) {
        String text = "";
        try {
            for (Method aMethod : method) {
                if (aMethod.getName().trim().equalsIgnoreCase("checkMultiLanguages")) {
                    text = (String) aMethod.invoke(object, compareText);
                    break;
                }
            }
        } catch (NoSuchElementException e) {
            Result.checkFail("Class Helper | Method invokeToMethod | Exception desc : " + e.getMessage());
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return text;
    }

    public void backButton() {
        try {
            driver.navigate().back();
        } catch (Exception ex) {
            Result.checkFail("Class Helper | Method backButton | Exception desc : " + ex.getMessage());
        }
    }

    public void refreshPage() {
        try {
            driver.navigate().refresh();
        } catch (Exception ex) {
            Result.checkFail("Class Helper | Method refreshPage | Exception desc : " + ex.getMessage());
        }
    }

    public boolean isElementEnable(String locator) {
        try {
            boolean isEnabled = driver.findElement(byLocator(locator)).isEnabled();
            if (isEnabled) {
                addLog("Element was enabled : " + locator);
                return true;
            } else {
                addLog("Element was disabled : " + locator);
                return false;
            }
        } catch (NoSuchElementException e) {
            Result.checkFail("Class Helper | Method isElementEnable | Exception desc : " + locator);
            return false;
        }
    }

    public boolean isElementDisable(String locator) {
        try {
            boolean isEnabled = driver.findElement(byLocator(locator)).isEnabled();
            if (isEnabled == false) {
                addLog("Element was disabled : " + locator);
                return true;
            } else {
                addLog("Element was enabled : " + locator);
                return false;
            }
        } catch (NoSuchElementException e) {
            addLog("Element doesn't existed : " + locator);

            return false;
        }
    }

    public Boolean waitElementByID(String locationID) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(locationID)));
        return element.isEnabled();
    }

    public void waitElementIsDisplayed(String locator) {
        WebElement el = findElement(locator);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(el));
    }

    public void compareTextFromList(String locator, String textCompare) {
        List<WebElement> list = findElements(locator);
        for (int i = 0; i < list.size(); i++) {
            String text = list.get(i).getText();
            if (text.equalsIgnoreCase(textCompare)) {
                addLog("Value is: " + text + "\nThis case is PASSED");
                break;
            }
        }
    }

    public void compareTextFromList(By locator, String textCompare) {
        List<WebElement> list = driver.findElements(locator);
        for (int i = 0; i < list.size(); i++) {
            String text = list.get(i).getText();
            if (text.equalsIgnoreCase(textCompare)) {
                addLog("Compare: " + text + "\nThis case is passed");
                break;
            }
        }
    }

    public String changeFormatDate(String date, String read, String write) {
        String formattedDate = "";
        SimpleDateFormat readFormat = new SimpleDateFormat(read);
        SimpleDateFormat writeFormat = new SimpleDateFormat(write);
        try {
            formattedDate = writeFormat.format(readFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // System.out.println(" ===== STEP =====> format: "+formattedDate);
        return formattedDate;
    }

    *//**
     * Verify element text
     *
     * @param locator
     * @return
     *//*
    public Boolean elementTextShoudlBe(String locator, String value) {
        WebElement el = findElement(locator);
        String actualValue = el.getText();
        return actualValue.equals(value);
    }

    *//**
     * get exactly element text
     *
     * @param locator
     * @return
     *//*
    public String getTextElement(String locator) {
        WebElement el = findElement(locator);
        return el.getText();
    }

    *//**
     * get exactly value of text field (txt/input)
     *
     * @param locator
     * @return
     *//*
    public String getValue(String locator) {
        WebElement el = findElement(locator);
        return el.getAttribute("value");
    }

    *//**
     * get exactly value of text field (txt/input)
     *
     * @param locator
     * @param attribute
     * @return
     *//*
    public String getTextElementAttribute(String locator, String attribute) {
        WebElement el = findElement(locator);
        return el.getAttribute(attribute);
    }

    *//**
     * get textElements for list
     *
     * @param locator
     * @param i
     * @return
     *//*
    public String getTextElements(String locator, int i) {
        List<WebElement> list = findElements(locator);
        // System.out.println(" ===== STEP =====> - "+list.get(i).getText());
        return list.get(i).getText();
    }

    public String getTextElementsFromMain(String mainBranch, int iMain, String subLocator) {
        // get text element from main branch --> then get sub branch
        List<WebElement> list = findElements(mainBranch);
        String getTextSubLocator = list.get(iMain).findElement(byLocator(subLocator)).getText();
        // System.out.println(" ===== STEP =====> - "+getTextSubLocator);
        return getTextSubLocator;
    }

    *//**
     * @param getDate
     * @param getTime
     * @return
     * @throws ParseException
     *//*
    public boolean checkDurationTime(String getDate, String getTime) {
        Calendar calendarOfCurrentDate = Calendar.getInstance();
        Date currentDate = new Date();
        calendarOfCurrentDate.setTime(currentDate);

        String getDateTime = getDate + " " + getTime;
        System.out.println(" ===== STEP =====> -------------------------------------------------");

        DateFormat dateTimeFormat = new SimpleDateFormat("E, MMMM dd, yyyy hh:mm a", Locale.ENGLISH);
        Date compareDateTime = null;
        try {
            compareDateTime = dateTimeFormat.parse(getDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long MAX_DURATION = MILLISECONDS.convert(30, MINUTES);
        long duration = compareDateTime.getTime() - currentDate.getTime();

        if (duration <= MAX_DURATION) {
            System.out.println(" ===== STEP =====> return true");
            return true;
        }
        return false;
    }

    protected enumOsType getOsType() {
        enumOsType osType = enumOsType.Unknown;
        String osname = System.getProperty("os.name").toLowerCase();
        if (osname.contains("unix") || osname.contains("linux")) {
            osType = enumOsType.Linux;
        } else if (osname.contains("windows")) {
            osType = enumOsType.Windows;
        } else if (osname.contains("mac os")) {
            osType = enumOsType.MacOS;
        }
        return osType;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void addLog(String logmsg) {
        Reporter.log(logmsg + "</br>", true);

    }

    public void addErrorLog(String logmsg) {
        Reporter.log("<font color='red'> " + logmsg + " </font></br>", true);
    }

    public void addSuccessLog(String logmsg) {
        Reporter.log("<font color='green'> " + logmsg + " </font></br>", true);
    }

    public String randomString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    // Return a random number in a range
    public int getRandomIndexWithRange(int Min, int Max) {
        int random = 0;
        try {
            random = Min + new Random().nextInt(((Max - Min) + 1));
        } catch (Exception e) {
            Result.checkFail("Class Helper | Method getRandomIndexWithRange | Exception desc : " + e.getMessage());
            e.printStackTrace();
        }
        return random;
    }

    // Return a random number in size
    public int getRandomIndex(int size) {
        int random = 0;
        try {
            random = new Random().nextInt(size);
        } catch (Exception e) {
            Result.checkFail("Class Helper | Method getRandomIndex | Exception desc : " + e.getMessage());
            e.printStackTrace();
        }
        return random;
    }

    public String randomNumber(int numberChars) {
        String SALTCHARS = "1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < numberChars) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    public String randomChars(int numberChars) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < numberChars) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    public void waitForAjax() {
        System.err.println("Checking active ajax calls by calling jquery.active ...");
        try {
            if (driver instanceof JavascriptExecutor) {
                JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
                for (int i = 0; i < Constant.TIME_WAIT; i++) {
                    Object numberOfAjaxConnections = jsDriver.executeScript("return jQuery.active");
                    // return should be a number
                    if (numberOfAjaxConnections instanceof Long) {
                        Long n = (Long) numberOfAjaxConnections;
                        System.err.println("Number of active jquery ajax calls: " + n);
                        if (n.longValue() == 0L)
                            break;
                    }
                    Thread.sleep(3000);
                }
            } else {
                System.err.println("Web driver: " + driver + " cannot execute javascript");
            }
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }

    public void reloadPage() {
        try {
            addLog("Reload current page");
            driver.navigate().refresh();
        } catch (NoSuchElementException e) {
            addLog("Can not reloadPage current page");

        }
    }

    public void type(String locator, String data) {
        try {
            // waitForAjax();
            findElement(locator).clear();
            addLog("change data : " + data);
            findElement(locator).sendKeys(data);
        } catch (NoSuchElementException e) {
            addLog("NoSuchElementException at editData :  " + locator);

        }

    }

    public void clickClassName(String classname) {
        // waitForAjax();
        try {
            addLog("Click : " + classname);
            findElement(classname).click();
            // waitForAjax();
            //
        } catch (NoSuchElementException e) {
            addLog("No element exception : " + classname);

        }
    }

    public String getTextByXpath(String locator) {
        String text = "";
        try {
            text = findElement(locator).getText();
            addLog("Text : " + text);
        } catch (NoSuchElementException e) {
            addLog("NoSuchElementException at getTextByXpath :  " + locator);
        }
        return text;
    }

    public String jsID(String Id) {
        String text = "";
        try {
            WebElement btn = (WebElement) ((JavascriptExecutor) driver)
                    .executeScript("return document.getElementById(Id)");
            text = btn.getText();
            addLog("Text : " + text);
        } catch (NoSuchElementException e) {
            addLog("NoSuchElementException at getTextByjsID :  " + Id);
        }
        return text;
    }

    public Boolean isElementPresent(String locator) {
        Boolean isPresent = Boolean.FALSE;
        try {
            jsColorBorderElement = "arguments[0].style='border: 2px solid; border-color: green'";
            isPresent = findElements(locator).size() > 0;
            return isPresent;
        } catch (NoSuchElementException ex) {
            Result.checkFail("Class Helper | Method isElementPresent | Exception desc : " + ex.getMessage());
            return isPresent;
        }
    }

    public Boolean isElementNotPresent(String locator) {
        Boolean isPresent = Boolean.FALSE;
        try {
            jsColorBorderElement = "arguments[0].style='border: 2px solid; border-color: green'";
            isPresent = findElements(locator).size() == 0;
            return isPresent;
        } catch (NoSuchElementException ex) {
            Result.checkFail("Class Helper | Method isElementNotPresent | Exception desc : " + ex.getMessage());
            return isPresent;
        }
    }

    public Boolean isElementSelected(String locator) {
        Boolean isSelected = Boolean.FALSE;
        try {
            System.out.print(locator + "\n>>> DEBUG CHECKBOX STATUS >>>>>CHECKED>>>>:"
                    + findElement(locator).getAttribute("checked"));
            String expected = findElement(locator).getAttribute("checked");
            Assert.assertEquals(expected, "true");
            return true;
        } catch (NoSuchElementException ex) {
            Result.checkFail(
                    "Class Helper | Method isElementSelected | Exception desc : " + locator + "\n" + ex.getMessage());
            return false;
        }
    }

    public Boolean isElementUnSelected(String locator) {
        try {
            System.out.print(locator + "\n>>> DEBUG CHECKBOX STATUS >>>>>UNCHECKED>>>>:"
                    + findElement(locator).getAttribute("checked"));
            ;
            String expected = findElement(locator).getAttribute("checked");
            Assert.assertNotEquals(expected, "true");
            return true;
        } catch (NoSuchElementException ex) {
            Result.checkFail(
                    "Class Helper | Method isElementUnSelected | Exception desc : " + locator + "\n" + ex.getMessage());
            return false;
        }
    }

    public boolean isElementPresent(WebElement element) {
        try {
            if (element == null) {
                return false;
            }
            boolean isDisplayed = element.isDisplayed();
            if (isDisplayed) {
                addLog("Element displayed : " + element.getText());
                return true;
            } else {
                addLog("Element doesn't existed : " + element.getText());
                return false;
            }
        } catch (NoSuchElementException e) {
            addLog("NoSuchElementException on isElementPresent " + element.getText());
            return false;
        }
    }

    public void scrollByVisibleElement(WebElement element) {
        try {
            // Create instance of Javascript executor
            JavascriptExecutor je = (JavascriptExecutor) driver;
            // now execute query which actually will scroll until that element is not
            // appeared on page.
            je.executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception ex) {
            Result.checkFail("Class Helper | Method scrollByVisibleElement | Exception desc : " + ex.getMessage());
        }
    }

    public void scrollByPixel(int xPixel, int yPixel) {
        try {
            // Create instance of Javascript executor
            JavascriptExecutor je = (JavascriptExecutor) driver;
            // This will scroll down the page by 1000 pixel vertical
            je.executeScript("window.scrollBy(" + xPixel, yPixel + ")");
        } catch (Exception ex) {
            Result.checkFail("Class Helper | Method scrollByPixel | Exception desc : " + ex.getMessage());
        }
    }

    public void scrollDownByPage() {
        try {
            // Create instance of Javascript executor
            JavascriptExecutor je = (JavascriptExecutor) driver;
            // This will scroll the web page till end.
            je.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        } catch (Exception ex) {
            Result.checkFail("Class Helper | Method scrollDownByPage | Exception desc : " + ex.getMessage());
        }
    }

    public void scrollUpByPage() {
        try {
            // Create instance of Javascript executor
            JavascriptExecutor je = (JavascriptExecutor) driver;
            // This will scroll the web page till end.
            je.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
        } catch (Exception ex) {
            Result.checkFail("Class Helper | Method scrollUpByPage | Exception desc : " + ex.getMessage());
        }
    }

    public String getTextByXpath(WebElement element) {
        try {
            // waitForAjax();
            String text = element.getText();
            addLog("Text : " + text);
            return text;
        } catch (NoSuchElementException e) {
            addLog("NoSuchElementException at getTextByXpath : " + element);

        }
        return "";
    }

    // -----------------------methods from homeController
    public boolean isLinkExist(String locator) {
        try {
            String isHref = findElement(locator).getAttribute("href");
            if (isHref != null) {
                return true;
            }
        } catch (NoSuchElementException e) {
            System.out.println("NoSuchElementException" + locator);
        }
        return false;
    }

    public void editData(WebElement element, String data) {
        try {
            // waitForAjax();
            element.clear();
            addLog("change data : " + data);
            element.sendKeys(data);
            // waitForAjax();
        } catch (NoSuchElementException e) {
            addLog("NoSuchElementException at editData :  " + element);
        }
    }

    public String clickOptionByIndex(String locator, int indexNumber) {
        try {
            waitForAjax();
            WebElement dropDownListBox = driver.findElement(byLocator(locator));
            Select clickThis = new Select(dropDownListBox);
            System.out.print(clickThis);
            addLog("click : item index " + indexNumber);
            clickThis.selectByIndex(indexNumber);
            String textSelected = getItemSelected(locator);
            addLog("click : " + textSelected);
            // wait data loading
            // waitForAjax();
            return textSelected;
        } catch (NoSuchElementException e) {
            addErrorLog("No such element exception");
        }
        return "";
    }

    public String getItemSelected(String xpath) {
        return readField(xpath);
    }

    public String readField(String locator) {
        try {
            // waitForAjax();
            WebElement footer = driver.findElement(byLocator(locator));
            List<WebElement> columns = footer.findElements(By.tagName("option"));
            for (WebElement column : columns) {
                if (column.isSelected()) {
                    String selected = column.getText();
                    addLog("Item selected : " + selected);
                    return selected;
                }
            }
        } catch (NoSuchElementException e) {
            System.err.println("NoSuchElementException at readField : " + locator);
        }
        return "";
    }

    *//**
     * get size of the input table id
     *
     * @param locator
     *            get from class: PageHome (BRAND_TABLE_INFO, LINK_APP_DEVICES,
     *            LINK_AUDIO_ROUTES, COMPANY_LIST_TABLE_INFO, PRODUCT_TABLE_INFO,
     *            ADMIN_USER_LIST_TABLE_INFO, PROMOTION_TABLE_INFO)
     * @return
     *//*
    public int getPageSize(String locator) {
        try {
            String text = "";
            if (findElements(locator).size() > 0) {
                text = findElement(locator).getText();
            }
            addLog("page size text: " + text);
            int size = StringUtils.getPageSize(text);
            return size;
        } catch (NoSuchElementException e) {
            addErrorLog("-------NoSuchElementException------- : getPageSize");
        }
        return 0;
    }

    public void clickText(String text) {
        try {
            // waitForAjax();
            addLog("Click : " + text);
            // Thread.sleep(2000);
            driver.findElement(By.partialLinkText(text)).click();
            // Thread.sleep(1000);
            // waitForAjax();
        } catch (NoSuchElementException e) {
            System.err.println("No element exception : " + text);
        }
        // catch (InterruptedException e) {
        // e.printStackTrace();
        // }
    }

    public void clickHref(String href) {
        try {
            // waitForAjax();
            addLog("Click : " + href);
            // Thread.sleep(2000);
            driver.findElement(By.cssSelector(href)).click();
            // href example: "a[href*='long']"
            // Thread.sleep(1000);
            // waitForAjax();
        } catch (NoSuchElementException e) {
            System.err.println("No element exception : " + href);
        }
        // catch (InterruptedException e) {
        // e.printStackTrace();
        // }
    }

    public void navigateToUrl(String url) {
        try {
            // waitForAjax();
            addLog("Navigate to : " + url);
            // Thread.sleep(2000);
            driver.navigate().to(url);
            // href example: "a[href*='long']"
            // Thread.sleep(1000);
            // waitForAjax();
        } catch (NoSuchElementException e) {
            System.err.println("No element exception : " + url);
        }
        // catch (InterruptedException e) {
        // e.printStackTrace();
        // }
    }

    public void assertBackGroundColor(String name, String verify) {
        try {
            waitForAjax();
            addLog("get background color : " + name);
            // Thread.sleep(2000);
            // ing color =
            // driver.findElement(By.name("btnK")).getCssValue("background-color");
            String color = driver.findElement(By.name(name)).getCssValue("background-color");
            System.out.println("color is " + color);
            Assert.assertEquals(verify, color);
            // driver.navigate().to(url);
            // href example: "a[href*='long']"
            // Thread.sleep(1000);
            // waitForAjax();
        } catch (NoSuchElementException e) {
            System.err.println("No element exception : " + name);
        }
        // catch (InterruptedException e) {
        // e.printStackTrace();
        // }
    }

    public void inputTextToField(String text, String locator) {
        try {
            // waitForAjax();
            addLog("input text to : " + locator);
            // Thread.sleep(2000);
            // ing color =
            // driver.findElement(By.name("btnK")).getCssValue("background-color");
            findElement(locator).sendKeys(text);
            // driver.navigate().to(url);
            // href example: "a[href*='long']"
            // Thread.sleep(1000);
            // waitForAjax();
        } catch (NoSuchElementException e) {
            System.err.println("No element exception : " + locator);
        }
        // catch (InterruptedException e) {
        // e.printStackTrace();
        // }
    }

    public void editData(String editXpath, String data) {
        if (data != null) {
            try {
                // waitForAjax();
                WebElement element = driver.findElement(By.xpath(editXpath));
                // note: work around because element.clear() wont' work in some cases
                element.sendKeys(Keys.BACK_SPACE);
                element.sendKeys(Keys.BACK_SPACE);
                element.clear();
                addLog("change data : " + data);
                // driver.findElement(By.xpath(editXpath)).sendKeys(data);
                element.sendKeys(data);
                // waitForAjax();
            } catch (NoSuchElementException e) {
                Result.checkFail("Class Helper | Method editData | Exception desc : " + e.getMessage());

            }
        }
    }

    public void selectConfirmationDialogOption(String option) {
        try {
            Thread.sleep(3000);
            addLog("Select option: " + option);
            findElement("xpath:://*[@href='javascript:;' and text() = '" + option + "']").click();
            Thread.sleep(2000);
            // waitForAjax();
        } catch (NoSuchElementException e) {
            addLog("No such element exception");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // public void click(WebElement element) {
    // try {
    // addLog("Click element: //*[@id=" + element.getAttribute("id") + "]");
    // element.click();
    // //waitForAjax();
    // } catch (NoSuchElementException e) {
    // addLog("Element: " + element + " is not present");
    // }
    // }

    public boolean waitForElementClickable(String locator) {
        try {
            addLog("Wait for element: " + locator + " clickable");
            wait.until(ExpectedConditions.elementToBeClickable(byLocator(locator)));
            return true;

        } catch (NoSuchElementException e) {
            addLog("No such element: " + locator);
            return false;
        }
    }

    public boolean waitForElementDisappear(String locator) {
        try {
            addLog("Wait for element: " + locator + " disappear");
            wait.until(ExpectedConditions.invisibilityOfElementLocated(byLocator(locator)));
            return true;

        } catch (NoSuchElementException e) {
            addLog("No such element: " + locator);
            return false;
        }
    }

    public boolean checkMessageDisplay(String message) {
        String text = driver.getPageSource();
        if (text.contains(message)) {
            addLog("Message: " + message + " found");
            return true;
        } else {
            addLog("Message: " + message + " not found");
            return false;
        }
    }

    public int getTotalItem(String xpath) {
        try {
            String text = getTextByXpath(xpath);
            return StringUtils.getNumAtIndex(text, 2);
        } catch (NoSuchElementException e) {
            System.err.println("No element exception : getTotalItem");
        }
        return 0;
    }

    public int getPerPage(String xpath) {
        try {
            String text = getTextByXpath(xpath);
            return StringUtils.getNumAtIndex(text, 1);
        } catch (NoSuchElementException e) {
            System.err.println("No element exception : getPerPage");
        }
        return 0;
    }

    // TODO move to wrapper class
    public boolean selectACheckbox(String locator) {
        try {
            WebElement checkbox = driver.findElement(byLocator(locator));
            if (!checkbox.isSelected()) {
                addLog("Select checkbox: " + locator);
                checkbox.click();
                // waitForAjax();
                return true;
            }
            addLog("Checkbox: " + locator + " is already selected");
            return true;
        } catch (NoSuchElementException e) {
            addLog("No such element: " + locator);
            return false;
        }
    }

    public void clickid(String id) {
        // TODO Auto-generated method stub
        // waitForAjax();
        try {
            addLog("Click : " + id);
            driver.findElement(By.xpath(id)).click();
            // waitForAjax();

        } catch (NoSuchElementException e) {
            addLog("No element exception : " + id);
            Assert.assertTrue(false);
        }
    }

    public void typeid(String id, String data) {
        // TODO Auto-generated method stub
        try {
            // waitForAjax();
            driver.findElement(By.id(id)).clear();
            addLog("change data : " + data);
            driver.findElement(By.id(id)).sendKeys(data);
        } catch (NoSuchElementException e) {
            addLog("NoSuchElementException at editData :  " + id);
        }
    }

    public String gettextelementbytagname(String tagname) {
        try {
            WebElement webElement = driver.findElement(By.tagName(tagname));
            addLog("Element is get successful ");
            String text = webElement.getText();
            addLog("Text : " + text);
            return text;
        } catch (NoSuchElementException e) {
            addLog("NoSuchElementException: Element was not exist ");
        }
        return "";
    }

    public static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public String randomDataOfBirth(int yearStartInclusive, int yearEndExclusive) {
        LocalDate start = LocalDate.ofYearDay(yearStartInclusive, 1);
        LocalDate end = LocalDate.ofYearDay(yearEndExclusive, 1);

        long longDays = ChronoUnit.DAYS.between(start, end);
        int days = (int) longDays;
        if (days != longDays) {
            throw new IllegalStateException("int overflow; too many years");
        }
        int day = randBetween(0, days);
        LocalDate dateOfBirth = start.plusDays(day);

        return dateOfBirth.toString();
    }

    *//**
     * @param Project
     *            Project Name: Cupid, Social Network, Market, ...
     * @param ClassNames
     *            Current Class Name, contains test cases
     * @param Result
     *            Passed or Failed
     * @param TCsID
     *            Current Test Case ID
     * @return A screenshot locate in path with given param above
     *//*
    public String takeScreenshot(String Project, String ClassNames, String Result, String TCsID) {
        String sProjectPath = new File("test-reports/").getAbsolutePath().concat(File.separator)
                .concat(BaseTest.reportFolder).concat(File.separator).concat(Project).concat(File.separator);
        DateFormat dateFormat = new SimpleDateFormat("_yyyy_MM_dd_HH_mm_ss");
        // get current date time with Date()
        Date date = new Date();
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String fileScrShot = sProjectPath.concat(ClassNames) + File.separator + TCsID + File.separator + Result + "_"
                + dateFormat.format(date).toString() + ".png";
        CommonElements.sScreenShot_Path = Project.concat(File.separator).concat(ClassNames) + File.separator + TCsID
                + File.separator + Result + "_" + dateFormat.format(date).toString() + ".png";
        try {
            FileUtils.copyFile(scrFile, new File(fileScrShot));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.err.println(e);
        }

        addLog("Captured a screenshot to: " + fileScrShot);
        return fileScrShot;
    }

    public void selectBeecowDropdown(String name, String value) throws InterruptedException {
        String locatorDrpBtn = String.format("xpath::.//div[contains(@class,'%s')]/button", name);
        String locatorDrpOption = String.format("xpath::.//div[contains(@class,'%s')]//a[text()='%s']", name, value);
        try {
            findElement(locatorDrpBtn).click();
            waitForElementClickable(locatorDrpOption);
            hover(locatorDrpOption);
            Thread.sleep(500);
            findElement(locatorDrpOption).click();

        } catch (NoSuchElementException ex) {
            addLog("\n[######### SELECT FAILED =========> The dropdown [" + name + "] locator with option = " + "["
                    + value + "] NOT EXIST or NOT CLICKABLE\n] " + ex.getMessage());
        }
    }

    public void selectRandomDropDownList(String locatorDrpBtn, String locatorDrpOption) throws InterruptedException {
        String nameCate = "";
        try {
            // click element at dropdown list to show all items
            WebElement drp = findElement(locatorDrpBtn);
            drp.click();
            System.out.println("-----Click button to show dropdown successfully-----");
            // get random item from dropdown list
            List<WebElement> els = findElements(locatorDrpOption);
            int cate = getRandomIndex(els.size() - 1);
            System.out.println("------Total of list dropdown----" + els.size());
            System.out.println("------Value is selected in position----" + cate);
            els.get(cate).click();
            System.out.println("------Value of dropdown will be selected----" + nameCate);
            // click item is selected
            // waitForElementClickable(locatorDrpOption);
            // hover(locatorDrpOption);
            System.out.println("Select main categories---" + nameCate);
        } catch (NoSuchElementException ex) {
            addLog("\n[######### SELECT FAILED =========> The dropdown [" + nameCate + "] locator with option = ["
                    + nameCate + "] NOT EXIST or NOT CLICKABLE\n] " + ex.getMessage());
        }
    }

    public void selectRandomDropDownListByValue(String locatorDrpBtn, String locatorDrpOption)
            throws InterruptedException {
        String nameCate = "";
        try {

            findElement(locatorDrpBtn).click();
            List<WebElement> els = findElements(locatorDrpOption);
            int cate = getRandomIndex(els.size() - 1);
            nameCate = els.get(cate).getText();
            for (WebElement element : els)
                if (element.getText().equalsIgnoreCase(nameCate)) {
                    element.click();
                    break;
                }
            System.out.println("Select main categories " + nameCate);

        } catch (NoSuchElementException ex) {
            addLog("\n[######### SELECT FAILED =========> The dropdown [" + nameCate + "] locator with option = ["
                    + nameCate + "] NOT EXIST or NOT CLICKABLE\n] " + ex.getMessage());
        }
    }

    public void selectDropDownListByValue(String locatorDrpBtn, String locatorDrpOption){
        try {
            Thread.sleep(2000);
            findElement(locatorDrpBtn).click();
            findElement(locatorDrpOption).click();
        } catch (NoSuchElementException | InterruptedException e) {
            Result.checkFail("Class Helper | Method selectDropDownListByValue | Exception desc : " + e.getMessage());
        }
    }
    public void selectDropDownListByValue(String locatorDrpBtn, String locatorDrpOption, String valueOption){
        try {
            findElement(locatorDrpBtn).click();
            List<WebElement> els = findElements(locatorDrpOption);
            for (WebElement element : els)
                if (element.getText().equalsIgnoreCase(valueOption)) {
                    element.click();
                    break;
                }
            System.out.println("Select main categories " + valueOption);

        } catch (NoSuchElementException e) {
            Result.checkFail("Class Helper | Method selectDropDownListByValue | Exception desc : " + e.getMessage());
        }
    }

    *//**
     * @param locator
     * @return
     *//*
    public String selectRandomOptionVisible (String locator) {
        try {
            List<WebElement> els = findElements(locator);
            int cate = getRandomIndex(els.size() - 1);
            String namecate = els.get(cate).getText();
            for (WebElement element : els) {
                element.getText().equalsIgnoreCase(namecate);
                element.click();
                break;
            }
        } catch (Exception e){
            Result.checkFail("Class Helper | Method selectRandomOptionVisible | Exception desc : " + e.getMessage());
        }
        return locator;
    }

    public void hover(String locator) {
        Actions action = new Actions(driver);
        WebElement e = findElement(locator);
        try {
            action.moveToElement(e).build().perform();
        } catch (NoSuchElementException ex) {
            addLog("\n[######### HOVER ELEMENT FAILED =========> The element NOT EXIST or NOT CLICKABLE\n] "
                    + ex.getMessage());
        }
    }

    public void focusElementFieldAndInputField(String locator, String data) {
        try {
            WebElement element = findElement(locator);
            element.equals(driver.switchTo().activeElement());
            element.sendKeys(data);
        } catch (Exception ex) {
            addLog("\n [##### FOCUS ELEMENT FAILED =======> The element NOT EXIST or NOT CLICKABLE\n]"
                    + ex.getMessage());
        }
    }

    public Boolean isBeecowDropdownPresent(String name) {
        Boolean isPresent = Boolean.FALSE;
        try {
            isPresent = findElements(String.format("xpath::.//div[contains(@class,'%s')]/button", name)).size() > 0;
            return isPresent;
        } catch (NoSuchElementException ex) {
            return isPresent;
        }
    }

    public void click_BeecowDropdownOptionInvisible(String locator) {
        WebElement element = findElement(locator);
        try {
            hover(locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].focus();", element);
            js.executeScript("arguments[0].click();", element);
        } catch (Exception ex) {
            Result.checkFail(
                    "Class Helper | Method click_BeecowDropdownOptionInvisible | Exception desc : " + ex.getMessage());
        }

    }
    public void switchToPopUp( WebDriver driver) {
        String subWindowHandler = null;
        final Set<String> handles = driver.getWindowHandles(); // get all window
        // handles
        final Iterator<String> iterator = handles.iterator();
        while (iterator.hasNext()) {
            subWindowHandler = iterator.next();
        }
        driver.switchTo().window(subWindowHandler);
    }

    public void switchtoWindowBytitle(String title){
        Set<String> allWindows = driver.getWindowHandles();
        for(String runWindows : allWindows){
            driver.switchTo().window(runWindows);
            String currentWin = driver.getTitle();
            if(currentWin.equals(title)){
                break;
            }
        }
    }
    public void switchToChildWindowByID(String parent) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String runWindow : allWindows) {
            if (!runWindow.equals(parent)) {
                driver.switchTo().window(runWindow);
                break;
            }
        }
    }
    public void closeOtherWindows(String parentPage) {
        final Set<String> set = driver.getWindowHandles();
        set.remove(parentPage);
        assert set.size() == 1;
        driver.switchTo().window((String) set.toArray()[0]);
        driver.close();
        driver.switchTo().window(parentPage);
    }

    public void closeAllWithoutParentWindows(String parentWindow) {
        Set <String> allWindows = driver.getWindowHandles();
        for (String runWindows : allWindows) {
            if (!runWindows.equals(parentWindow)) {
                driver.switchTo().window(runWindows);
                driver.close();
            }
        }
        driver.switchTo().window(parentWindow);
    }

    public void upload_File(String locator, String fileName) {
        String path = System.getProperty("user.dir");
        try {
            WebElement element = findElement(locator);
            element.sendKeys(path.concat(PATH_FILES).concat(File.separator).concat(fileName));
        } catch (Exception ex) {
            Result.checkFail(
                    "Class Helper | Method upload_File | LOCATOR UPLOAD must be <input and [@type=file]> \nException desc : "
                            + ex.getMessage());
        }
    }

    public String get_CurrentUrl() {
        String currentUrl = "";
        try {
            currentUrl = driver.getCurrentUrl();
            return currentUrl;
        } catch (Exception ex) {
            Result.checkFail("Class Helper | Method get_CurrentUrl | Exception desc : " + ex.getMessage());
        }
        return currentUrl;
    }
    public String getDataExpectedResult(String data, String verifyTag) {
        data = data.substring(data.indexOf("<"+verifyTag+">")+verifyTag.length()+2,data.indexOf("</"+verifyTag+">"));
        System.out.println("Data verify is: "+data+"\n==============");
        return data;
    }

    public String convertArrayToString(String... arrStr){
        return Stream.of(arrStr)
                .collect(Collectors.joining(",","",""));
    }
    public String convertListToString(List<String> stringList){
        return String.join(",", stringList);
    }
    public int formatPriceStringToInt(String price){
        String str = price.replaceAll("[^0-9]", "");
        return Integer.parseInt(str);
    }
    public List<String> removeDuplicate(String... arr){
        List<String> listWithDuplicates = new ArrayList<>();
        listWithDuplicates.addAll(Arrays.asList(arr));
        List<String> listWithoutDuplicates = listWithDuplicates.stream()
                .distinct()
                .collect(Collectors.toList());
        return listWithoutDuplicates;
    }*/

}

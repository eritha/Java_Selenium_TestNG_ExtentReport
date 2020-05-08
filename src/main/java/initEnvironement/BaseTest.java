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
package initEnvironement;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utility.*;
import utility.helper.DateTime;
import utility.helper.Helper;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static initEnvironement.Constants.sTestCases;

public class BaseTest {
    public static WebDriver driver;
    public static String currentDir;


    public static String reportFolder = "";
    public ExtentTest test;

    public ScreenshotUtils getScreenshot() {
        return new ScreenshotUtils(driver);
    }

    public Helper getHelper() {
        return new Helper(driver);
    }

    protected static ExtentReports extent;
    final String reportPath = "./Extent.html";

    protected ArrayList<String> sFlagData = new ArrayList<>();


    @BeforeSuite
    public void beforeSuite() {
        //Main Directory of the project
        currentDir = System.getProperty("user.dir");
        PropertiesUtils.readGlobalPropertyFile();
        extentReport();
    }


    @AfterSuite
    protected void afterSuite() {
//        extent.endTest(test);
        extent.flush();
//        extent.close();
    }

    public void setUp(String url) {
        openBrowser(url);
    }

    public void afterClass() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void openBrowser(String url) {
        try {
            if (PropertiesUtils.getBrowserPro.equalsIgnoreCase(BrowserType.FIREFOX)) {
                FirefoxDriverManager.getInstance().version(PropertiesUtils.FFVersionPro).setup();
                driver = new FirefoxDriver();
                LogUtils.info("Firefox browser started");
            } else if (PropertiesUtils.getBrowserPro.equalsIgnoreCase(BrowserType.IE)) {
                InternetExplorerDriverManager.getInstance().version(PropertiesUtils.IEVersionPro).setup();
                driver = new InternetExplorerDriver();
                LogUtils.info("IE browser started");
            } else if (PropertiesUtils.getBrowserPro.equalsIgnoreCase(BrowserType.CHROME)) {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-notifications");
                options.addArguments("--start-maximized");
                ChromeDriverManager.getInstance().version(PropertiesUtils.ChromeVersionPro).setup();
                driver = new ChromeDriver(options);
                LogUtils.info("Chrome browser started");
            }
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
//            driver.manage().window().maximize();
            driver.manage().window().fullscreen();
            driver.get(url);
        } catch (Exception e) {
            Result.checkFail("Class BaseTest | Method openBrowser | Exception desc : " + e.getMessage());
        }
    }

    protected void screenShotAfterMethod(String className, String fileName) {
        try {
            if (Result.bSkip) {
                if (Result.bResult) {
                    getScreenshot().takeScreenshot(className, fileName);
                    Reporter.log(Constants.sScreenShot_Path);
                    test.log(LogStatus.PASS, "<p style=\"color: blue; font-size: medium\">" + sTestCases + "</p>"
                            + "<p style=\"color: green; font-size: medium\">This case is PASSED\n"
                            + " >>> [<a href=\"" + Constants.sScreenShot_Path + "\">Click Here</a>] <<< to see full the screenshot" +
                            "<br><img src=\"" + Constants.sScreenShot_Path + "\" height='900' width='1700' style='border:1px solid green'></p>");
                    Reporter.log("<p style=\"color: green; font-size: medium\">Your Test is Passed\n" +
                            " >>> [<a href=\"" + Constants.sScreenShot_Path + "\">Click Here</a>] <<< to see full the screenshot" +
                            "<br><img src=\"" + Constants.sScreenShot_Path + "\" height='1500' width='2200' style='border:1px solid green'></p>");
                } else {
                    if (PropertiesUtils.setScreenshotPro.equals("y")) {
                        getScreenshot().takeScreenshot(className, fileName);
                    }
                    String[] strs = Constants.sVideoRecord_Path.split("/");
                    String vFolder = strs[strs.length-1];
                    Reporter.log(Constants.sScreenShot_Path + "</br>", true);
                    test.log(LogStatus.FAIL, "<p style=\"color: blue; font-size: medium\">" + sTestCases + "</p>"
                            + "<p style=\"color: red; font-size: medium\">This case is FAILED\n"
                            + " >>> [<a href=\"" + Constants.sScreenShot_Path + "\">Click Here</a>] <<< to see full the screenshot" +
                            "<br><img src=\"" + Constants.sScreenShot_Path + "\" height='700' width='1500' style='border:3px solid red'></p></br><p> ---------- </p>" +
                            "<br><p style='color: red; font-size: medium'>Check the video record >>> [<a href=\"" + vFolder.replace("[","").replace("]","") + "\">Click Here</a>] <<< to see more" +
                            "<br><video width=\"320\" height=\"240\" controls>\n" +
                            "      <source src=\""+ vFolder.replace("[","").replace("]","") +"\" type=video/avi>\n" +
                            "      <source src=\""+ vFolder.replace("[","").replace("]","") +"\" type=video/mp4>\n" +
                            "    </video>");
                    Reporter.log("<p style=\"color: red; font-size: medium\">Your Test is Failed\n" +
                            " >>> [<a href=\"" + Constants.sScreenShot_Path + "\">Click Here</a>] <<< to see full the screenshot" +
                            "<br><img src=\"" + Constants.sScreenShot_Path + "\" height='700' width='1500' style='border:3px solid red'></p>" +
                            "<br><p style='color: red; font-size: medium'>Check the video record >>> [<a href=\"" + vFolder.replace("[","").replace("]","") + "\">Click Here</a>] <<< to see more" +
                            "<br><video width=\"320\" height=\"240\" controls>\n" +
                            "      <source src=\""+ vFolder.replace("[","").replace("]","") +"\" type=video/avi>\n" +
                            "      <source src=\""+ vFolder.replace("[","").replace("]","") +"\" type=video/mp4>\n" +
                            "    </video>");
                }// HANDLE VIDEO RECORD LINK
            }
        } catch (Exception ex) {
            LogUtils.addLog("<font color='red'> " + ex.getMessage() + " </font></br>");
        }
    }

    protected void startRecordVideoAfterMethod(String className, String testCaseName) {
        try {
            if (PropertiesUtils.testRecorder.equalsIgnoreCase("yes")) {
                getScreenshot().startRecording(className, testCaseName);
            }
        } catch (Exception ex) {
            System.out.println("Can't start video recording for test case: " + testCaseName + "due to error " + ex);
        }
    }

    protected void stopRecordVideoAfterMethod() {
        try {
            //if this TCs does not skip, will close record
            if (Result.bSkip) {
                if (PropertiesUtils.testRecorder.equalsIgnoreCase("yes")) {
                    getScreenshot().stopRecordingVideo(Result.bResult);
                }
            }
        } catch (Exception ex) {
            System.out.println("Can't stop video recording: " + ex);
        }
    }

    private void extentReport() {
        reportFolder = DateTime.getCurrentTime("yyyy_MM_dd_HH_mm_ss");
        String reportPath = "test-reports/" + reportFolder;
        FolderFile.createMutilFolder(reportPath);
        extent = ExtentManager.getReporter("test-reports/" + reportFolder + "/ExtentReport.html");
    }
}

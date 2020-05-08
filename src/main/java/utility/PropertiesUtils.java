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
package utility;

import initEnvironement.Constants;
import org.testng.internal.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {
    public static Properties globalPro;

    public static String FFVersionPro;
    public static String ChromeVersionPro;
    public static String IEVersionPro;

    public static String environmentTest;
    public static String getBrowserPro;
    public static String accountEmail;
    public static String urlWebsiteSignupPro;
    public static String urlWebsiteLoginPro;

    public static String keyLanguagePro;
    public static String setScreenshotPro;

    public static String testRecorder;


    public static Properties initProperties(String propertyFile) {
        try {

            Properties pro = new Properties();
            InputStream resourceAsStream = Utils.class.getClassLoader().getResourceAsStream(propertyFile);
            // InputStream resourceAsStream = new
            // FileInputStream("/Users/hangpham/Documents/company/Fantastic/src/resources/Global.properties");
            pro.load(resourceAsStream);
            return pro;
        } catch (IOException e) {
            e.printStackTrace();
        }
        // }catch (Exception ex){
        // ex.printStackTrace();
        // }
        return null;
    }

    public static String getPropertyValue(Properties pro, String propertyName) {
        if (pro == null || propertyName == null) {
            return null;// new Exception("properties==null || propertyName==null");
        }
        return pro.getProperty(propertyName);
    }

    public static void readGlobalPropertyFile() {
        Constants Constants = new Constants();
        globalPro = initProperties(Constants.GlobalPropertiesFile);
        getBrowserPro = getPropertyValue(globalPro, Constants.Get_Browser);

        FFVersionPro = getPropertyValue(globalPro, Constants.Firefox_version);
        ChromeVersionPro = getPropertyValue(globalPro, Constants.Chrome_version);
        IEVersionPro = getPropertyValue(globalPro, Constants.IE_version);

        keyLanguagePro = getPropertyValue(globalPro, Constants.Key_Language);
        setScreenshotPro = getPropertyValue(globalPro, Constants.Set_screenshot);

        accountEmail = getPropertyValue(globalPro, Constants.Email);
        environmentTest = getPropertyValue(globalPro, Constants.environment);
        urlWebsiteSignupPro = getPropertyValue(globalPro, environmentTest.concat(Constants.URL_Website_Signup));
        urlWebsiteLoginPro = getPropertyValue(globalPro, environmentTest.concat(Constants.URL_Website_Login));

        testRecorder = getPropertyValue(globalPro, Constants.testRecorder);
    }
}

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

import org.openqa.selenium.WebDriver;
import utility.helper.Helper;

public class CommonPage {
    public WebDriver driver;

    public CommonPage(WebDriver driver) {
        this.driver = driver;
    }

    public Helper getHelper() {
        return new Helper(driver);
    }

}

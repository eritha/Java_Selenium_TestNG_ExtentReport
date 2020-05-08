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

import java.io.File;

public class FolderFile {
    // E.g: createFolder(parentFolder/childFolder)
    // E.g: createFolder(C:\\parentFolder\\childFolder)
    public static boolean createFolder(String folderPath) {
        File file = new File(folderPath);
        if (!file.exists()) {
            return file.mkdir();
        } else {
            System.out.println("Directory was exist !");
            return false;
        }
    }

    // E.g: createMutilFolder(parentFolder/childFolder)
    // E.g: createMutilFolder(C:\\parentFolder\\childFolder)
    public static boolean createMutilFolder(String folderPath) {
        File file = new File(folderPath);
        if (!file.exists()) {
            return file.mkdirs();
        } else {
            System.out.println("Directory was exist !");
            return false;
        }
    }
}

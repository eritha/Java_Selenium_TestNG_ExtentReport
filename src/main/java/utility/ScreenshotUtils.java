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
import org.apache.commons.io.FileUtils;
import org.monte.media.Format;
import org.monte.media.FormatKeys.*;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import screenObjects.CommonPage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static initEnvironement.BaseTest.reportFolder;
import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class ScreenshotUtils extends CommonPage {
    private static ScreenRecorder screenRecorder;
    private static File file;

    public ScreenshotUtils(WebDriver driver) {
        super(driver);
    }

    /**
     * @param ClassNames
     * @param fileName
     * @return
     */
    public String takeScreenshot(String ClassNames, String fileName) {
        try {
            String sProjectPath = new File("test-reports/").getAbsolutePath().concat(File.separator);
            DateFormat dateFormat = new SimpleDateFormat("_yyyy_MM_dd_HH_mm_ss");
            //get current date time with Date()
            Date date = new Date();
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String fileScrShot = sProjectPath.concat(reportFolder).concat(File.separator).concat(ClassNames) + File.separator + fileName + "_" + dateFormat.format(date) + ".png";
            Constants.sScreenShot_Path = ClassNames + File.separator + fileName + "_" + dateFormat.format(date) + ".png";
            try {
                FileUtils.copyFile(scrFile, new File(fileScrShot));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.err.println(e);
            }

            LogUtils.info("Captured a screenshot to: " + fileScrShot);
            return fileScrShot;
        } catch (Exception e) {
            Result.checkFail("Class ScreenshotUtils | Method takeScreenshot | Exception desc : " + e.getMessage());
            return null;
        }
    }

    public void startRecording(String className, String fileName) {
        String sProjectPath = new File("test-reports/").getAbsolutePath().concat(File.separator);
//        String fileRecord = sProjectPath.concat("video").concat(File.separator).concat(className);
//		String pathFolder = System.getProperty("user.dir") + "/video/" + className;
        String fileRecord = sProjectPath.concat(reportFolder).concat(File.separator).concat("video").concat(File.separator).concat(className);
        file = new File(fileRecord);
        if (!file.exists()) {
            System.out.println("Create folder: " + fileRecord);
            file.mkdirs();
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        Rectangle captureSize = new Rectangle(0, 0, width, height);
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
                .getDefaultConfiguration();
        try {
            screenRecorder = new RecordTest(gc, captureSize,
                    new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                            CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey,
                            Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
                    null, file, fileName);
            screenRecorder.start();
//            Constants.sVideoRecord_Path = sProjectPath.concat("video").concat(File.separator).concat(className).concat(File.separator).concat(this.screenRecorder.getCreatedMovieFiles().toString());
        } catch (IOException | AWTException e) {
            e.printStackTrace();
        }
    }

    public void stopRecording() throws Exception {
        screenRecorder.stop();
        if (Result.bResult) {
            List<File> fileCreated = screenRecorder.getCreatedMovieFiles();
            for (File e : fileCreated) {
                //delete video
                e.delete();
            }
        }
    }

    public void stopRecordingVideo(boolean result) {
        try {
            screenRecorder.stop();
            Constants.sVideoRecord_Path = screenRecorder.getCreatedMovieFiles().toString();
            if (result) {
                List<File> fileCreated = screenRecorder.getCreatedMovieFiles();
                System.out.println(fileCreated.toString());
                for (File e : fileCreated) {
                    e.delete();
                    //delete folder
//                    file.delete();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

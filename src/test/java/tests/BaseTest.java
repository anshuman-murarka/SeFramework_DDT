package tests;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import utils.logs.Log;

import utils.properties.ReadProperties;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

public class BaseTest {
    public WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }


    @BeforeClass
    public void classLevelSetup() {
        Log.info("class level setup");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @BeforeMethod
    public void methodLevelSetup(Method method) {
        Log.info("method level setup: "+method.getName());
        String url = ReadProperties.readProperty("url");
        driver.get(url);
    }

    @AfterClass
    public void classLevelTearDown() {
        Log.info("class level teardown");
        driver.close();
        driver.quit();
    }

    public String takeScreenshot(WebDriver driver, String testName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File srcFile = ts.getScreenshotAs(OutputType.FILE);
        testName = testName + "_" + String.valueOf(System.currentTimeMillis());
        String destPath = System.getProperty("user.dir") + "\\reports\\" + testName + ".png";
        File destFile = new File(destPath);
        FileUtils.copyFile(srcFile, destFile);
        return destPath;
    }

}

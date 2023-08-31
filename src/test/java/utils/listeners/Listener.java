package utils.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import tests.BaseTest;
import utils.excelutils.ExcelUtils;
import utils.extentreports.ExtentManager;
import utils.logs.Log;

import java.io.IOException;
import java.util.HashMap;

public class Listener extends BaseTest implements ITestListener {

    ExtentTest test;
    String methodName;

    private String getMethodName(ITestResult result) {
        return result.getMethod().getMethodName();
    }

    @Override
    public void onTestStart(ITestResult result) {
        Log.info("test start: "+result.getMethod().getMethodName());
        methodName = getMethodName(result);
        Object[] params = result.getParameters();
        String tcID = ((HashMap<String,String>)params[0]).get("TCID");
        test = ExtentManager.getReport().createTest(tcID+"_"+methodName, result.getMethod().getDescription());
        int rowNumTCID = ExcelUtils.getRowNumForTCID(tcID);
        ExcelUtils.setRowNum(rowNumTCID);
        int colNumStatus = ExcelUtils.getColumnNumForColumnName("status");
        ExcelUtils.setColNum(colNumStatus);
        StringBuilder paramStr = new StringBuilder();
        paramStr.append("Test Data: ");
        for (Object param : params) {
            paramStr.append(param).append(" ");
        }
        test.info(String.valueOf(paramStr));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Log.info("test PASS: "+methodName);
        test.log(Status.PASS, "Test Passed");
        ExcelUtils.setExcelCellData("PASS", ExcelUtils.getRowNum(), ExcelUtils.getColNum());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Log.info("test FAIL: "+methodName);
        test.fail(result.getThrowable());
        Object testClass = result.getInstance();
        WebDriver driver = ((BaseTest) testClass).getDriver();
        try {
            String ss = takeScreenshot(driver, methodName);
            test.addScreenCaptureFromPath(ss);
        } catch (IOException e) {
            test.log(Status.FAIL, e.getMessage());
        }
        ExcelUtils.setExcelCellData("FAIL", ExcelUtils.getRowNum(), ExcelUtils.getColNum());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onStart(ITestContext context) {
        context.setAttribute("WebDriver", this.driver);
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.getReport().flush();
    }
}

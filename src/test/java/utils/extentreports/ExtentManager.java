package utils.extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    public static final ExtentReports report = new ExtentReports();

    public static ExtentReports getReport() {
        String currentWorkingDir = System.getProperty("user.dir");
        ExtentSparkReporter reporter = new ExtentSparkReporter(currentWorkingDir + "\\reports\\index.html");
        reporter.config().setDocumentTitle("SeFramework_DDT");
        reporter.config().setReportName("Swag Labs Automated Test_SeFramework_DDT");
        reporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy hh:mm a '('zzz')'");
        report.attachReporter(reporter);
        report.setSystemInfo("Author", "Anshuman");
        return report;
    }
}

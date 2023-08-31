package tests;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.testng.Assert;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.excelutils.ExcelUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class LoginTests extends BaseTest {

    @BeforeTest
    public void setup() {
        ExcelUtils.setExcelSheet("LoginTests");
    }


    @Test(dataProvider = "getTestData", description = "Verify login into app with valid username & password")
    public void testLogin_ValidLogin(HashMap<String, String> data) {

        LoginPage lp = new LoginPage(driver);
        lp.enterUsername(data.get("username")).enterPassword(data.get("password")).clickLogin();
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, "Swag Labs");

    }

    @Test(dataProvider = "getTestData", description = "Verify login error message for a locked out user")
    public void testLogin_InvalidLogin(HashMap<String, String> data) {

        LoginPage lp = new LoginPage(driver);
        String actualErrorMsg = lp.enterUsername(data.get("username")).enterPassword(data.get("password")).clickLogin().readErrorMessage();
        Assert.assertEquals(actualErrorMsg, "Epi sadface: Sorry, this user has been locked out.");

    }

    @DataProvider
    private Object[][] getTestData(ITestNGMethod method) {

        String testName = method.getMethodName();
        XSSFRow headerRow = ExcelUtils.getExcelRow(0);
        int maxRows = ExcelUtils.getLastRowNum();
        int maxCols = ExcelUtils.getLastColNum(); // index 1
        int count = 0;
        List<HashMap<String, String>> testDataListMap = new ArrayList<>();
        for (int i = 0; i < maxRows; i++) {
            HashMap<String, String> rowMap = new HashMap<>();
            if (Objects.equals(ExcelUtils.getCellData(i + 1, 1), testName)) {
                count++;
                XSSFRow dataRow = ExcelUtils.getExcelRow(i + 1);
                for (int j = 0; j < maxCols - 1; j++) {
                    assert headerRow != null;
                    assert dataRow != null;
                    rowMap.put(headerRow.getCell(j).getStringCellValue(), dataRow.getCell(j).getStringCellValue());
                }
                testDataListMap.add(rowMap);
            }
        }

        Object[][] testData = new Object[count][1];
        for (int i = 0; i < count; i++) {
            testData[i][0] = testDataListMap.get(i);

        }

//        Object[][] testData = testDataListMap.stream().map(hashMap -> new Object[]{hashMap}).toArray(Object[][]::new);
        return testData;
    }

}

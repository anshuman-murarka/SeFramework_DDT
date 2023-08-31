package utils.excelutils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {

    public static final String currWorkingDir = System.getProperty("user.dir");
    public static String excelPath = null;
    public static XSSFWorkbook workbook;
    public static XSSFSheet sheet;
    public static XSSFRow row;
    public static XSSFCell cell;
    public static int rowNum;
    public static int colNum;
    public static int lastRowNum;
    public static int lastColNum;

    public static int getRowNum() {
        return rowNum;
    }

    public static void setRowNum(int rownum) {
        rowNum = rownum;
    }

    public static int getColNum() {
        return colNum;
    }

    public static void setColNum(int colnum) {
        colNum = colnum;
    }

    public static void setExcelSheet(String sheetName) {
        excelPath = currWorkingDir + "\\src\\test\\resources\\testdata\\";
        try (FileInputStream fis = new FileInputStream(excelPath + "testdata.xlsx")) {
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCellData(int rowNum, int colNum) {
        try {
            cell = sheet.getRow(rowNum).getCell(colNum);
            DataFormatter formatter = new DataFormatter();
            return formatter.formatCellValue(cell);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static XSSFRow getExcelRow(int rowNum) {
        try {
            row = sheet.getRow(rowNum);
            return row;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setExcelCellData(String value, int rowNum, int colNum) {
        try {
            row = getExcelRow(rowNum);
            assert row != null;
            cell = row.getCell(colNum);
            if (cell == null) {
                cell = row.createCell(colNum);
                cell.setCellValue(value);
            } else {
                cell.setCellValue(value);
            }

            FileOutputStream fos = new FileOutputStream(excelPath + "testdata.xlsx");
            workbook.write(fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getLastRowNum() {
        return sheet.getLastRowNum();
    }

    public static int getLastColNum() {
        return sheet.getRow(0).getLastCellNum();
    }

    public static int getColumnNumForColumnName(String colName) {
        XSSFRow row = sheet.getRow(0);
        for (int i = 0; i <= row.getLastCellNum() - 1; i++) {
            if (row.getCell(i).getStringCellValue().equals(colName))
                return i;
        }
        return -1;
    }

    public static int getRowNumForTCID(String TCID) {
        lastRowNum = sheet.getLastRowNum();
        lastColNum = sheet.getRow(0).getLastCellNum();
        int col = getColumnNumForColumnName("TCID");
        if (col != -1) {
            for (int i = 1; i <= lastRowNum; i++) {
                XSSFCell cell = sheet.getRow(i).getCell(col);
                if (cell.getStringCellValue().equals(TCID)) {
                    return i;
                }
            }
        }
        return -1;
    }

}

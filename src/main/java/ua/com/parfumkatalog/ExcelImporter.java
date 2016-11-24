package ua.com.parfumkatalog;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author <a href="mailto:dmytro.barabash@playtech.com"> Dmytro Barabash</a> 2013-09-06 15:45
 */
public class ExcelImporter {

    private static final Logger LOGGER = Logger.getLogger(ExcelImporter.class);

    /**
     * Read the first sheet from the file
     *
     * @param fileName where to read from
     * @return list of rows (every row is list of {@code HSSFCell})
     */
    public static List<List<Cell>> importExcelSheet(String fileName) {
        List<List<Cell>> result = new ArrayList<List<Cell>>();
        File file = new File(fileName);
        if (!file.exists()) {
            file = new File(fileName + "x");
            if (!file.exists()) {
                LOGGER.error("File '" + fileName + "[x]' not found");
                return result;
            }
            fileName = fileName + "x";

            try {
                Workbook workBook = WorkbookFactory.create(new FileInputStream(fileName));
                Sheet sheet = workBook.getSheetAt(0);
                Iterator rowIterator = sheet.rowIterator();

                while (rowIterator.hasNext()) {
                    XSSFRow row = (XSSFRow) rowIterator.next();
                    Iterator cellIterator = row.cellIterator();
                    List<Cell> cellStoreVector = new ArrayList<Cell>();

                    while (cellIterator.hasNext()) {
                        Cell cell = (XSSFCell) cellIterator.next();
                        cellStoreVector.add(cell);
                    }
                    result.add(cellStoreVector);
                }
            } catch (Exception ex) {
                LOGGER.error("Couldn't parse file '" + fileName + "'", ex);
            }
            return result;
        }
        try {
            Workbook workBook = WorkbookFactory.create(new FileInputStream(fileName));
            Sheet sheet = workBook.getSheetAt(0);
            Iterator rowIterator = sheet.rowIterator();

            while (rowIterator.hasNext()) {
                HSSFRow row = (HSSFRow) rowIterator.next();
                Iterator cellIterator = row.cellIterator();
                List<Cell> cellStoreVector = new ArrayList<Cell>();

                while (cellIterator.hasNext()) {
                    Cell cell = (HSSFCell) cellIterator.next();
                    cellStoreVector.add(cell);
                }
                result.add(cellStoreVector);
            }
        } catch (Exception ex) {
            LOGGER.error("Couldn't parse file '" + fileName + "'", ex);
        }
        return result;
    }

}

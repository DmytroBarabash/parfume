package ua.com.parfumkatalog;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

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
     * @param fileName where to read from
     * @return list of rows (every row is list of {@code HSSFCell})
     */
    public static List<List<HSSFCell>> importExcelSheet(String fileName) {
        List<List<HSSFCell>> result = new ArrayList<List<HSSFCell>>();
        try {
            Workbook workBook = WorkbookFactory.create(new FileInputStream(fileName));
            Sheet sheet = workBook.getSheetAt(0);
            Iterator rowIterator = sheet.rowIterator();

            while (rowIterator.hasNext()) {
                HSSFRow row = (HSSFRow) rowIterator.next();
                Iterator cellIterator = row.cellIterator();
                List<HSSFCell> cellStoreVector = new ArrayList<HSSFCell>();

                while (cellIterator.hasNext()) {
                    HSSFCell cell = (HSSFCell) cellIterator.next();
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

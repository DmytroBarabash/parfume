package ua.com.parfumkatalog;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

/**
 * @author <a href="mailto:d.barabash@gmail.com"> Dmytro Barabash</a> 2014-02-08 23:48
 */
public class Util {

    public static String readCode(Cell cell) {
        if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            return String.valueOf(Math.round(cell.getNumericCellValue()));
        }
        if (cell.getCellTypeEnum() == CellType.STRING) {
            return cell.getStringCellValue();
        }
        return null;
    }

}

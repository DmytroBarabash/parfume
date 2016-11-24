package ua.com.parfumkatalog;

import org.apache.poi.ss.usermodel.Cell;

/**
 * @author <a href="mailto:d.barabash@gmail.com"> Dmytro Barabash</a> 2014-02-08 23:48
 */
public class Util {

    public static String readCode(Cell cell) {
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return String.valueOf(Math.round(cell.getNumericCellValue()));
        }
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        }
        return null;
    }

}

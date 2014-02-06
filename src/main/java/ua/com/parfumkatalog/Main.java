package ua.com.parfumkatalog;

import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;

/**
 * @author <a href="mailto:dmytro.barabash@playtech.com"> Dmytro Barabash</a> 2013-09-06 15:42
 */
public class Main {

	public static void main(String[] args) {
		ExcelImporter excelImporter = new ExcelImporter();
		List<List<HSSFCell>> sheet = excelImporter.importExcelSheet("d:\\000\\Prices_Junction\\Поставщик 2.xls");
		SheetStructure sheetStructure;
		int i = 0;
		for (List<HSSFCell> row : sheet) {
			if (i == 0) {
				sheetStructure = obtainSheetStructure(row);
			}
			System.out.print(i++ + ": ");
			int j = 0;
			for (HSSFCell cell : row) {
				Product product = new Product();
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					product.setCode(String.valueOf(Math.round(cell.getNumericCellValue())));
				} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					product.setCode(cell.getStringCellValue());
				}
				System.out.print(cell.toString() + '|');
				j++;
			}
			System.out.println("\n######################################################");
		}
	}

	private static SheetStructure obtainSheetStructure(List<HSSFCell> row) {
        SheetStructure sheetStructure = new SheetStructure();
        int i = 0;
        for (HSSFCell cell : row) {
           if (cell.getStringCellValue().equalsIgnoreCase("code")) {
               sheetStructure.setPropertyMapping(i, ProductProperties.CODE);
           }   else if (cell.getStringCellValue().equalsIgnoreCase("category")){
               sheetStructure.setPropertyMapping(i, ProductProperties.CODE);
           }
        }
        return sheetStructure;
    }
}

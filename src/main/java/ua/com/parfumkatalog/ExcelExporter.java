package ua.com.parfumkatalog;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Dmytro Barabash
 *         2015-09-26 22:21
 */
public class ExcelExporter {

    public static void export(List<Product> products, String fileName) throws IOException {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet();
        int i = 0;
        SheetStructure sheetStructure = new SheetStructure("2,9,4,5,6,7,8,3,1");
        Row head = sheet.createRow(i++);
        for (Map.Entry<Integer, ProductProperty> e: sheetStructure.getColumns().entrySet()) {
            Cell cell = head.createCell(e.getKey(), Cell.CELL_TYPE_STRING);
            cell.setCellValue(e.getValue().name());
        }
        ProductConverter productConverter = new ProductConverter(sheetStructure);
        for (Product product : products) {
            Row row = sheet.createRow(i++);
            productConverter.fromProduct(product, row);
        }

        FileOutputStream fileOut = new FileOutputStream(fileName);
        wb.write(fileOut);
        fileOut.close();
    }
}

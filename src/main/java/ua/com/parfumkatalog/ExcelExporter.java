package ua.com.parfumkatalog;

import com.google.common.collect.ImmutableMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Dmytro Barabash
 *         2015-09-26 22:21
 */
public class ExcelExporter {

    public static void export(List<Product> products, String fileName) throws IOException {
        ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("ru", "RU"));
        //ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en", "EN"));

        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet();
        int i = 0;
        //CODE, CATEGORY, NAME, GENDER, DESCRIPTION, VOLUME, PRICE, SUPPLIER, SUPER_CODE
        Map<Integer, ProductProperty> columns = ImmutableMap.<Integer, ProductProperty>builder()
                .put(0, ProductProperty.SUPER_CODE)
                .put(1, ProductProperty.CODE)
                .put(2, ProductProperty.SUPPLIER)
                .put(3, ProductProperty.CATEGORY)
                .put(4, ProductProperty.NAME)
                .put(5, ProductProperty.PRICE)
                .put(6, ProductProperty.GENDER)
                .put(7, ProductProperty.DESCRIPTION)
                .put(8, ProductProperty.VOLUME)
                .build();
        Row head = sheet.createRow(i++);
        for (Map.Entry<Integer, ProductProperty> e : columns.entrySet()) {
            Cell cell = head.createCell(e.getKey(), Cell.CELL_TYPE_STRING);
            cell.setCellValue(messages.getString(Product.class.getName() + "." + e.getValue().name()));
        }
        for (Product product : products) {
            Row row = sheet.createRow(i++);
            fromProduct(product, row, columns);
        }

        FileOutputStream fileOut = new FileOutputStream(fileName);
        wb.write(fileOut);
        fileOut.close();
    }

    private static void fromProduct(Product product, Row row, Map<Integer, ProductProperty> columns) {
        for (Map.Entry<Integer, ProductProperty> entry : columns.entrySet()) {
            Cell cell;
            switch (entry.getValue()) {
                case CODE:
                    cell = row.createCell(entry.getKey(), Cell.CELL_TYPE_STRING);
                    cell.setCellValue(product.getCode());
                    break;
                case CATEGORY:
                    cell = row.createCell(entry.getKey(), Cell.CELL_TYPE_STRING);
                    cell.setCellValue(product.getCategory());
                    break;
                case NAME:
                    cell = row.createCell(entry.getKey(), Cell.CELL_TYPE_STRING);
                    cell.setCellValue(product.getName());
                    break;
                case GENDER:
                    Gender gender = product.getGender();
                    if (gender != Gender.UNKNOWN) {
                        cell = row.createCell(entry.getKey(), Cell.CELL_TYPE_STRING);
                        cell.setCellValue(product.getGender().name());
                    }
                    break;
                case DESCRIPTION:
                    cell = row.createCell(entry.getKey(), Cell.CELL_TYPE_STRING);
                    cell.setCellValue(product.getDescription());
                    break;
                case PRICE:
                    cell = row.createCell(entry.getKey(), Cell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(product.getPrice().doubleValue());
                    break;
                case VOLUME:
                    Integer volume = product.getVolume();
                    if (volume != null) {
                        cell = row.createCell(entry.getKey(), Cell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(volume);
                    }
                    break;
                case SUPPLIER:
                    cell = row.createCell(entry.getKey(), Cell.CELL_TYPE_STRING);
                    cell.setCellValue(product.getSupplier());
                    break;
                case SUPER_CODE:
                    cell = row.createCell(entry.getKey(), Cell.CELL_TYPE_STRING);
                    cell.setCellValue(product.getSuperCode());
                    break;
                default:
                    break;
            }
        }
    }

}

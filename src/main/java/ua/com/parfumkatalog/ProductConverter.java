package ua.com.parfumkatalog;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:dmytro.barabash@playtech.com"> Dmytro Barabash</a> 2014-02-08 02:14
 */
public class ProductConverter {

    private final SheetStructure sheetStructure;

    public ProductConverter(SheetStructure sheetStructure) {
        this.sheetStructure = sheetStructure;
    }

    public Product toProduct(List<HSSFCell> row) {
        int j = -1;
        Product product = new Product();
        for (HSSFCell cell : row) {
            j++;
            ProductProperty productProperty = sheetStructure.getProperty(j);
            if (productProperty == null) {
                continue;
            }
            switch (productProperty) {
                case CODE:
                    product.setCode(Util.readCode(cell));
                    break;
                case CATEGORY:
                    product.setCategory(cell.getStringCellValue());
                    break;
                case NAME:
                    product.setName(cell.getStringCellValue());
                    break;
                case GENDER:
                    product.setGender(cell.getStringCellValue());
                    break;
                case DESCRIPTION:
                    product.setDescription(cell.getStringCellValue());
                    break;
                case PRICE:
                    try {
                        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            product.setPrice(BigDecimal.valueOf(cell.getNumericCellValue()));
                        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                            product.setPrice(new BigDecimal(cell.getStringCellValue()));
                        }
                    } catch (NumberFormatException ex) {
                        // nothing to do
                    }
                    break;
                case VOLUME:
                    try {
                        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            product.setVolume(new Double(cell.getNumericCellValue()).intValue());
                        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                            product.setVolume(Integer.valueOf(cell.getStringCellValue()));
                        }
                    } catch (NumberFormatException ex) {
                        // nothing to do
                    }
                    break;
                default:
                    break;
            }
        }
        return product;
    }

    public void fromProduct(Product product, Row row) {
        for (Map.Entry<Integer, ProductProperty> entry : sheetStructure.getColumns().entrySet()) {
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
                    cell = row.createCell(entry.getKey(), Cell.CELL_TYPE_BOOLEAN);
                    cell.setCellValue(product.getGender().name());
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
                    cell = row.createCell(entry.getKey(), Cell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(product.getVolume());
                    break;
                case SUPPLIER:
                    cell = row.createCell(entry.getKey(), Cell.CELL_TYPE_STRING);
                    cell.setCellValue(product.getSupplier());
                    break;
                default:
                    break;
            }
        }
    }

}

package ua.com.parfumkatalog;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author <a href="mailto:dmytro.barabash@playtech.com"> Dmytro Barabash</a> 2014-02-08 02:14
 */
public class ProductBuilder {

    private final SheetStructure sheetStructure;

    public ProductBuilder(SheetStructure sheetStructure) {
        this.sheetStructure = sheetStructure;
    }

    public Product build(List<HSSFCell> row) {
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
                    product.setFemale(cell.getStringCellValue());
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
}

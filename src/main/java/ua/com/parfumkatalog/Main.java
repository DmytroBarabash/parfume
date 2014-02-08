package ua.com.parfumkatalog;

import org.apache.poi.hssf.usermodel.HSSFCell;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:dmytro.barabash@playtech.com"> Dmytro Barabash</a> 2013-09-06 15:42
 */
public class Main {

    public static void main(String[] args) {
        ExcelImporter excelImporter = new ExcelImporter();

        List<List<HSSFCell>> codeSheet = excelImporter.importExcelSheet("d:\\000\\Prices_Junction\\коды.xls");
        List<HSSFCell> supplierRow = codeSheet.get(0);
        List<HSSFCell> sheetStructureRow = codeSheet.get(1);
        List<Supplier> suppliers = new ArrayList<Supplier>();
        for (int k = 2; k < supplierRow.size(); k++) {
            HSSFCell cell = supplierRow.get(k);
            Supplier supplier = new Supplier();
            supplier.setName(cell.getStringCellValue());
            supplier.buildSheetStructure(sheetStructureRow.get(k).getStringCellValue());
            if (supplier.isValid()) {
                suppliers.add(supplier);
            }
        }

        for (Supplier supplier : suppliers) {
            List<List<HSSFCell>> sheet = excelImporter
                    .importExcelSheet("d:\\000\\Prices_Junction\\" + supplier.getName() + ".xls");
            System.out.println(supplier.getName());
            int i = 0;
            for (List<HSSFCell> row : sheet) {
                Product product = new ProductBuilder(supplier.getSheetStructure()).build(row);
                if (product.isValid()) {
                    System.out.println(i + ": " + product);
                }
                i++;
            }
        }

    }

    private static SheetStructure obtainSheetStructure() {
        SheetStructure s = new SheetStructure();
        s.setPropertyMapping(0, ProductProperty.CODE);
        s.setPropertyMapping(1, ProductProperty.CATEGORY);
        s.setPropertyMapping(2, ProductProperty.NAME);
        s.setPropertyMapping(4, ProductProperty.GENDER);
        s.setPropertyMapping(5, ProductProperty.DESCRIPTION);
        s.setPropertyMapping(6, ProductProperty.VOLUME);
        s.setPropertyMapping(7, ProductProperty.PRICE);
        return s;
    }

    private static SheetStructure obtainSheetStructure1() {
        SheetStructure s = new SheetStructure();
        s.setPropertyMapping(1, ProductProperty.CODE);
        s.setPropertyMapping(2, ProductProperty.NAME);
        s.setPropertyMapping(3, ProductProperty.GENDER);
        s.setPropertyMapping(4, ProductProperty.DESCRIPTION);
        s.setPropertyMapping(5, ProductProperty.VOLUME);
        s.setPropertyMapping(6, ProductProperty.PRICE);
        return s;
    }
}

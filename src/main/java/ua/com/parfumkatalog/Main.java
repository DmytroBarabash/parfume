package ua.com.parfumkatalog;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author <a href="mailto:dmytro.barabash@playtech.com"> Dmytro Barabash</a> 2013-09-06 15:42
 */
public class Main {

    private final static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.info("Start");
        ExcelImporter excelImporter = new ExcelImporter();

        File file = new File("xls/коды.xls");
        if (!file.exists()) {
            logger.error("File " + file + " not found");
            return;
        }
        List<List<HSSFCell>> codeSheet = excelImporter.importExcelSheet(file.getAbsolutePath());
        List<HSSFCell> supplierRow = codeSheet.get(0);
        List<HSSFCell> sheetStructureRow = codeSheet.get(1);
        List<Supplier> suppliers = new ArrayList<Supplier>();
        List<String> superCodes = new ArrayList<String>();
        for (int k = 2; k < supplierRow.size(); k++) {
            HSSFCell cell = supplierRow.get(k);
            Supplier supplier = new Supplier();
            supplier.setName(cell.getStringCellValue());
            supplier.buildSheetStructure(sheetStructureRow.get(k).getStringCellValue());
            if (supplier.isValid()) {
                suppliers.add(supplier);
            }
        }
        for (int m = 2; m < codeSheet.size(); m++) {
            List<HSSFCell> row = codeSheet.get(m);
            String code = Util.readCode(row.get(1));
            if (code != null && !code.isEmpty()) {
                superCodes.add(code);
                int i = 1;
                for (Supplier supplier : suppliers) {
                    i++;
                    String supplierCode = Util.readCode(row.get(i));
                    if (supplierCode != null && !supplierCode.isEmpty()) {
                        supplier.getCodes().put(supplierCode, code);
                    }
                }
            }
        }

        for (Supplier supplier : suppliers) {
            List<List<HSSFCell>> sheet = excelImporter
                    .importExcelSheet("d:\\000\\Prices_Junction\\" + supplier.getName() + ".xls");
            System.out.println(supplier.getName());
            System.out.println(supplier.getCodes());
            int i = 0;
            ProductBuilder builder = new ProductBuilder(supplier.getSheetStructure());
            for (List<HSSFCell> row : sheet) {
                Product product = builder.build(row);
                if (product.isValid()) {
                    String superCode = supplier.getCodes().get(product.getCode());
                    if (superCode != null && !superCode.isEmpty()) {
                        supplier.getProducts().put(superCode, product);
                        System.out.println(i + ": " + superCode + " " + product);
                    }
                }
                i++;
            }
        }

        for (String superCode : superCodes) {
            List<Product> sameProducts = new ArrayList<Product>();
            for (Supplier s : suppliers) {
                Product product = s.getProducts().get(superCode);
                if (product != null) {
                    sameProducts.add(product);
                }
            }
            Collections.sort(sameProducts, new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return o1.getPrice().compareTo(o2.getPrice());
                }
            });
            if (sameProducts.size() > 1) {
                System.out.println(sameProducts);
            }
        }

    }

}

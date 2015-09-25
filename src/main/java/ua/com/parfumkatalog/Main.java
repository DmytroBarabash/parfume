package ua.com.parfumkatalog;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;

import java.io.File;
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

        File file = new File("xls/коды.xls");
        if (!file.exists()) {
            logger.error("File " + file + " not found");
            return;
        }
        List<List<HSSFCell>> codeSheet = ExcelImporter.importExcelSheet(file.getAbsolutePath());
        Processor processor = new Processor();
        processor.setCodeSheet(codeSheet);
        processor.processSuppliers();

        processor.processSuperCodes();

        for (Supplier supplier : processor.getSuppliers()) {
            processor.processSupplier(supplier);
        }

        for (String superCode : processor.getSuperCodes()) {
            List<Product> sameProducts = processor.obtainSameProducts(superCode);
            if (sameProducts.size() > 1) {
                Collections.sort(sameProducts, new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        return o1.getPrice().compareTo(o2.getPrice());
                    }
                });
                System.out.println(sameProducts);
            }
        }

    }

}

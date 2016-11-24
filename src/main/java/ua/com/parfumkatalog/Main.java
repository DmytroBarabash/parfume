package ua.com.parfumkatalog;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author <a href="mailto:dmytro.barabash@playtech.com"> Dmytro Barabash</a> 2013-09-06 15:42
 */
public class Main {

    private final static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.info("Start");

        //Locale currentLocale = new Locale("en", "US");
        Locale currentLocale = new Locale("ru", "RU");

        ResourceBundle messages = ResourceBundle.getBundle("messages", currentLocale);
        System.out.println(messages.getString("ua.com.parfumkatalog.Product.CODE"));

        File file = new File("xls/коды.xls");
        if (!file.exists()) {
            logger.error("File " + file + " not found");
            return;
        }
        List<List<Cell>> codeSheet = ExcelImporter.importExcelSheet(file.getAbsolutePath());
        Processor processor = new Processor();
        processor.setCodeSheet(codeSheet);
        processor.processSuppliers();

        processor.processSuperCodes();

        for (Supplier supplier : processor.getSuppliers()) {
            processor.processSupplier(supplier);
        }

        List<Product> result = new ArrayList<Product>();
        for (String superCode : processor.getSuperCodes()) {
            List<Product> sameProducts = processor.obtainSameProducts(superCode);
            if (sameProducts.isEmpty()) {
                continue;
            }

            if (sameProducts.size() > 1) {
                Collections.sort(sameProducts, new Comparator<Product>() {
                    public int compare(Product o1, Product o2) {
                        return o1.getPrice().compareTo(o2.getPrice());
                    }
                });
            }
            result.add(sameProducts.get(0));
        }
        try {
            ExcelExporter.export(result, "xls/workbook.xls");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

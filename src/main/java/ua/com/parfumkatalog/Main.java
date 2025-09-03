package ua.com.parfumkatalog;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author <a href="mailto:dmytro.barabash@playtech.com"> Dmytro Barabash</a> 2013-09-06 15:42
 */
public class Main {

    private final static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.info("Start");

        logger.info("file.encoding: " + System.getProperty("file.encoding"));
        logger.info("defaultCharset: " + Charset.defaultCharset());
        logger.info("defaultLocale: " + Locale.getDefault());

        Locale currentLocale = new Locale("en", "US");
        //Locale currentLocale = new Locale("ru", "RU");
        ResourceBundle messages = ResourceBundle.getBundle("messages", currentLocale);
        System.out.println(messages.getString("ua.com.parfumkatalog.Product.CODE"));

        String codesFile = "xls/codes.xls";
        File file = new File(codesFile);
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
                sameProducts.sort(new Comparator<Product>() {
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

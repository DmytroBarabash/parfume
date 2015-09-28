package ua.com.parfumkatalog;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.omg.CORBA.ShortSeqHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmytro Barabash
 *         2015-09-25 23:54
 */
public class Processor {

    private final static Logger LOGGER = Logger.getLogger(Processor.class);
    private List<List<HSSFCell>> codeSheet;

    private List<HSSFCell> supplierRow;
    private List<HSSFCell> sheetStructureRow;
    private List<Supplier> suppliers;
    private List<String> superCodes;

    /**
     * Build list of suppliers and list of structures
     */
    public void processSuppliers() {
        supplierRow = codeSheet.get(0);
        sheetStructureRow = codeSheet.get(1);
        suppliers = new ArrayList<Supplier>();
        for (int k = 2; k < supplierRow.size(); k++) {
            HSSFCell cell = supplierRow.get(k);
            Supplier supplier = new Supplier();
            supplier.setName(cell.getStringCellValue());
            supplier.setSheetStructure(new SheetStructure(sheetStructureRow.get(k).getStringCellValue()));
            if (supplier.isValid()) {
                suppliers.add(supplier);
            }
        }
    }

    public List<List<HSSFCell>> getCodeSheet() {
        return codeSheet;
    }

    public void setCodeSheet(List<List<HSSFCell>> codeSheet) {
        this.codeSheet = codeSheet;
    }

    public List<Supplier> processSuperCodes() {
        superCodes = new ArrayList<String>();
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
        return suppliers;
    }

    public void processSupplier(Supplier supplier) {
        List<List<HSSFCell>> sheet = ExcelImporter
                .importExcelSheet("xls/" + supplier.getName() + ".xls");
        LOGGER.info("Supplier: " + supplier.getName());
        LOGGER.debug("Supplier codes: " + supplier.getCodes());
        int i = 0;
        ProductConverter builder = new ProductConverter(supplier.getSheetStructure());
        for (List<HSSFCell> row : sheet) {
            Product product = builder.toProduct(row);
            if (product.isValid()) {
                String superCode = supplier.getCodes().get(product.getCode());
                if (superCode != null && !superCode.isEmpty()) {
                    supplier.getProducts().put(superCode, product);
                    LOGGER.debug(i + ": " + superCode + " " + product);
                }
            }
            i++;
        }
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public List<String> getSuperCodes() {
        return superCodes;
    }

    public List<Product> obtainSameProducts(String superCode) {
        List<Product> sameProducts = new ArrayList<Product>();
        for (Supplier s : suppliers) {
            Product product = s.getProducts().get(superCode);
            if (product != null) {
                sameProducts.add(product);
            }
        }
        return sameProducts;
    }
}

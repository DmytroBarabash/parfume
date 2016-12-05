package ua.com.parfumkatalog;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dmytro Barabash
 *         2015-09-25 23:54
 */
public class Processor {

    private final static Logger LOGGER = Logger.getLogger(Processor.class);
    private List<List<Cell>> codeSheet;

    private List<Cell> supplierRow;
    private List<Cell> sheetStructureRow;
    private List<Supplier> suppliers;
    private List<String> superCodes;

    private String getStringValue(Cell cell, DataFormatter f) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            default:
                return f.formatCellValue(cell);
        }
    }

    /**
     * Build list of suppliers and list of structures
     */
    public void processSuppliers() {
        supplierRow = codeSheet.get(0);
        sheetStructureRow = codeSheet.get(1);
        suppliers = new ArrayList<Supplier>();
        DataFormatter f = new DataFormatter();
        for (int k = 2; k < supplierRow.size(); k++) {
            Cell cell = supplierRow.get(k);
            Supplier supplier = new Supplier();
            supplier.setName(getStringValue(cell, f));
            supplier.setColumnIndex(cell.getColumnIndex());
            supplier.setSheetStructure(new SheetStructure(getStringValue(sheetStructureRow.get(k), f)));
            if (supplier.isValid()) {
                suppliers.add(supplier);
            }
        }
    }

    public List<List<Cell>> getCodeSheet() {
        return codeSheet;
    }

    public void setCodeSheet(List<List<Cell>> codeSheet) {
        this.codeSheet = codeSheet;
    }

    public List<Supplier> processSuperCodes() {
        superCodes = new ArrayList<String>();
        Map<Integer, Supplier> supplierMap = new HashMap<Integer, Supplier>();
        for (Supplier supplier : suppliers) {
            supplierMap.put(supplier.getColumnIndex(), supplier);
        }
        for (int m = 2; m < codeSheet.size(); m++) {
            List<Cell> row = codeSheet.get(m);
            String superCode = null;
            for (Cell cell : row) {
                if (cell.getColumnIndex() == 0) {
                    // first column ignored
                    continue;
                }
                if (cell.getColumnIndex() == 1) {
                    superCode = Util.readCode(cell);
                    if (superCode != null && !superCode.isEmpty()) {
                        superCodes.add(superCode);
                    }
                    continue;
                }
                Supplier supplier = supplierMap.get(cell.getColumnIndex());
                if (supplier != null) {
                    String supplierCode = Util.readCode(cell);
                    if (supplierCode != null && !supplierCode.isEmpty()) {
                        supplier.getCodes().put(supplierCode, superCode);
                    }
                }
            }
        }

        LOGGER.warn("Suppliers: " + suppliers);
        for (Supplier supplier : suppliers) {
            LOGGER.warn("Supplier " + supplier.getName() + "'s codes: " + supplier.getCodes());
        }

        return suppliers;
    }

    public void processSupplier(Supplier supplier) {
        List<List<Cell>> sheet = ExcelImporter
                .importExcelSheet("xls/" + supplier.getName() + ".xls");
        LOGGER.info("Supplier: " + supplier.getName());
        LOGGER.debug("Supplier codes: " + supplier.getCodes());
        int i = 0;
        ProductConverter builder = new ProductConverter(supplier.getSheetStructure());
        for (List<Cell> row : sheet) {
            Product product = builder.toProduct(row);
            product.setSupplier(supplier.getName());
            if (product.isValid()) {
                String superCode = supplier.getCodes().get(product.getCode());
                if (superCode == null || superCode.isEmpty()) {
                    LOGGER.warn(i + ": No code for " + product);
                } else {
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

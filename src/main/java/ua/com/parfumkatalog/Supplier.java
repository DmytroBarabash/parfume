package ua.com.parfumkatalog;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:d.barabash@gmail.com"> Dmytro Barabash</a> 2014-02-08 02:55
 */
public class Supplier {

    private String name;
    private SheetStructure sheetStructure;
    private Map<String, Product> products = new HashMap<String, Product>();
    private Map<String, String> codes = new HashMap<String, String>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SheetStructure getSheetStructure() {
        return sheetStructure;
    }

    public void setSheetStructure(SheetStructure sheetStructure) {
        this.sheetStructure = sheetStructure;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Supplier");
        sb.append("{name='").append(name).append('\'');
        sb.append(", sheetStructure=").append(sheetStructure);
        sb.append('}');
        return sb.toString();
    }

    private int getInt(String s) {
        try {
            return Integer.valueOf(s.trim());
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    public void buildSheetStructure(String s) {
        try {
            String[] ss = s.split(",");
            sheetStructure = new SheetStructure();
            for (ProductProperty p : ProductProperty.values()) {
                int i = getInt(ss[p.ordinal()]);
                if (i > 0) {
                    sheetStructure.setPropertyMapping(i - 1, p);
                }
            }
        } catch (Exception ex) {
            System.out.println("Bad string '" + s + "' for sheet structure");
        }
    }

    public boolean isValid() {
        return name != null && !name.isEmpty() && !name.trim().isEmpty()
                && sheetStructure != null && sheetStructure.isValid();
    }

    public Map<String, Product> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Product> products) {
        this.products = products;
    }

    public Map<String, String> getCodes() {
        return codes;
    }

    public void setCodes(Map<String, String> codes) {
        this.codes = codes;
    }
}

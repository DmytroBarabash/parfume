package ua.com.parfumkatalog;

import com.google.common.base.MoreObjects;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:d.barabash@gmail.com"> Dmytro Barabash</a> 2014-02-08 02:55
 */
public class Supplier {

    private static final Logger LOGGER = Logger.getLogger(Supplier.class);

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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("sheetStructure", sheetStructure)
                .toString();
    }

}

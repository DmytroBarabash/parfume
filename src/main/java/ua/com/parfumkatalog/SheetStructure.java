package ua.com.parfumkatalog;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:dmytro.barabash@playtech.com"> Dmytro Barabash</a> 2013-09-06 16:59
 */
public class SheetStructure {

    private Map<Integer, ProductProperty> columns = new HashMap<Integer, ProductProperty>();

    public ProductProperty getProperty(int column) {
        return columns.get(column);
    }

    public void setPropertyMapping(int column, ProductProperty productProperty) {
        columns.put(column, productProperty);
    }

    public boolean isValid() {
        return columns.containsValue(ProductProperty.CODE) &&
                columns.containsValue(ProductProperty.NAME) &&
                columns.containsValue(ProductProperty.PRICE);
    }

}

package ua.com.parfumkatalog;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:dmytro.barabash@playtech.com"> Dmytro Barabash</a> 2013-09-06 16:59
 */
public class SheetStructure {

    private Map<Integer, ProductProperties> columns = new HashMap<Integer, ProductProperties>();

    public ProductProperties getProperty(int column) {
        return columns.get(Integer.valueOf(column));
    }

    public void setPropertyMapping(int column, ProductProperties productProperties) {
        columns.put(Integer.valueOf(column), productProperties);
    }

}

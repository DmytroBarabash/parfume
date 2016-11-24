package ua.com.parfumkatalog;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import org.apache.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

import static ua.com.parfumkatalog.ProductProperty.SUPPLIER;

/**
 * @author <a href="mailto:dmytro.barabash@playtech.com"> Dmytro Barabash</a> 2013-09-06 16:59
 */
public class SheetStructure {

    private static final Logger LOGGER = Logger.getLogger(ExcelImporter.class);

    private Map<Integer, ProductProperty> columns = new LinkedHashMap<Integer, ProductProperty>();

    public SheetStructure(String s) {
        buildSheetStructure(s);
    }

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

    public Map<Integer, ProductProperty> getColumns() {
        return ImmutableMap.copyOf(columns);
    }

    private static int getInt(String s) {
        try {
            return Integer.valueOf(s.trim());
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private void buildSheetStructure(String s) {
        try {
            String[] ss = s.split(",");
            for (ProductProperty p : ProductProperty.values()) {
                if (p == SUPPLIER) {
                    continue;
                }
                int i = getInt(ss[p.ordinal()]);
                if (i > 0) {
                    setPropertyMapping(i - 1, p);
                } else {
                    LOGGER.error("Bad value " + i + " in string '" + s + "' for sheet structure");
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Bad string '" + s + "' for sheet structure", ex);
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("columns", columns)
                .toString();
    }
}

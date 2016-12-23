package ua.com.parfumkatalog;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;

import java.math.BigDecimal;

/**
 * @author <a href="mailto:dmytro.barabash@playtech.com"> Dmytro Barabash</a> 2013-09-06 16:18
 */
public class Product {

    private String code;
    private String name;
    private Gender gender = Gender.UNKNOWN;
    private Integer volume;
    private BigDecimal price;
    private BigDecimal outPrice;
    private String category;
    private String description;
    private String supplier;
    private String superCode;

    public boolean isValid() {
        return !(Strings.isNullOrEmpty(code) || Strings.isNullOrEmpty(name) || price == null);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (Strings.isNullOrEmpty(gender)) {
            this.gender = Gender.UNKNOWN;
        } else {
            //todo use regexp and get it from configuration
            String g = gender.toLowerCase();
            if (g.startsWith("w") || g.startsWith("f") || g.startsWith("l")) {
                this.gender = Gender.FEMALE;
            } else if (g.startsWith("m")) {
                this.gender = Gender.MALE;
            } else if (g.startsWith("u")) {
                this.gender = Gender.UNISEX;
            } else {
                this.gender = Gender.UNKNOWN;
            }
        }
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
        outPrice = calculate(price);
    }

    /**
     * если менее 10уе + 4уе
     * 10-25уе +6уе
     * 25-35уе + 7уе
     * 35-50уе + 9уе
     * 50-70уе + 12уе
     * 70- 100уе+ 16уе
     * 100- 150уе + 20уе
     * 150 и более + 45уе
     */
    private static final BigDecimal P1 = BigDecimal.TEN;
    private static final BigDecimal P2 = new BigDecimal("25");
    private static final BigDecimal P3 = new BigDecimal("35");
    private static final BigDecimal P4 = new BigDecimal("50");
    private static final BigDecimal P5 = new BigDecimal("70");
    private static final BigDecimal P6 = new BigDecimal("100");
    private static final BigDecimal P7 = new BigDecimal("150");
    private static final BigDecimal A1 = new BigDecimal("4");
    private static final BigDecimal A2 = new BigDecimal("6");
    private static final BigDecimal A3 = new BigDecimal("7");
    private static final BigDecimal A4 = new BigDecimal("9");
    private static final BigDecimal A5 = new BigDecimal("12");
    private static final BigDecimal A6 = new BigDecimal("16");
    private static final BigDecimal A7 = new BigDecimal("20");
    private static final BigDecimal A8 = new BigDecimal("45");

    private static BigDecimal calculate(BigDecimal price) {
        if (price.compareTo(P1) < 0) {
            return price.add(A1);
        }
        if (price.compareTo(P2) < 0) {
            return price.add(A2);
        }
        if (price.compareTo(P3) < 0) {
            return price.add(A3);
        }
        if (price.compareTo(P4) < 0) {
            return price.add(A4);
        }
        if (price.compareTo(P5) < 0) {
            return price.add(A5);
        }
        if (price.compareTo(P6) < 0) {
            return price.add(A6);
        }
        if (price.compareTo(P7) < 0) {
            return price.add(A7);
        }
        return price.add(A8);
    }

    public BigDecimal getOutPrice() {
        return outPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSuperCode() {
        return superCode;
    }

    public void setSuperCode(String superCode) {
        this.superCode = superCode;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("code", code)
                .add("name", name)
                .add("gender", gender)
                .add("volume", volume)
                .add("price", price)
                .add("outPrice", outPrice)
                .add("category", category)
                .add("description", description)
                .add("supplier", supplier)
                .add("superCode", superCode)
                .toString();
    }

}

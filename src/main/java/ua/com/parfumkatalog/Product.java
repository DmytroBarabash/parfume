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
                .add("category", category)
                .add("description", description)
                .add("supplier", supplier)
                .add("superCode", superCode)
                .toString();
    }

}

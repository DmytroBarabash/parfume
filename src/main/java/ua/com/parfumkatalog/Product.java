package ua.com.parfumkatalog;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import java.math.BigDecimal;

/**
 * @author <a href="mailto:dmytro.barabash@playtech.com"> Dmytro Barabash</a> 2013-09-06 16:18
 */
public class Product {

    private String code;
    private String name;
    private Boolean female;
    private Integer volume;
    private BigDecimal price;
    private String category;
    private String description;

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

    public Boolean getFemale() {
        return female;
    }

    public void setFemale(String gender) {
        if (Strings.isNullOrEmpty(gender)) {
            female = null;
        } else {
            //todo use regexp and get it from configuration
            String g = gender.toLowerCase();
            if (g.startsWith("w") || g.startsWith("f") || g.startsWith("l")) {
                female = true;
            } else if (g.startsWith("m")) {
                female = false;
            } else {
                female = null;
            }
        }
    }

    public void setFemale(Boolean female) {
        this.female = female;
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

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("code", code)
                .add("name", name)
                .add("female", female)
                .add("volume", volume)
                .add("price", price)
                .add("category", category)
                .add("description", description)
                .toString();
    }

}

package tdc.edu.vn.apbansach.model;

import java.io.Serializable;

public class Products implements Serializable {
    private String products_id, categories_id, products_name,  price,description;

    private  String image;

    public Products(String products_id, String categories_id, String products_name, String price, String description, String image) {
        this.products_id = products_id;
        this.categories_id = categories_id;
        this.products_name = products_name;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public Products() {
    }

    public String getProducts_id() {
        return products_id;
    }

    public void setProducts_id(String products_id) {
        this.products_id = products_id;
    }

    public String getCategories_id() {
        return categories_id;
    }

    public void setCategories_id(String categories_id) {
        this.categories_id = categories_id;
    }

    public String getProducts_name() {
        return products_name;
    }

    public void setProducts_name(String products_name) {
        this.products_name = products_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

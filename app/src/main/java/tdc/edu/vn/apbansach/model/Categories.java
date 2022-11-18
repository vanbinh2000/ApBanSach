package tdc.edu.vn.apbansach.model;

public class Categories {
    String categories_id, categories_name, categories_icon;

    public Categories(String categories_id, String categories_name, String categories_icon) {
        this.categories_id = categories_id;
        this.categories_name = categories_name;
        this.categories_icon = categories_icon;
    }

    public Categories() {
    }

    public String getCategories_id() {
        return categories_id;
    }

    public void setCategories_id(String categories_id) {
        this.categories_id = categories_id;
    }

    public String getCategories_name() {
        return categories_name;
    }

    public void setCategories_name(String categories_name) {
        this.categories_name = categories_name;
    }

    public String getCategories_icon() {
        return categories_icon;
    }

    public void setCategories_icon(String categories_icon) {
        this.categories_icon = categories_icon;
    }
}

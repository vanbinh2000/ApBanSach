package tdc.edu.vn.apbansach.model;

public class Cart {
    String UserID, ProductID;

    public Cart() {
    }

    public Cart(String userID, String productID) {
        UserID = userID;
        ProductID = productID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }
}

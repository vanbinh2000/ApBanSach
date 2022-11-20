package tdc.edu.vn.apbansach.model;

public class Payment {
    String userid, productsid;
//    String total_price, datepayment;


    public Payment(String userid, String productsid) {
        this.userid = userid;
        this.productsid = productsid;
    }

    public Payment() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getProductsid() {
        return productsid;
    }

    public void setProductsid(String productsid) {
        this.productsid = productsid;
    }
}

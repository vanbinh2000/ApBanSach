package tdc.edu.vn.apbansach.model;

public class Payment {
    String userid, productsid;
    String total_price, quantity, datepayment;


    public Payment(String userid, String productsid, String total_price, String quantity, String datepayment) {
        this.userid = userid;
        this.productsid = productsid;
        this.total_price = total_price;
        this.quantity = quantity;
        this.datepayment = datepayment;
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

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDatepayment() {
        return datepayment;
    }

    public void setDatepayment(String datepayment) {
        this.datepayment = datepayment;
    }
}

package tdc.edu.vn.apbansach.model;

public class User {
    String Email, Fullname;

    public User() {
    }

    public User(String email, String fullname) {
        Email = email;
        Fullname = fullname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

}

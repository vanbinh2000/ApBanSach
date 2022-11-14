package tdc.edu.vn.apbansach.model;

public class User {
    String Email, Fullname, Password;

    public User() {
    }

    public User(String email, String fullname, String password) {
        Email = email;
        Fullname = fullname;
        Password = password;
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

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}

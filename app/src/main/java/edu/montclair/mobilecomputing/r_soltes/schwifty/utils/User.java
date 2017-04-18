package edu.montclair.mobilecomputing.r_soltes.schwifty.utils;

/**
 * Created by ryansoltes on 4/17/17.
 */

public class User {

    private String username, uid, email, userRole;

    public User(){

    }

    public User(String username, String email, String uid, String userRole) {
        this.username = username;
        this.uid = uid;
        this.email = email;
        this.userRole = userRole;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}

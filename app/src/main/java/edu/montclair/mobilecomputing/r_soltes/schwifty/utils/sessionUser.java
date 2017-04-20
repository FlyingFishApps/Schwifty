package edu.montclair.mobilecomputing.r_soltes.schwifty.utils;

import java.util.ArrayList;

/**
 * Created by ryansoltes on 4/19/17.
 */

public class sessionUser {

    public String name;
    public String email;
    public String userRole;
    public String uId;
    ArrayList<String> list;

    public sessionUser() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public sessionUser(String email,String uId,String name, String userRole) {
        this.name = name;
        this.email = email;
        this.userRole = userRole;
        this.uId = uId;
    }

    public sessionUser(String name, ArrayList<String> list) {
        this.name = name;
        this.list = list;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
}

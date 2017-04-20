package edu.montclair.mobilecomputing.r_soltes.schwifty.utils;

import java.util.ArrayList;

/**
 * Created by ryansoltes on 4/18/17.
 */

public class Business {

    private String bName, bId, bOwner;
    private ArrayList<String> listOfEmp = new ArrayList<>();

    public Business(){

    }

    public Business(String bName, String bId, String bOwner, ArrayList<String> listOfEmp) {
        this.bName = bName;
        this.bId = bId;
        this.bOwner = bOwner;
        this.listOfEmp = listOfEmp;

    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String getbId() {
        return bId;
    }

    public void setbId(String bId) {
        this.bId = bId;
    }

    public String getbOwner() {
        return bOwner;
    }

    public void setbOwner(String bOwner) {
        this.bOwner = bOwner;
    }
}

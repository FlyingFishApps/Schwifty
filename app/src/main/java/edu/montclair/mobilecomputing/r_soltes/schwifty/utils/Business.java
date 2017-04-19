package edu.montclair.mobilecomputing.r_soltes.schwifty.utils;

/**
 * Created by ryansoltes on 4/18/17.
 */

public class Business {

    private String bName, bId;

    public Business(){

    }

    public Business(String bName, String bId) {
        this.bName = bName;
        this.bId = bId;

    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String getBid() {
        return bId;
    }

    public void setBid(String bid) {
        this.bId = bid;
    }
}

package edu.montclair.mobilecomputing.r_soltes.schwifty.utils;

/**
 * Created by ryansoltes on 4/23/17.
 */

public class Notifications {

    private String nTitle, nBody, nId;

    public Notifications(){

    }

    public Notifications(String nTitle, String nBody, String nId) {
        this.nTitle = nTitle;
        this.nBody = nBody;
        this.nId = nId;

    }

    public String getnTitle() {
        return nTitle;
    }

    public void setnTitle(String nTitle) {
        this.nTitle = nTitle;
    }

    public String getnBody() {
        return nBody;
    }

    public void setnBody(String nBody) {
        this.nBody = nBody;
    }

    public String getnId() {
        return nId;
    }

    public void setnId(String nId) {
        this.nId = nId;
    }
}

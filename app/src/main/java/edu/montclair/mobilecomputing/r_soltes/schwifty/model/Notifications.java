package edu.montclair.mobilecomputing.r_soltes.schwifty.model;

/**
 * Created by ryansoltes on 4/23/17.
 */

public class Notifications {

    private String nTitle;
    private String nBody;
    private String nId;
    private String nDate;

    public Notifications(){
        // Empty required
    }

    /**
     * Constructor to create a notification with a date, title, body, and id
     * **/
    public Notifications(String nDate, String nTitle, String nBody, String nId) {
        this.nTitle = nTitle;
        this.nDate = nDate;
        this.nBody = nBody;
        this.nId = nId;

    }

    /**
     * Getters and setting for Notification attributes
     * **/
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

    public String getnDate() {
        return nDate;
    }

    public void setnDate(String nDate) {
        this.nDate = nDate;
    }
}

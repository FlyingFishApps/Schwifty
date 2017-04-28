package edu.montclair.mobilecomputing.r_soltes.schwifty.utils;

/**
 * Created by ryansoltes on 4/18/17.
 */

public class Business {

    private String bName, bId, bOwner;

    public Business(){
        // Empty required
    }

    /**
     * Constructor to create a new business with a name, id, and owner name
     * **/
    public Business(String bName, String bId, String bOwner) {
        this.bName = bName;
        this.bId = bId;
        this.bOwner = bOwner;

    }

    /**
     * Getters and setters for business attributes
     * **/
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

package edu.montclair.mobilecomputing.r_soltes.schwifty.model;

/**
 * Created by tjame_000 on 4/23/2017.
 */

public class ManagerNotifications {

    private String dEnd;
    private String dStart;
    private String nReason;
    private String nId;
    private String name;

    public ManagerNotifications(){
        // Empty required
    }

    /**
     * Constructor to create a manager notification with a start date, reason,
     * ID, end date, and a name
     * **/
    public ManagerNotifications(String dStart, String nReason, String nId, String dEnd, String name) {
        this.dStart = dStart;
        this.nReason = nReason;
        this.nId = nId;
        this.name = name;
        this.dEnd= dEnd;

    }

    /**
     * Getters and setters for manager notification attributes
     * **/
    public String getdStart() {
        return dStart;
    }

    public void setdStart(String dStart) {
        this.dStart = dStart;
    }

    public String getnReason() {
        return nReason;
    }

    public void setnReason(String nReason) {
        this.nReason = nReason;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getnId() {
        return nId;
    }

    public void setnId(String nId) {
        this.nId = nId;
    }

    public String getdEnd() {
        return dEnd;
    }

    public void setdEnd(String dEnd) {
        this.dEnd = dEnd;
    }
}

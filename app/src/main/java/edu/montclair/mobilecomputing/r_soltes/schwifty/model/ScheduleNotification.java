package edu.montclair.mobilecomputing.r_soltes.schwifty.model;

/**
 * Created by Jamest8 on 4/25/2017.
 */

public class ScheduleNotification {

    private String nDate;
    private String nStartTime;
    private String nEndTime;
    private String uID;

    public String getnId() {
        return nId;
    }

    public void setnId(String nId) {
        this.nId = nId;
    }

    private String nId;

    public String getnDate() {
        return nDate;
    }

    public void setnDate(String nDate) {
        this.nDate = nDate;
    }

    public String getnStartTime() {
        return nStartTime;
    }

    public void setnStartTime(String nStartTime) {
        this.nStartTime = nStartTime;
    }

    public String getnEndTime() {
        return nEndTime;
    }

    public void setnEndTime(String nEndTime) {
        this.nEndTime = nEndTime;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public ScheduleNotification() {

    }

    public ScheduleNotification(String nID, String uID, String nDate, String nStartTime, String nEndTime) {
        this.uID = uID;
        this.nId = nID;
        this.nDate = nDate;
        this.nStartTime = nStartTime;
        this.nEndTime = nEndTime;

    }
}
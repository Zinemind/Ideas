package com.example.customcalendar.model;


public class DateObject {
    private String displayName;
    private String displayDate;
    private StatusObject status;
    private Integer activateTime; //minute


    public DateObject(String displayName, String displayDate, StatusObject status, int activateTime) {
        this.displayName = displayName;
        this.displayDate = displayDate;
        this.status = status;
        this.activateTime = activateTime;
    }

    public DateObject(String displayDate) {
        this.displayDate = displayDate;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayDate() {
        return displayDate;
    }

    public void setDisplayDate(String displayDate) {
        this.displayDate = displayDate;
    }

    public StatusObject getStatus() {
        return status;
    }

    public void setStatus(StatusObject status) {
        this.status = status;
    }

    public Integer getActivateTime() {
        return activateTime;
    }

    public void setActivateTime(Integer activateTime) {
        this.activateTime = activateTime;
    }


    @Override
    public int hashCode() {
        return displayDate.hashCode();

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        return ((DateObject) obj).getDisplayDate().equals(getDisplayDate());
    }

    @Override
    public String toString() {
        return displayName;
  }
}

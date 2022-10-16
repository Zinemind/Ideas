package com.example.customcalendar.model;


public class StatusObject {

    private Boolean[] shiftStatus;
    private Boolean starRatingStatus;

    public StatusObject(Boolean[] bottomStatus, Boolean headStatus) {
        this.shiftStatus = bottomStatus;
        this.starRatingStatus = headStatus;
    }

    public Boolean[] getShiftStatus() {
        return shiftStatus;
    }

    public void setShiftStatus(Boolean[] shiftStatus) {
        this.shiftStatus = shiftStatus;
    }

    public Boolean getStarRatingStatus() {
        return starRatingStatus;
    }

    public void setStarRatingStatus(Boolean starRatingStatus) {
        this.starRatingStatus = starRatingStatus;
    }

}

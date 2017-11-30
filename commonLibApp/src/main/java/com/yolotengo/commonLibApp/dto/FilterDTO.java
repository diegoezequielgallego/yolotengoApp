package com.yolotengo.commonLibApp.dto;

/**
 * Created by dgallego on 29/11/2017.
 */
public class FilterDTO {

    private String areaLevel;
    private int ratio;
    private double latitude;
    private double longitude;
    private boolean righNow;
    private boolean delivery;
    private String categoryID;

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public boolean isRighNow() {
        return righNow;
    }

    public void setRighNow(boolean righNow) {
        this.righNow = righNow;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public String getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(String areaLevel) {
        this.areaLevel = areaLevel;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

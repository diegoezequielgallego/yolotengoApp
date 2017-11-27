package com.yolotengo.commonLibApp.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by dgallego on 27/11/2017.
 */
public class AdvertisementRequestDTO {

    private long userId;

    private String categoryId;
    private List<ItemDTO> items;
    private String picture;
    private Date creationDate;

    private String areaLevel1;
    private String areaLevel2;
    private double latitue;
    private double longitude;

    private boolean righNow;
    private boolean delivery;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getAreaLevel1() {
        return areaLevel1;
    }

    public void setAreaLevel1(String areaLevel1) {
        this.areaLevel1 = areaLevel1;
    }

    public String getAreaLevel2() {
        return areaLevel2;
    }

    public void setAreaLevel2(String areaLevel2) {
        this.areaLevel2 = areaLevel2;
    }

    public double getLatitue() {
        return latitue;
    }

    public void setLatitue(double latitue) {
        this.latitue = latitue;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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
}

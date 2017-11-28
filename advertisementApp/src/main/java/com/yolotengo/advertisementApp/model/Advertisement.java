package com.yolotengo.advertisementApp.model;


import com.datastax.driver.core.utils.UUIDs;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import java.util.Date;
import java.util.UUID;

@Table(value = "advertisement")
public class Advertisement {

    @PrimaryKeyColumn(name = "id",ordinal = 0,type = PrimaryKeyType.CLUSTERED)
    private UUID id = UUIDs.timeBased();

    @Column(value = "creationDate")
    private Date creationDate;

    @Column(value = "areaLevel1")
    private String areaLevel1;

    @Column(value = "areaLevel2")
    private String areaLevel2;

    @PrimaryKeyColumn(name = "userId",ordinal = 1,type = PrimaryKeyType.PARTITIONED)
    private String userId;

    @Column(value = "description")
    private String description;

    @Column(value = "categoryId")
    private String categoryId;

    @Column(value = "items")
    private String items;

    @Column(value = "picture")
    private String picture;

    @Column(value = "latitue")
    private double latitue;

    @Column(value = "longitude")
    private double longitude;

    @Column(value = "righNow")
    private boolean righNow;

    @Column(value = "delivery")
    private boolean delivery;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

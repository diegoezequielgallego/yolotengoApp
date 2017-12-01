package com.yolotengo.commonLibApp.dto;

import java.util.Date;
import java.util.UUID;

public class OfferDTO {

    private String id;
    private String idAdvertisement;
    private String idSeller;
    private String idBuyer;
    private String itemJason;
    private Date creationDate;
    private boolean send;
    private double price;
    private boolean buyerAccept;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdAdvertisement() {
        return idAdvertisement;
    }

    public void setIdAdvertisement(String idAdvertisement) {
        this.idAdvertisement = idAdvertisement;
    }

    public String getIdSeller() {
        return idSeller;
    }

    public void setIdSeller(String idSeller) {
        this.idSeller = idSeller;
    }

    public String getIdBuyer() {
        return idBuyer;
    }

    public void setIdBuyer(String idBuyer) {
        this.idBuyer = idBuyer;
    }

    public String getItemJason() {
        return itemJason;
    }

    public void setItemJason(String itemJason) {
        this.itemJason = itemJason;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isBuyerAccept() {
        return buyerAccept;
    }

    public void setBuyerAccept(boolean buyerAccept) {
        this.buyerAccept = buyerAccept;
    }
}

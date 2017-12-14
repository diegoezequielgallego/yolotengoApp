package com.yolotengo.offerApp.model;


import com.datastax.driver.core.utils.UUIDs;
import org.springframework.cassandra.core.Ordering;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import java.util.Date;
import java.util.UUID;

@Table(value = "offer")
public class Offer {

    @PrimaryKeyColumn(name = "id",ordinal = 1,type = PrimaryKeyType.CLUSTERED)
    private UUID id = UUIDs.timeBased();

    @PrimaryKeyColumn(name = "idAdvertisement",ordinal = 0,type = PrimaryKeyType.PARTITIONED)
    private UUID idAdvertisement;

    @Column(value = "idSeller")
    private String idSeller;

    @Column(value = "idBuyer")
    private String idBuyer;

    @Column(value = "itemJason")
    private String itemJason;

    @Column(value = "creationDate")
    private Date creationDate;

    @Column(value = "send")
    private boolean send;

    @Column(value = "price")
    private double price;

    @Column(value = "buyerAccept")
    private boolean buyerAccept;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdAdvertisement() {
        return idAdvertisement;
    }

    public void setIdAdvertisement(UUID idAdvertisement) {
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

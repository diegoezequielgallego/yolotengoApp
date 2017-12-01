package com.yolotengo.offerApp.model;


import com.datastax.driver.core.utils.UUIDs;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import java.util.Date;
import java.util.UUID;

@Table(value = "offer")
public class Offer {

    @PrimaryKeyColumn(name = "id",ordinal = 0,type = PrimaryKeyType.CLUSTERED)
    private UUID id = UUIDs.timeBased();

    @PrimaryKeyColumn(name = "idAdvertisement",ordinal = 1,type = PrimaryKeyType.PARTITIONED)
    private UUID idAdvertisement;

    @Column(value = "idSeller")
    private String idSeller;

    @PrimaryKeyColumn(name = "idBuyer",ordinal = 2,type = PrimaryKeyType.PARTITIONED)
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

}

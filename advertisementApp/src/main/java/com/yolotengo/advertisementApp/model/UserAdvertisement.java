package com.yolotengo.advertisementApp.model;

import com.datastax.driver.core.utils.UUIDs;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import java.util.Date;
import java.util.UUID;

@Table(value = "useradavertisement")
public class UserAdvertisement {

    @PrimaryKeyColumn(name = "id",ordinal = 0,type = PrimaryKeyType.CLUSTERED)
    private UUID id = UUIDs.timeBased();

    @PrimaryKeyColumn(name = "userId",ordinal = 1,type = PrimaryKeyType.PARTITIONED)
    private String userId;

    @Column(value = "idAdvertisement")
    private UUID idAdvertisement;

    @Column(value = "finishDate")
    private Date finishDate;

    @Column(value = "reposted")
    private boolean reposted;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UUID getIdAdvertisement() {
        return idAdvertisement;
    }

    public void setIdAdvertisement(UUID idAdvertisement) {
        this.idAdvertisement = idAdvertisement;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public boolean isReposted() {
        return reposted;
    }

    public void setReposted(boolean reposted) {
        this.reposted = reposted;
    }
}

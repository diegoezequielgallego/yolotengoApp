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

    @PrimaryKeyColumn(name="user",ordinal = 1,type = PrimaryKeyType.PARTITIONED)
    private String user;

    @Column(value = "description")
    private String description;

    @Column(value = "creation_date")
    private Date creationDate;


    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Date getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
}

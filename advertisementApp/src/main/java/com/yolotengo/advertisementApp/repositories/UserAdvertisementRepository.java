package com.yolotengo.advertisementApp.repositories;

import com.yolotengo.advertisementApp.model.UserAdvertisement;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserAdvertisementRepository extends CassandraRepository<UserAdvertisement> {

    @Query("SELECT * FROM useradavertisement WHERE userId =?0")
    List<UserAdvertisement> findByUser(String userId);

}

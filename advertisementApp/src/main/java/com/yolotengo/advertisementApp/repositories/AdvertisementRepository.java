package com.yolotengo.advertisementApp.repositories;

import com.yolotengo.advertisementApp.model.Advertisement;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;
import java.util.UUID;


/**
 * Created by dgallego on 23/11/2017.
 */
public interface AdvertisementRepository extends CassandraRepository<Advertisement> {

    @Query("SELECT * FROM advertisement WHERE areaLevel1 =?0")
    List<Advertisement> findByPlace(String areaLevel);

    @Query("SELECT * FROM advertisement WHERE id = ?0 ALLOW FILTERING")
    Advertisement findById(UUID idAdvertisement);

}

package com.yolotengo.advertisementApp.repositories;

import com.yolotengo.advertisementApp.model.Advertisement;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;


/**
 * Created by dgallego on 23/11/2017.
 */
public interface AdvertisementRepository extends CassandraRepository<Advertisement> {

    @Query("SELECT * FROM advertisement WHERE userId=?0 LIMIT ?1")
    List<Advertisement> findByUser(String userId, Integer limit);


}

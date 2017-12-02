package com.yolotengo.offerApp.repositories;

import com.yolotengo.offerApp.model.Offer;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;
import java.util.UUID;


/**
 * Created by dgallego on 23/11/2017.
 */
public interface OfferRepository extends CassandraRepository<Offer> {

    @Query("SELECT * FROM offer WHERE id = ?0 ALLOW FILTERING")
    List<Offer> findById(UUID idad);

    @Query("SELECT * FROM offer WHERE idAdvertisement = ?0")
    List<Offer> findByIdAdvertisement(UUID id);

}

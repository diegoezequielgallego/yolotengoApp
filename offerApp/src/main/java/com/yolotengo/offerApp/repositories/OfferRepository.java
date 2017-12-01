package com.yolotengo.offerApp.repositories;

import com.yolotengo.offerApp.model.Offer;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;


/**
 * Created by dgallego on 23/11/2017.
 */
public interface OfferRepository extends CassandraRepository<Offer> {

    @Query("SELECT * FROM offer WHERE id =?0")
    List<Offer> findByPlace(String id);

}

package com.yolotengo.gatewayApp.repositories;

import com.yolotengo.gatewayApp.dto.CountDTO;
import com.yolotengo.gatewayApp.model.Mutant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MutantRepository extends JpaRepository<Mutant, Long>{

    @Query(value = "select new com.yolotengo.gatewayApp.dto.CountDTO (COUNT(m)) FROM Mutant m WHERE isMutant = 1")
    CountDTO findTotales();

}

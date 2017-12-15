package com.yolotengo.gatewayApp.repositories;

import com.yolotengo.gatewayApp.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserLogin, Long>{

}

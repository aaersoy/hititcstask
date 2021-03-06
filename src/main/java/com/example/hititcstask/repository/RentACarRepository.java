package com.example.hititcstask.repository;



import com.example.hititcstask.entity.RentACar;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("RentACarRepository")
public interface RentACarRepository extends JpaRepository<RentACar,Long> {

    @Query("SELECT rentACar FROM RentACar rentACar where rentACar.rentACarName=:rentACarName")
    RentACar getRentACarByName(@Param("rentACarName") String rentACarName);



}

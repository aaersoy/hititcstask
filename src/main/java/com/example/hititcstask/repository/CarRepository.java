package com.example.hititcstask.repository;

import com.example.hititcstask.entity.Car;
import com.example.hititcstask.exception.CarNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
@Qualifier("CarRepository")
public interface CarRepository extends JpaRepository<Car, Long>  {

    @Query("SELECT car FROM Car car where car.plateCode=:plateCode")
    Car getCarByPlateCode(@Param("plateCode") String plateCode) throws CarNotFoundException;

    @Query("SELECT car FROM Car car where car.carID=:carID")
    Car getCarByID( @Param("carID")Long carID)throws CarNotFoundException;
}

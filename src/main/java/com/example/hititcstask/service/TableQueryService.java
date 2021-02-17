package com.example.hititcstask.service;

import com.example.hititcstask.entity.Car;
import com.example.hititcstask.entity.RentACar;


import java.util.List;
import java.util.Map;

public interface TableQueryService {

    Car getCar(Long carID) ;

    RentACar getRentACar(Long rentACarID) ;

    void removeCar(Long carID) ;

    void removeRentACar(Long rentACarID) ;

    void rentCar(Long carID) ;

    void releaseCar(Long id) ;

    void getCarData(List<Map<String, Object>> carTable, String carIDStr, String plateCodeStr, String rentedStr);

    void getRentACarData(List<Map<String, Object>> right, String rentACarNameStr, String carCountStr);

    void searchCarWithKey(String key, List<Map<String, Object>> left, String carIDStr, String plateCodeStr, String rentedStr);

    void searchRentACarWithKey(String key, List<Map<String, Object>> right);
}

package com.example.hititcstask.service;

import com.example.hititcstask.entity.Car;
import com.example.hititcstask.entity.RentACar;
import com.example.hititcstask.exception.CarNotFoundException;
import com.example.hititcstask.exception.RentACarNotFoundException;


import java.util.List;
import java.util.Map;

public interface TableQueryService {

    Car getCar(Long carID) throws CarNotFoundException;

    RentACar getRentACar(Long rentACarID) throws RentACarNotFoundException;

    void removeCar(Long carID) throws CarNotFoundException;

    void removeRentACar(Long rentACarID) throws CarNotFoundException,RentACarNotFoundException;

    void rentCar(Long carID) throws CarNotFoundException;

    void releaseCar(Long id) throws CarNotFoundException;

    void getCarData(List<Map<String, Object>> carTable, String carIDStr, String plateCodeStr, String rentedStr);

    void getRentACarData(List<Map<String, Object>> right, String rentACarNameStr, String carCountStr);

    void searchCarWithKey(String key, List<Map<String, Object>> left, String carIDStr, String plateCodeStr, String rentedStr);

    void searchRentACarWithKey(String key, List<Map<String, Object>> right);
}

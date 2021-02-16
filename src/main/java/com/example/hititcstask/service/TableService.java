package com.example.hititcstask.service;

import com.example.hititcstask.entity.Car;
import com.example.hititcstask.entity.RentACar;
import com.example.hititcstask.model.*;

import java.util.Map;
import java.util.List;



public interface TableService {
    ResponseNewCar addNewCar(RequestNewCar requestNewCar) throws Exception;
    ResponseNewRentACar addRentACar(RequestNewRentACar requestNewRentACar) throws Exception;

    void removeCar(Long carID);

    void getCarData(List<Map<String, Object>> left);

    void getRentACarData(List<Map<String, Object>> right);

    void rentCar(Long carID);

    void searchCarWithKey(String key, List<Map<String, Object>> left);

    Car getCar(Long carID);
    RentACar getRentACar(Long rentACarID);

    void updateCar(RequestUpdateCar requestUpdateCar) throws Exception;

    void updateRentACar(RequestUpdateRentACar requestUpdateRentACar) throws Exception;

    void removeRentACar(Long rentACarID) throws Exception;

    void searchRentACarWithKey(String key, List<Map<String, Object>> right);

    ResponseAllRentACar getAllRentACarIDS();
}

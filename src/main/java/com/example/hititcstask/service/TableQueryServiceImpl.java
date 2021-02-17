package com.example.hititcstask.service;

import com.example.hititcstask.entity.Car;
import com.example.hititcstask.entity.RentACar;
import com.example.hititcstask.exception.CarNotFoundException;
import com.example.hititcstask.exception.RentACarNotFoundException;
import com.example.hititcstask.repository.CarRepository;
import com.example.hititcstask.repository.RentACarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Qualifier("TableQueryServiceImpl")
public class TableQueryServiceImpl implements TableQueryService {

    private static String rentACarID = "rentACarID";
    @Autowired
    @Qualifier("RentACarRepository")
    private RentACarRepository rentACarRepository;
    @Autowired
    @Qualifier("CarRepository")
    private CarRepository carRepository;

    @Override
    public void removeCar(Long carID) throws CarNotFoundException {

        Car car = carRepository.getCarByID(carID);
        if (car.getRentACar() != null) {
            RentACar rentACar = car.getRentACar();
            rentACar.getCarList().remove(car);
            rentACarRepository.save(rentACar);
        }
        carRepository.delete(car);
    }

    @Override
    public void getCarData(List<Map<String, Object>> carTable, String carIDStr, String plateCodeStr, String rentedStr) {
        for (Car car : carRepository.findAll()) {
            HashMap<String, Object> element = new HashMap<>();
            element.put(carIDStr, car.getCarID());
            element.put(plateCodeStr, car.getPlateCode());
            element.put(rentedStr, car.isRented());
            if (car.getRentACar() == null) {
                element.put(rentACarID, Long.valueOf(-1));
            } else {
                element.put(rentACarID, car.getRentACar().getRentACarID());
            }
            carTable.add(element);
        }
    }

    @Override
    public void getRentACarData(List<Map<String, Object>> right, String rentACarNameStr, String carCountStr) {
        for (RentACar rentACar : rentACarRepository.findAll()) {
            HashMap<String, Object> element = new HashMap<>();
            element.put(rentACarID, rentACar.getRentACarID());
            element.put(rentACarNameStr, rentACar.getRentACarName());
            element.put(carCountStr, Long.valueOf(rentACar.getCarList().size()));
            right.add(element);
        }
    }

    @Override
    public void rentCar(Long carID) throws CarNotFoundException {

        Car car = carRepository.getCarByID(carID);
        car.setRented(true);
        carRepository.save(car);

    }

    @Override
    public void searchCarWithKey(String key, List<Map<String, Object>> left, String carIDStr, String plateCodeStr, String rentedStr) {


        for (Car car : carRepository.findAll()) {
            if (car.getPlateCode().contains(key) || car.getCarID().toString().contains(key)
                    || Boolean.toString(car.isRented()).contains(key)
                    || Long.toString(car.getRentACar().getRentACarID()).contains(key)
            ) {
                HashMap<String, Object> element = new HashMap<>();
                element.put(carIDStr, car.getCarID());
                element.put(plateCodeStr, car.getPlateCode());
                element.put(rentedStr, car.isRented());
                if (car.getRentACar() == null) {
                    element.put(rentACarID, Long.valueOf(-1));
                } else {
                    element.put(rentACarID, car.getRentACar().getRentACarID());
                }
                left.add(element);
            }
        }
    }

    @Override
    public Car getCar(Long carID) throws CarNotFoundException {
        return carRepository.getCarByID(carID);
    }

    @Override
    public RentACar getRentACar(Long rentACarID) throws RentACarNotFoundException {
        return rentACarRepository.getRentACarByID(rentACarID);
    }


    @Override
    public void removeRentACar(Long rentACarID) throws CarNotFoundException, RentACarNotFoundException {
        RentACar rentACar = rentACarRepository.getOne(rentACarID);
        for (Car car : rentACar.getCarList()) {
            carRepository.delete(car);
        }
        rentACarRepository.delete(rentACar);
    }

    @Override
    public void searchRentACarWithKey(String key, List<Map<String, Object>> right) {

        for (RentACar rentACar : rentACarRepository.findAll()) {
            if (rentACar.getRentACarName().contains(key) || rentACar.getRentACarID().toString().contains(key) ||
                    Integer.toString(rentACar.getCarList().size()).contains(key)
            ) {
                HashMap<String, Object> element = new HashMap<>();
                element.put("rentACarID", rentACar.getRentACarID());
                element.put("rentACarName", rentACar.getRentACarName());
                element.put("carCount", rentACar.getCarList().size());
                right.add(element);
            }
        }
    }


    @Override
    public void releaseCar(Long id) throws CarNotFoundException {
        Car car = carRepository.getCarByID(id);
        car.setRented(false);
        carRepository.save(car);

    }
}

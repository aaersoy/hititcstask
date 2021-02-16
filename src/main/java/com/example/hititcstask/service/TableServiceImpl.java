package com.example.hititcstask.service;

import com.example.hititcstask.entity.Car;
import com.example.hititcstask.entity.RentACar;
import com.example.hititcstask.model.*;
import com.example.hititcstask.repository.CarRepository;
import com.example.hititcstask.repository.RentACarRepository;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Qualifier("TableServiceImpl")
public class TableServiceImpl implements TableService {

    @Autowired
    @Qualifier("RentACarRepository")
    private RentACarRepository rentACarRepository;

    @Autowired
    @Qualifier("CarRepository")
    private CarRepository carRepository;

    private static String rentACarID="rentACarID";

    @Override
    public ResponseNewCar addNewCar(RequestNewCar requestNewCar) throws Exception{
        ResponseNewCar responseNewCar = new ResponseNewCar();
        Car car = carRepository.getWithPlateCode(requestNewCar.getPlateCode());
        if (car == null) {
            car= new Car();
            car.setPlateCode(requestNewCar.getPlateCode());
            car.setRented(false);
            RentACar rentACar = rentACarRepository.getOne(Long.parseLong(requestNewCar.getRentACarID()));
            carRepository.save(car);
            if (rentACar==null) {
                car.setRentACar(null);
                responseNewCar.setRentACarID( Long.valueOf(-1));
                responseNewCar.setCarID(car.getCarID());
            } else {

                car.setRentACar(rentACar);
                rentACar.getCarList().add(car);
                responseNewCar.setRentACarID(rentACar.getRentACarID());
                responseNewCar.setCarID(car.getCarID());
            }
            carRepository.save(car);

            responseNewCar.setPlateCode(car.getPlateCode());
            responseNewCar.setRented(car.isRented());
        }
        else
            throw new Exception();

        return responseNewCar;
    }

    @Override
    public ResponseNewRentACar addRentACar(RequestNewRentACar requestNewRentACar) throws Exception {
        ResponseNewRentACar responseNewRentACar=new ResponseNewRentACar();
        RentACar rentACar = rentACarRepository.getRentACarbyName(requestNewRentACar.getRentACarName());

        if(rentACar==null){
            rentACar=new RentACar();
            rentACar.setCarList(new ArrayList<>());
            rentACar.setRentACarName(requestNewRentACar.getRentACarName());
            rentACarRepository.save(rentACar);
            responseNewRentACar.setRentACarID(rentACar.getRentACarID());
            responseNewRentACar.setRentACarName(rentACar.getRentACarName());
            responseNewRentACar.setCarCount(Long.valueOf(rentACar.getCarList().size()));
        }
        else
            throw new Exception();

        return responseNewRentACar;
    }

    @Override
    public void removeCar(Long carID) {

        Car car=carRepository.getOne(carID);
        if(car.getRentACar()!=null){
            RentACar rentACar=car.getRentACar();
            rentACar.getCarList().remove(car);
            rentACarRepository.save(rentACar);
        }
        carRepository.delete(car);
    }

    @Override
    public void getCarData(List<Map<String, Object>> left) {
        for(Car car : carRepository.findAll()){
            HashMap<String,Object> element=new HashMap<>();
            element.put("carID",car.getCarID());
            element.put("plateCode",car.getPlateCode());
            element.put("rented", car.isRented());
            if(car.getRentACar()==null){
                element.put(rentACarID,Long.valueOf(-1));
            }
            else{
                element.put(rentACarID,car.getRentACar().getRentACarID());
            }
            left.add(element);
        }
    }

    @Override
    public void getRentACarData(List<Map<String, Object>> right) {
        for(RentACar rentACar : rentACarRepository.findAll()){
            HashMap<String,Object> element=new HashMap<>();
            element.put(rentACarID,rentACar.getRentACarID());
            element.put("rentACarName",rentACar.getRentACarName());
            element.put("carCount",  Long.valueOf(rentACar.getCarList().size()));
            right.add(element);
        }
    }

    @Override
    public void rentCar(Long carID) {

        Car car=carRepository.getOne(carID);
        car.setRented(true);
        carRepository.save(car);

    }

    @Override
    public void searchCarWithKey(String key,List<Map<String, Object>> left) {


        for(Car car : carRepository.findAll()){
            if(car.getPlateCode().contains(key) || car.getCarID().toString().contains(key)
                    || Boolean.toString(car.isRented()).contains(key)
                    || Long.toString(car.getRentACar().getRentACarID()).contains(key)
            ){
            HashMap<String,Object> element=new HashMap<>();
            element.put("carID",car.getCarID());
            element.put("plateCode",car.getPlateCode());
            element.put("rented", car.isRented());
            if(car.getRentACar()==null){
                element.put(rentACarID, Long.valueOf(-1));
            }
            else{
                element.put(rentACarID,car.getRentACar().getRentACarID());
            }
            left.add(element);
            }
        }
    }

    @Override
    public Car getCar(Long carID) {
       return carRepository.getOne(carID);
    }

    @Override
    public RentACar getRentACar(Long rentACarID) {
        return rentACarRepository.getOne(rentACarID);
    }

    @Override
    public void updateCar(RequestUpdateCar requestUpdateCar) throws Exception {
        Car car=carRepository.getOne(requestUpdateCar.getCarID());
        RentACar rentACar=rentACarRepository.getOne(requestUpdateCar.getRentACarID());

        if(rentACar == null){
            throw new Exception();
        }
        else{

            if(car.getRentACar()==null){
                rentACar.getCarList().add(car);
                rentACarRepository.save(rentACar);
            }
            else{
                car.getRentACar().getCarList().remove(car);
                rentACar.getCarList().add(car);
                rentACarRepository.save(car.getRentACar());
                rentACarRepository.save(rentACar);

            }

            car.setRentACar(rentACar);
            car.setPlateCode(requestUpdateCar.getPlateCode());
        }


        carRepository.save(car);
    }

    @Override
    public void updateRentACar(RequestUpdateRentACar requestUpdateRentACar) throws Exception {
        RentACar rentACar=rentACarRepository.getOne(requestUpdateRentACar.getRentACarID());
        if(rentACar==null)
            throw new Exception();
        else{
            rentACar.setRentACarName(requestUpdateRentACar.getRentACarName());
            rentACarRepository.save(rentACar);
        }
    }

    @Override
    public void removeRentACar(Long rentACarID) throws Exception {
        RentACar rentACar=rentACarRepository.getOne(rentACarID);
        for(Car car : rentACar.getCarList()){
            carRepository.delete(car);
        }
        rentACarRepository.delete(rentACar);
    }

    @Override
    public void searchRentACarWithKey(String key, List<Map<String, Object>> right) {

        for(RentACar rentACar : rentACarRepository.findAll()){
            if(rentACar.getRentACarName().contains(key) || rentACar.getRentACarID().toString().contains(key) ||
                   Integer.toString(rentACar.getCarList().size()).contains(key)
            ){
                HashMap<String,Object> element=new HashMap<>();
                element.put("rentACarID",rentACar.getRentACarID());
                element.put("rentACarName",rentACar.getRentACarName());
                element.put("carCount", rentACar.getCarList().size());
                right.add(element);
            }
        }
    }

    @Override
    public ResponseAllRentACar getAllRentACarIDS() {
        ResponseAllRentACar responseAllRentACar=new ResponseAllRentACar();
        List<Long> rentACarList=new ArrayList<>();
        for(RentACar rentACar : rentACarRepository.findAll()){
            rentACarList.add(rentACar.getRentACarID() );
        }
        responseAllRentACar.setRentACarList(rentACarList);
        return responseAllRentACar;
    }

    @Override
    public void releaseCar(Long id) {
        Car car=carRepository.getOne(id);
        car.setRented(false);
        carRepository.save(car);

    }


}

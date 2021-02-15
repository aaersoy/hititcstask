package com.example.hititcstask.service;

import com.example.hititcstask.entity.Car;
import com.example.hititcstask.entity.RentACar;
import com.example.hititcstask.model.*;
import com.example.hititcstask.repository.CarRepository;
import com.example.hititcstask.repository.RentACarRepository;
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


    @Override
    public ResponseNewCar addNewCar(RequestNewCar requestNewCar) throws Exception{
        ResponseNewCar responseNewCar = new ResponseNewCar();
        Car car = carRepository.getWithPlateCode(requestNewCar.getPlateCode());
        if (car == null) {
            car= new Car();
            car.setPlateCode(requestNewCar.getPlateCode());
            car.setRented(false);
            RentACar rentACar = rentACarRepository.getRentACarbyID(Long.parseLong(requestNewCar.getRentACarID()));
            carRepository.save(car);
            if (rentACar == null) {
                car.setRentACar(null);
                responseNewCar.setRentACarID(new Long(-1));
                responseNewCar.setCarID(car.getCarID());
            } else {
                System.out.println("asdasdasd");

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
            rentACar.setCarList(new ArrayList<Car>());
            rentACar.setRentACarName(requestNewRentACar.getRentACarName());
            rentACarRepository.save(rentACar);
            responseNewRentACar.setRentACarID(rentACar.getRentACarID());
            responseNewRentACar.setRentACarName(rentACar.getRentACarName());
            responseNewRentACar.setCarCount(new Long(rentACar.getCarList().size()));
        }
        else
            throw new Exception();

        return responseNewRentACar;
    }

    @Override
    public void removeCar(RequestDeleteCar requestDeleteCar) {


        Car car=carRepository.getOne(new Long(requestDeleteCar.getCarID()));
        RentACar rentACar=car.getRentACar();
        rentACar.getCarList().remove(car);
        carRepository.delete(car);
        rentACarRepository.save(rentACar);

    }

    @Override
    public void getCarData(List<Map<String, Object>> left) {
        for(Car car : carRepository.findAll()){
            HashMap<String,Object> element=new HashMap<>();
            element.put("carID",car.getCarID());
            element.put("plateCode",car.getPlateCode());
            element.put("rented", car.isRented());
            if(car.getRentACar()==null){
                element.put("rentACarID",new Long(-1));
            }
            else{
                element.put("rentACarID",car.getRentACar().getRentACarID());
            }
            left.add(element);
        }
    }

    @Override
    public void getRentACarData(List<Map<String, Object>> right) {
        for(RentACar rentACar : rentACarRepository.findAll()){
            HashMap<String,Object> element=new HashMap<>();
            element.put("rentACarID",rentACar.getRentACarID());
            element.put("rentACarName",rentACar.getRentACarName());
            element.put("carCount", new Long(rentACar.getCarList().size()));
            right.add(element);
        }
    }

    @Override
    public void rentCar(RequestRentCar requestRentCar) {

        Car car=carRepository.getWithID(new Long(requestRentCar.getCarID()));
        car.setRented(true);
        carRepository.save(car);

    }

    @Override
    public void searchCarWithKey(String key,List<Map<String, Object>> left) {
        for(Car car : carRepository.findAll()){
            if(car.getPlateCode().contains(key)){
            HashMap<String,Object> element=new HashMap<>();
            element.put("carID",car.getCarID());
            element.put("plateCode",car.getPlateCode());
            element.put("rented", car.isRented());
            if(car.getRentACar()==null){
                element.put("rentACarID",new Long(-1));
            }
            else{
                element.put("rentACarID",car.getRentACar().getRentACarID());
            }
            left.add(element);
            }
        }
    }

    @Override
    public Car getCar(Long carID) {
       return carRepository.getWithID(carID);
    }

    @Override
    public RentACar getRentACar(Long rentACarID) {
        return rentACarRepository.getRentACarbyID(rentACarID);
    }

    @Override
    public void updateCar(RequestUpdateCar requestUpdateCar) throws Exception {
        Car car=carRepository.getWithID(requestUpdateCar.getCarID());
        RentACar rentACar=rentACarRepository.getRentACarbyID(requestUpdateCar.getRentACarID());
        System.out.println(rentACar);
        if(rentACar == null){
            throw new Exception();
        }
        else{
            System.out.println("else");
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
        RentACar rentACar=rentACarRepository.getRentACarbyID(requestUpdateRentACar.getRentACarID());
        if(rentACar==null)
            throw new Exception();
        else{
            rentACar.setRentACarName(requestUpdateRentACar.getRentACarName());
        rentACarRepository.save(rentACar);
        }
    }

    @Override
    public void removeRentACar(RequestRemoveRentACar requestRemoveRentACar) throws Exception {
        RentACar rentACar=rentACarRepository.getRentACarbyID(requestRemoveRentACar.getRentACarID());
        for(Car car : rentACar.getCarList()){
            car.setRentACar(null);
            carRepository.save(car);
        }
        rentACarRepository.delete(rentACar);
    }
}

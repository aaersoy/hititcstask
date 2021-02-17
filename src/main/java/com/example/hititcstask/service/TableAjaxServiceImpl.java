package com.example.hititcstask.service;

import com.example.hititcstask.entity.Car;
import com.example.hititcstask.entity.RentACar;
import com.example.hititcstask.exception.CarNotFoundException;
import com.example.hititcstask.exception.ElementExistException;
import com.example.hititcstask.exception.RentACarNotFoundException;
import com.example.hititcstask.model.*;
import com.example.hititcstask.repository.CarRepository;
import com.example.hititcstask.repository.RentACarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("TableAjaxServiceImpl")
public class TableAjaxServiceImpl implements TableAjaxService {


    @Autowired
    @Qualifier("RentACarRepository")
    private RentACarRepository rentACarRepository;
    @Autowired
    @Qualifier("CarRepository")
    private CarRepository carRepository;

    @Override
    public void updateCar(RequestUpdateCar requestUpdateCar) throws CarNotFoundException, RentACarNotFoundException, ElementExistException {
        Car car = carRepository.getCarByID(requestUpdateCar.getCarID());
        RentACar rentACar = rentACarRepository.getRentACarByID(requestUpdateCar.getRentACarID());

        if(carRepository.getCarByPlateCode(requestUpdateCar.getPlateCode())==null){

        car.setRentACar(rentACar);
        car.setPlateCode(requestUpdateCar.getPlateCode());
        rentACar.getCarList().add(car);
        rentACarRepository.save(rentACar);
        carRepository.save(car);
    }else if(carRepository.getCarByPlateCode(requestUpdateCar.getPlateCode())!=null
                && !carRepository.getCarByPlateCode(requestUpdateCar.getPlateCode())
                .getRentACar().getRentACarID().equals(requestUpdateCar.getRentACarID())){

            car.getRentACar().getCarList().remove(car);
            rentACarRepository.save(car.getRentACar());
            car.setRentACar(rentACar);
            car.setPlateCode(requestUpdateCar.getPlateCode());
            rentACar.getCarList().add(car);
            rentACarRepository.save(rentACar);
            carRepository.save(car);
        } else{
        throw new ElementExistException();}
    }

    @Override
    public void updateRentACar(RequestUpdateRentACar requestUpdateRentACar) throws CarNotFoundException, RentACarNotFoundException, ElementExistException {
        if(rentACarRepository.getRentACarByName(requestUpdateRentACar.getRentACarName())==null){
        RentACar rentACar = rentACarRepository.getRentACarByID(requestUpdateRentACar.getRentACarID());
        rentACar.setRentACarName(requestUpdateRentACar.getRentACarName());
        rentACarRepository.save(rentACar);
        }else
            throw new ElementExistException();
    }

    @Override
    public ResponseAllRentACar getAllRentACarIDS() {
        ResponseAllRentACar responseAllRentACar = new ResponseAllRentACar();
        List<Long> rentACarList = new ArrayList<>();
        for (RentACar rentACar : rentACarRepository.findAll()) {
            rentACarList.add(rentACar.getRentACarID());
        }
        responseAllRentACar.setRentACarList(rentACarList);
        return responseAllRentACar;
    }

    @Override
    public void addNewCar(RequestNewCar requestNewCar) throws CarNotFoundException, RentACarNotFoundException, ElementExistException {


        if(carRepository.getCarByPlateCode(requestNewCar.getPlateCode())==null) {
            Car car = new Car();
            car.setPlateCode(requestNewCar.getPlateCode());
            car.setRented(false);
            RentACar rentACar = rentACarRepository.getRentACarByID(Long.parseLong(requestNewCar.getRentACarID()));

            car.setRentACar(rentACar);
            rentACar.getCarList().add(car);

            carRepository.save(car);
        }else{
            throw new ElementExistException();
        }
    }

    @Override
    public void addRentACar(RequestNewRentACar requestNewRentACar) throws CarNotFoundException, RentACarNotFoundException, ElementExistException {
        if(rentACarRepository.getRentACarByName(requestNewRentACar.getRentACarName())==null){
        RentACar rentACar = new RentACar();
        rentACar.setCarList(new ArrayList<>());
        rentACar.setRentACarName(requestNewRentACar.getRentACarName());
        rentACarRepository.save(rentACar);}

    else
        throw new ElementExistException();

    }
}

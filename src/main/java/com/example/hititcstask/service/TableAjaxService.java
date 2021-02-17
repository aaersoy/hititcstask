package com.example.hititcstask.service;

import com.example.hititcstask.exception.CarNotFoundException;
import com.example.hititcstask.exception.ElementExistException;
import com.example.hititcstask.exception.RentACarNotFoundException;
import com.example.hititcstask.model.*;

public interface TableAjaxService {

    void updateCar(RequestUpdateCar requestUpdateCar) throws CarNotFoundException, RentACarNotFoundException, ElementExistException;

    void updateRentACar(RequestUpdateRentACar requestUpdateRentACar) throws CarNotFoundException, RentACarNotFoundException, ElementExistException;

    ResponseAllRentACar getAllRentACarIDS();

    void addNewCar(RequestNewCar requestNewCar) throws CarNotFoundException, RentACarNotFoundException, ElementExistException;

    void addRentACar(RequestNewRentACar requestNewRentACar) throws CarNotFoundException, RentACarNotFoundException, ElementExistException;

}

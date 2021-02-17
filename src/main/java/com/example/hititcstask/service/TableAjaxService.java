package com.example.hititcstask.service;

import com.example.hititcstask.exception.ElementExistException;
import com.example.hititcstask.model.*;

public interface TableAjaxService {

    void updateCar(RequestUpdateCar requestUpdateCar) throws ElementExistException;

    void updateRentACar(RequestUpdateRentACar requestUpdateRentACar) throws  ElementExistException;

    ResponseAllRentACar getAllRentACarIDS();

    void addNewCar(RequestNewCar requestNewCar) throws ElementExistException;

    void addRentACar(RequestNewRentACar requestNewRentACar) throws  ElementExistException;

}

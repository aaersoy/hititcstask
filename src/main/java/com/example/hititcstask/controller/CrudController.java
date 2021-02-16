package com.example.hititcstask.controller;

import com.example.hititcstask.model.*;
import com.example.hititcstask.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import sun.misc.Request;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController

public class CrudController extends AbstractController {
    @Autowired
    @Qualifier("TableServiceImpl")
    TableService tableService;

    @RequestMapping(value = "/newcar", method = RequestMethod.POST,
           produces = "application/json")
    public ResponseEntity<?> newCar(@Valid@RequestBody RequestNewCar requestNewCar, Errors errors){
        try{
            if (errors.hasErrors()) {
                String errorMessage = errors.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
                return new ResponseEntity<>(produceJSONResponse(errorMessage), HttpStatus.BAD_REQUEST);
            }
            System.out.println("asdasd");
            ResponseNewCar responseNewCar=tableService.addNewCar(requestNewCar);
            return new ResponseEntity<>(responseNewCar, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(produceJSONResponse(INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @RequestMapping(value = "/newrentacar", method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<?> newRentACar(@Valid@RequestBody RequestNewRentACar requestNewCar, Errors errors){
        try{
            if (errors.hasErrors()) {
                String errorMessage = errors.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
                return new ResponseEntity<>(produceJSONResponse(errorMessage), HttpStatus.BAD_REQUEST);
            }
            ResponseNewRentACar responseNewRentACar=tableService.addRentACar(requestNewCar);
            return new ResponseEntity<>(responseNewRentACar, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(produceJSONResponse(INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



    @RequestMapping(value = "/updatecarpost", method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<?> updateCar(@Valid@RequestBody RequestUpdateCar requestUpdateCar, Errors errors){
        try{
            if (errors.hasErrors()) {
                String errorMessage = errors.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
                return new ResponseEntity<>(produceJSONResponse(errorMessage), HttpStatus.BAD_REQUEST);
            }
            System.out.println(requestUpdateCar.getCarID());
            tableService.updateCar(requestUpdateCar);
            ResponseUpdateCar responseUpdateCar=new ResponseUpdateCar();
            responseUpdateCar.setResult(OK);
            return new ResponseEntity<>(responseUpdateCar,HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(produceJSONResponse(INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/updaterentacarpost", method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<?> updateRentACar(@Valid@RequestBody RequestUpdateRentACar requestUpdateRentACar, Errors errors){
        try{
            if (errors.hasErrors()) {
                String errorMessage = errors.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
                return new ResponseEntity<>(produceJSONResponse(errorMessage), HttpStatus.BAD_REQUEST);
            }

            tableService.updateRentACar(requestUpdateRentACar);
            ResponseUpdateRentACar responseUpdateRentACar=new ResponseUpdateRentACar();
            responseUpdateRentACar.setResult(OK);
            return new ResponseEntity<>(responseUpdateRentACar,HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(produceJSONResponse(INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/getallrentacarids", method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<?> allRentACarIDS(){
        try{

            ResponseAllRentACar responseAllRentACar=tableService.getAllRentACarIDS();

            return new ResponseEntity<>(responseAllRentACar,HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(produceJSONResponse(INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



}

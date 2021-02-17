package com.example.hititcstask.controller;



import com.example.hititcstask.model.*;
import com.example.hititcstask.service.TableAjaxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;



import javax.validation.Valid;
import java.util.Arrays;



@RestController
public class MainAjaxController {

    private Logger logger = LoggerFactory.getLogger(MainAjaxController.class);

    @Autowired
    @Qualifier("TableAjaxServiceImpl")
    TableAjaxService tableAjaxService;

    private static final String BAD_REQUEST="BAD_REQUEST";

    @PostMapping(value = "/newcar")
    public ResponseEntity<Object> newCar(@Valid@RequestBody RequestNewCar requestNewCar, Errors errors){
        try{
            if (errors.hasErrors()) {
                logger.error(BAD_REQUEST);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            tableAjaxService.addNewCar(requestNewCar);

            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch(Exception e){
            logger.error(String.valueOf(Arrays.stream(e.getStackTrace()).findFirst()));
            return new ResponseEntity<>( HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PostMapping(value = "/newrentacar")
    public ResponseEntity<Object> newRentACar(@Valid@RequestBody RequestNewRentACar requestNewCar, Errors errors){
        try{
            if (errors.hasErrors()) {
                logger.error(BAD_REQUEST);

                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            tableAjaxService.addRentACar(requestNewCar);

            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch(Exception e){
            logger.error(String.valueOf(Arrays.stream(e.getStackTrace()).findFirst()));
            return new ResponseEntity<>( HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PostMapping(value = "/updatecarpost")
    public ResponseEntity<Object> updateCar(@Valid@RequestBody RequestUpdateCar requestUpdateCar, Errors errors){
        try{
            if (errors.hasErrors()) {
                logger.error(BAD_REQUEST);

                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            tableAjaxService.updateCar(requestUpdateCar);



            return new ResponseEntity<>(HttpStatus.ACCEPTED);

        }catch(Exception e){
            logger.error(String.valueOf(Arrays.stream(e.getStackTrace()).findFirst()));
            return new ResponseEntity<>( HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PostMapping(value = "/updaterentacarpost")
    public ResponseEntity<Object> updateRentACar(@Valid@RequestBody RequestUpdateRentACar requestUpdateRentACar, Errors errors){
        try{
            if (errors.hasErrors()) {
                logger.error(BAD_REQUEST);

                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            tableAjaxService.updateRentACar(requestUpdateRentACar);


            return new ResponseEntity<>(HttpStatus.ACCEPTED);

        }catch(Exception e){
            logger.error(String.valueOf(Arrays.stream(e.getStackTrace()).findFirst()));
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @GetMapping(value = "/getallrentacarids", produces = "application/json")
    public ResponseEntity<Object> allRentACarIDS(){
        try{
            ResponseAllRentACar responseAllRentACar=tableAjaxService.getAllRentACarIDS();

            return new ResponseEntity<>(responseAllRentACar,HttpStatus.ACCEPTED);


        }catch(Exception e){
            logger.error(String.valueOf(Arrays.stream(e.getStackTrace()).findFirst()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }




}

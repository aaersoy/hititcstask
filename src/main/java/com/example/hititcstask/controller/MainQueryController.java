package com.example.hititcstask.controller;

import com.example.hititcstask.entity.Car;
import com.example.hititcstask.entity.RentACar;
import com.example.hititcstask.service.TableQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import java.util.*;

@Controller
public class MainQueryController {

    private Logger logger = LoggerFactory.getLogger(MainQueryController.class);


    @Autowired
    @Qualifier("TableQueryServiceImpl")
    TableQueryService tableQueryService;

    public static final String ERROR_VIEW_STR = "error";
    public static final String INDEX_VIEW_STR = "index";
    public static final String UPDATE_CAR_VIEW_STR = "update_car";
    public static final String UPDATE_RENT_A_CAR_VIEW_STR = "update_rent_a_car";
    public static final String MESSAGE_STR = "message";
    public static final String MODEL_DATA_STR = "modelData";
    public static final String CAR_TABLE_STR = "left";
    public static final String RENT_A_CAR_TABLE_STR = "right";
    public static final String RENT_A_CAR_ID_STR = "rentACarID";
    public static final String CAR_ID = "carID";
    public static final String RENT_A_CAR_NAME = "rentACarName";
    public static final String PLATE_CODE_STR = "plateCode";
    public static final String CAR_COUNT_STR = "carCount";
    public static final String RENTED_STR = "rented";
    public static final String OK = "Ok";


    @RequestMapping("/")
    public ModelAndView index() {
        return currentValues();
    }

    @RequestMapping("/updatecar")
    public ModelAndView updateCar(@RequestParam Long id) {
        try {
            ModelAndView model = new ModelAndView(UPDATE_CAR_VIEW_STR);

            Car car = tableQueryService.getCar(id);

            Map<String, Object> modelData = new HashMap<>();
            modelData.put(CAR_ID, car.getCarID());
            modelData.put(PLATE_CODE_STR, car.getPlateCode());
            modelData.put(RENT_A_CAR_ID_STR, car.getRentACar().getRentACarID());
            model.addObject(MODEL_DATA_STR, modelData);
            return model;
        } catch (Exception e) {
            logger.error(String.valueOf(Arrays.stream(e.getStackTrace()).findFirst()));
            return errorPage("Araç bilgileri güncellenemedi.");
        }
    }

    @RequestMapping("/updaterentacar")
    public ModelAndView updateRentACar(@RequestParam Long id) {
        try {
            ModelAndView model = new ModelAndView(UPDATE_RENT_A_CAR_VIEW_STR);
            RentACar rentACar = tableQueryService.getRentACar(id);

            Map<String, Object> modelData = new HashMap<>();
            modelData.put(RENT_A_CAR_ID_STR, rentACar.getRentACarID());
            modelData.put(RENT_A_CAR_NAME, rentACar.getRentACarName());
            model.addObject(MODEL_DATA_STR, modelData);


            return model;
        } catch (Exception e) {
            logger.error(String.valueOf(Arrays.stream(e.getStackTrace()).findFirst()));
            return errorPage("Rent A Car bilgileri güncellenemedi.");
        }
    }

    @RequestMapping("/removecar")
    public ModelAndView removeCar(@RequestParam Long id) {

        try {
            tableQueryService.removeCar(id);
            return currentValues();
        } catch (Exception e) {
            logger.error(String.valueOf(Arrays.stream(e.getStackTrace()).findFirst()));
            return errorPage("Araç silinemedi.");
        }


    }

    @RequestMapping("/removerentacar")
    public ModelAndView removeRentACar(@RequestParam Long id) {

        try {
            tableQueryService.removeRentACar(id);
            return currentValues();
        } catch (Exception e) {
            logger.error(String.valueOf(Arrays.stream(e.getStackTrace()).findFirst()));
            return errorPage("Rent A Car silinemedi.");
        }


    }

    @RequestMapping("/rentcar")
    public ModelAndView rentCar(@RequestParam Long id) {

        try {
            tableQueryService.rentCar(id);
            return currentValues();
        } catch (Exception e) {
            logger.error(String.valueOf(Arrays.stream(e.getStackTrace()).findFirst()));
            return errorPage("Araç kiralanamadı.");
        }


    }

    @RequestMapping("/searchcar")
    public ModelAndView searchCar(@RequestParam String key) {

        try {
            ModelAndView model = new ModelAndView(INDEX_VIEW_STR);
            Map<String, List<Map<String, Object>>> modelData = new HashMap<>();
            List<Map<String, Object>> left = new ArrayList<>();
            List<Map<String, Object>> right = new ArrayList<>();
            tableQueryService.searchCarWithKey(key, left , CAR_ID, PLATE_CODE_STR, RENTED_STR);
            tableQueryService.getRentACarData(right, RENT_A_CAR_NAME, CAR_COUNT_STR);
            modelData.put(CAR_TABLE_STR, left);
            modelData.put(RENT_A_CAR_TABLE_STR, right);
            model.addObject(MODEL_DATA_STR, modelData);

            return model;
        } catch (Exception e) {
            logger.error(String.valueOf(Arrays.stream(e.getStackTrace()).findFirst()));
            return errorPage("Araç arama esnasında bir hata oluştu.");
        }

    }

    @RequestMapping("/searchrentacar")
    public ModelAndView searchRentACar(@RequestParam String key) {

        try {
            ModelAndView model = new ModelAndView(INDEX_VIEW_STR);
            Map<String, List<Map<String, Object>>> modelData = new HashMap<>();
            List<Map<String, Object>> left = new ArrayList<>();
            List<Map<String, Object>> right = new ArrayList<>();
            tableQueryService.getCarData(left, CAR_ID, PLATE_CODE_STR, RENTED_STR);
            tableQueryService.searchRentACarWithKey(key, right);
            modelData.put(CAR_TABLE_STR, left);
            modelData.put(RENT_A_CAR_TABLE_STR, right);
            model.addObject(MODEL_DATA_STR, modelData);

            return model;
        } catch (Exception e) {
            logger.error(String.valueOf(Arrays.stream(e.getStackTrace()).findFirst()));
            return errorPage("Rent A Car arama esnasında bir hata oluştu.");
        }


    }

    @RequestMapping("/releasecar")
    public ModelAndView releaseCar(@RequestParam Long id) {

        try {
            tableQueryService.releaseCar(id);

            return currentValues();
        } catch (Exception e) {
            logger.error(String.valueOf(Arrays.stream(e.getStackTrace()).findFirst()));
            return errorPage("Araç bırakılamadı.");
        }


    }



    public ModelAndView errorPage(String message){
        ModelAndView errorModel = new ModelAndView(ERROR_VIEW_STR);
        errorModel.addObject(MESSAGE_STR, message);
        return errorModel;
    }


    public ModelAndView currentValues(){

        try {
            ModelAndView model = new ModelAndView(INDEX_VIEW_STR);

            Map<String, List<Map<String, Object>>> modelData = new HashMap<>();
            List<Map<String, Object>> left = new ArrayList<>();
            List<Map<String, Object>> right = new ArrayList<>();

            tableQueryService.getCarData(left, CAR_ID, PLATE_CODE_STR, RENTED_STR);
            tableQueryService.getRentACarData(right, RENT_A_CAR_NAME, CAR_COUNT_STR);

            modelData.put(CAR_TABLE_STR, left);
            modelData.put(RENT_A_CAR_TABLE_STR, right);
            model.addObject(MODEL_DATA_STR, modelData);


            return model;
        } catch (Exception e) {
            logger.error(String.valueOf(Arrays.stream(e.getStackTrace()).findFirst()));
            return errorPage("Sayfa yüklenirken bir hata oluştu.");
        }

    }


}

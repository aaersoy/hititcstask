package com.example.hititcstask.controller;

import com.example.hititcstask.entity.Car;
import com.example.hititcstask.entity.RentACar;
import com.example.hititcstask.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private static String errorViewStr = "error";
    private static String indexViewStr = "index";
    private static String messageStr = "message";
    private static String modelDataStr = "modelData";
    private static String carTableStr = "left";
    private static String rentACarTableStr = "right";
    private static String rentACarIDStr = "rentACarID";
    @Autowired
    @Qualifier("TableServiceImpl")
    TableService tableService;

    @RequestMapping("/")
    public ModelAndView index() {
        try {
            ModelAndView model = new ModelAndView(indexViewStr);
            Map<String, List<Map<String, Object>>> modelData = new HashMap<>();
            List<Map<String, Object>> left = new ArrayList<>();
            List<Map<String, Object>> right = new ArrayList<>();
            tableService.getCarData(left);
            tableService.getRentACarData(right);
            modelData.put(carTableStr, left);
            modelData.put(rentACarTableStr, right);
            model.addObject(modelDataStr, modelData);

            return model;
        } catch (Exception e) {
            ModelAndView errorModel = new ModelAndView(errorViewStr);
            errorModel.addObject(messageStr, "Sayfa yüklenirken bir hata oluştu.");
            return errorModel;
        }
    }

    @RequestMapping("/updatecar")
    public ModelAndView updateCar(@RequestParam Long id) {
        try {
            ModelAndView model = new ModelAndView("update_car");
            Car car = tableService.getCar(id);

            Map<String, Object> modelData = new HashMap<>();
            modelData.put("carID", car.getCarID());
            modelData.put("plateCode", car.getPlateCode());
            if (car.getRentACar() != null)
                modelData.put(rentACarIDStr, car.getRentACar().getRentACarID());
            else
                modelData.put(rentACarIDStr, Long.valueOf(-1));
            model.addObject(modelDataStr, modelData);

            return model;
        } catch (Exception e) {
            ModelAndView errorModel = new ModelAndView(errorViewStr);
            errorModel.addObject(messageStr, "Araç bilgileri güncellenemedi.");
            return errorModel;
        }
    }

    @RequestMapping("/updaterentacar")
    public ModelAndView updateRentACar(@RequestParam Long id) {
        try {
            ModelAndView model = new ModelAndView("update_rent_a_car");
            RentACar rentACar = tableService.getRentACar(id);

            Map<String, Object> modelData = new HashMap<>();
            modelData.put(rentACarIDStr, rentACar.getRentACarID());
            modelData.put("rentACarName", rentACar.getRentACarName());
            model.addObject(modelDataStr, modelData);

            return model;
        } catch (Exception e) {
            ModelAndView errorModel = new ModelAndView(errorViewStr);
            errorModel.addObject(messageStr, "Rent A Car bilgileri güncellenemedi.");
            return errorModel;
        }
    }

    @RequestMapping("/removecar")
    public ModelAndView removeCar(@RequestParam Long id) {

        try {
            ModelAndView model = new ModelAndView(indexViewStr);
            tableService.removeCar(id);
            Map<String, List<Map<String, Object>>> modelData = new HashMap<>();
            List<Map<String, Object>> left = new ArrayList<>();
            List<Map<String, Object>> right = new ArrayList<>();
            tableService.getCarData(left);
            tableService.getRentACarData(right);
            modelData.put(carTableStr, left);
            modelData.put(rentACarTableStr, right);

            model.addObject(modelDataStr, modelData);
            return model;
        } catch (Exception e) {
            ModelAndView errorModel = new ModelAndView(errorViewStr);
            errorModel.addObject(messageStr, "Araç silinemedi.");
            return errorModel;
        }


    }

    @RequestMapping("/removerentacar")
    public ModelAndView removeRentACar(@RequestParam Long id) {

        try {
            ModelAndView model = new ModelAndView(indexViewStr);
            tableService.removeRentACar(id);
            Map<String, List<Map<String, Object>>> modelData = new HashMap<>();
            List<Map<String, Object>> left = new ArrayList<>();
            List<Map<String, Object>> right = new ArrayList<>();
            tableService.getCarData(left);
            tableService.getRentACarData(right);
            modelData.put(carTableStr, left);
            modelData.put(rentACarTableStr, right);
            model.addObject(modelDataStr, modelData);
            return model;

        } catch (Exception e) {
            ModelAndView errorModel = new ModelAndView(errorViewStr);
            errorModel.addObject(messageStr, "Rent A Car silinemedi.");
            return errorModel;
        }


    }

    @RequestMapping("/rentcar")
    public ModelAndView rentCar(@RequestParam Long id) {

        try {
            ModelAndView model = new ModelAndView(indexViewStr);
            tableService.rentCar(id);
            Map<String, List<Map<String, Object>>> modelData = new HashMap<>();
            List<Map<String, Object>> left = new ArrayList<>();
            List<Map<String, Object>> right = new ArrayList<>();
            tableService.getCarData(left);
            tableService.getRentACarData(right);
            modelData.put(carTableStr, left);
            modelData.put(rentACarTableStr, right);

            model.addObject(modelDataStr, modelData);
            return model;
        } catch (Exception e) {
            ModelAndView errorModel = new ModelAndView(errorViewStr);
            errorModel.addObject(messageStr, "Araç kiralanamadı.");
            return errorModel;
        }


    }

    @RequestMapping("/searchcar")
    public ModelAndView searchCar(@RequestParam String key) {

        try {
            ModelAndView model = new ModelAndView(indexViewStr);
            Map<String, List<Map<String, Object>>> modelData = new HashMap<>();
            List<Map<String, Object>> left = new ArrayList<>();
            List<Map<String, Object>> right = new ArrayList<>();
            tableService.searchCarWithKey(key, left);
            tableService.getRentACarData(right);
            modelData.put(carTableStr, left);
            modelData.put(rentACarTableStr, right);
            model.addObject(modelDataStr, modelData);
            return model;
        } catch (Exception e) {
            ModelAndView errorModel = new ModelAndView(errorViewStr);
            errorModel.addObject(messageStr, "Araç arama esnasında bir hata oluştu.");
            return errorModel;
        }

    }

    @RequestMapping("/searchrentacar")
    public ModelAndView searchRentACar(@RequestParam String key) {

        try {
            ModelAndView model = new ModelAndView(indexViewStr);
            Map<String, List<Map<String, Object>>> modelData = new HashMap<>();
            List<Map<String, Object>> left = new ArrayList<>();
            List<Map<String, Object>> right = new ArrayList<>();
            tableService.getCarData(left);
            tableService.searchRentACarWithKey(key, right);
            modelData.put(carTableStr, left);
            modelData.put(rentACarTableStr, right);
            model.addObject(modelDataStr, modelData);
            return model;
        } catch (Exception e) {
            ModelAndView errorModel = new ModelAndView(errorViewStr);
            errorModel.addObject(messageStr, "Rent A Car arama esnasında bir hata oluştu.");
            return errorModel;
        }


    }

    @RequestMapping("/releasecar")
    public ModelAndView releaseCar(@RequestParam Long id) {

        try {
            ModelAndView model = new ModelAndView(indexViewStr);
            tableService.releaseCar(id);
            Map<String, List<Map<String, Object>>> modelData = new HashMap<>();
            List<Map<String, Object>> left = new ArrayList<>();
            List<Map<String, Object>> right = new ArrayList<>();
            tableService.getCarData(left);
            tableService.getRentACarData(right);
            modelData.put(carTableStr, left);
            modelData.put(rentACarTableStr, right);

            model.addObject(modelDataStr, modelData);
            return model;
        } catch (Exception e) {
            ModelAndView errorModel = new ModelAndView(errorViewStr);
            errorModel.addObject(messageStr, "Araç bırakılamadı.");
            return errorModel;
        }


    }


}

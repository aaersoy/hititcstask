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
import java.util.Map;
import java.util.List;

@Controller
public class MainController{

    @Autowired
    @Qualifier("TableServiceImpl")
    TableService tableService;

    @RequestMapping("/")
    public ModelAndView index(){
        ModelAndView model=new ModelAndView("index");
        Map<String, List<Map<String,Object>>> modelData=new HashMap<>();
        List<Map<String,Object>> left= new ArrayList<>();
        List<Map<String,Object>> right= new ArrayList<>();
        tableService.getCarData(left);
        tableService.getRentACarData(right);
        modelData.put("left",left);
        modelData.put("right",right);
        if(modelData.get(right)!=null)
            System.out.println(modelData.get(right).get(0));
        model.addObject("modelData",modelData);

        return model;
    }

    @RequestMapping("/updatecar")
    public ModelAndView updateCar(@RequestParam Long id){
        ModelAndView model=new ModelAndView("update_car");
        Car car=tableService.getCar(id);

        Map<String,Object> modelData= new HashMap<>();
        modelData.put("carID",car.getCarID());
        modelData.put("plateCode", car.getPlateCode());
        if(car.getRentACar()!=null)
            modelData.put("rentACarID", car.getRentACar().getRentACarID());
        else
            modelData.put("rentACarID", new Long(-1));
        model.addObject("modelData",modelData);

        return model;
    }

    @RequestMapping("/updaterentacar")
    public ModelAndView updateRentACar(@RequestParam Long id){
        ModelAndView model=new ModelAndView("update_rent_a_car");
        RentACar rentACar=tableService.getRentACar(id);

        Map<String,Object> modelData= new HashMap<>();
        modelData.put("rentACarID", rentACar.getRentACarID());
        modelData.put("rentACarName", rentACar.getRentACarName());
        model.addObject("modelData",modelData);

        return model;
    }



}

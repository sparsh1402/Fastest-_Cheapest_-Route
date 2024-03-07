package com.mmt.mmtApp.controller;


import com.mmt.mmtApp.models.City;
import com.mmt.mmtApp.models.Route;
import com.mmt.mmtApp.service.RouteDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/routes")  // Define a base URL for this controller
public class RoutesController {

    @Autowired
    public RouteDataService routeDataService;
    @GetMapping("/{source}/{destination}/allRoutes")
    @ResponseBody
    public List<String> showAllRoutes(@PathVariable String source, @PathVariable String destination) {
        List<String> allRoutes;
        routeDataService.getCityData();
        routeDataService.getAllRouteData();
        allRoutes = routeDataService.showAllRoutes(source, destination);
        return allRoutes;
    }
    @GetMapping("/{source}/{destination}/cheapest")
    @ResponseBody
    public List<Object> showCheapest(@PathVariable String source, @PathVariable String destination) {
        List<Object> allRoutes;
        routeDataService.getCityData();
        routeDataService.getAllRouteData();
        System.out.println(routeDataService.getCityData());
        System.out.println(routeDataService.getAllRouteData());
        allRoutes = routeDataService.showCheapestRoute(source, destination);

        return allRoutes;
    }

//    @GetMapping("/cheapest")
//    @ResponseBody
//    public List<Object> showCheapest(@RequestParam String source ,@RequestParam String destination) {
//
//        List<Object> allRoutes;
//        routeDataService.getCityData();
//        routeDataService.getAllRouteData();
//        System.out.println(routeDataService.getCityData());
//        System.out.println(routeDataService.getAllRouteData());
//        allRoutes = routeDataService.showCheapestRoute(source, destination);
//
//        return allRoutes;
//    }





    @GetMapping("/{source}/{destination}/fastest")
    @ResponseBody
    public List<Object> showFastest(@PathVariable String source, @PathVariable String destination) {
        List<Object> allRoutes;
        routeDataService.getCityData();
        routeDataService.getAllRouteData();


        allRoutes = routeDataService.showFastestRoute(source, destination);
        return allRoutes;
    }


}

package com.mmt.mmtApp.service;
import com.mmt.mmtApp.models.Route;
import com.mmt.mmtApp.models.City;
import com.mmt.mmtApp.repository.CityRepository;
import com.mmt.mmtApp.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class RouteDataService {
    @Autowired
    public RouteRepository routeRepository;
    @Autowired
    public CityRepository cityRepository;
    public class Structure1 {
        public double TimeInHrs;
        public String SourceCity;
        public String destinationCity;

        public String ModeOfTransport;

        public double Fare;
        public Structure1(String sr, String de,String mode,double timeTravel,double Fare) {
            this.SourceCity = sr;
            this.destinationCity = de;
            this.ModeOfTransport = mode;
            this.TimeInHrs = timeTravel;
            this.Fare = Fare;
        }



//        private int field2;

        // Constructors, getters, setters
    }

    public class Structure2 {

        public double TotalTime;
        public double TotalFare;


        public Structure2(double TotalTime,double TotalFare){
            this.TotalFare = TotalFare;
            this.TotalTime = TotalTime;

        }

        // Constructors, getters, setters
    }
    public static List<Route> routeData;
    public static List<City> cityData;
    public static Map<Long,String> cityMap = new HashMap<>();



    public List<Route> getAllRouteData() {
//        System.out.println(routeRepository.findAll());
        return routeData = routeRepository.findAll();
    }

    public List<City> getCityData(){
//        System.out.println(cityRepository.findAll());
        cityData = cityRepository.findAll();
//        System.out.println(cityData);
        return cityRepository.findAll();
    }




    public static void printAdjacencyList(Map<Long, List<CityEdge>> adjacencyList) {
        for (Map.Entry<Long, List<CityEdge>> entry : adjacencyList.entrySet()) {
            Long cityId = entry.getKey();
            List<CityEdge> edges = entry.getValue();

            System.out.print("City ID " + cityId + " is connected to: ");

            for (CityEdge edge : edges) {
             System.out.print(cityId + " -> " + edge.getDestinationCityId() + " ");
            }

            System.out.println(); // Move to the next line for the next city
        }
    }
    public static CityAdjacencyList constructAdjacencyList(List<City> cityData, List<Route> routeData) {
        CityAdjacencyList adjacencyList = new CityAdjacencyList();

        for (Route route : routeData) {
            Long sourceCityId = route.getSourceCityId();
            Long destinationCityId = route.getDestinationCityId();
            double travelTime = route.getTravelTime();
            double fare = route.getFare();

            adjacencyList.addEdge(sourceCityId, destinationCityId, travelTime, fare);

            }

        return adjacencyList;
    }


    public static List<List<Long>> findAllRouteIds(Long sourceCityId, Long destinationCityId,Map<Long, List<CityEdge>> adj) {
        List<List<Long>> allRouteIds = new ArrayList<>();
        List<Long> currentRouteIds = new ArrayList<>();
        Set<Long> visitedCityIds = new HashSet<>();
//        printAdjacencyList(adj);
        findRouteIds(sourceCityId, destinationCityId, currentRouteIds, allRouteIds, visitedCityIds,adj);
        return allRouteIds;
    }

    private static void findRouteIds(
            Long currentCityId,
            Long destinationCityId,
            List<Long> currentRouteIds,
            List<List<Long>> allRouteIds,
            Set<Long> visitedCityIds,
            Map<Long, List<CityEdge>> adj
    ) {
        if (visitedCityIds.contains(currentCityId)) {
            // City has already been visited, return
            return;
        }

        currentRouteIds.add(currentCityId);
        visitedCityIds.add(currentCityId);

        if (currentCityId.equals(destinationCityId)) {
            allRouteIds.add(new ArrayList<>(currentRouteIds));
        } else {
            if (adj.get(currentCityId) != null) {
                for (CityEdge edge : adj.get(currentCityId)) {
                    Long nextCityId = edge.getDestinationCityId();
                    findRouteIds(nextCityId, destinationCityId, currentRouteIds, allRouteIds, visitedCityIds, adj);
                }
            }
        }
        visitedCityIds.remove(currentCityId);
        currentRouteIds.remove(currentRouteIds.size() - 1);
    }


//    private static void findRouteIds(Long currentCityId, Long destinationCityId, List<Long> currentRouteIds, List<List<Long>> allRouteIds, Set<Long> visitedCityIds,Map<Long, List<CityEdge>> adj) {
//        currentRouteIds.add(currentCityId);
//        visitedCityIds.add(currentCityId);
//        printAdjacencyList(adj);
//        if (currentCityId.equals(destinationCityId)) {
//
//            allRouteIds.add(new ArrayList<>(currentRouteIds));
//        } else {
//            if(adj.get(currentCityId) == null){
//                System.out.println("Hi");
//                return;
//            }
//
//
//            for (CityEdge edge : adj.get(currentCityId)) {
////				System.out.println("in else1");
//                Long nextCityId = edge.getDestinationCityId();
//                if (!visitedCityIds.contains(nextCityId)) {
//                    findRouteIds(nextCityId, destinationCityId, currentRouteIds, allRouteIds, visitedCityIds,adj);
//                }
//            }
//        }
//
//        currentRouteIds.remove(currentRouteIds.size() - 1);
//        visitedCityIds.remove(currentCityId);
//    }

    public  static  void findFareandTime(Long source , Long dest , List<Route> routeData, List<Double> fare , List<String> mode, List<Double> travelTime, Route minFareid , Route minTimeid){
        Double miniFare = 100000.0;


        Double miniTime = 100000.0;

        for(Route r : routeData){
            if(Objects.equals(r.getSourceCityId(), source) && Objects.equals(r.getDestinationCityId(), dest)){
//				System.out.println(r.getSourceCityId()+" "+r.getDestinationCityId());

                if(miniFare>r.getFare()){
                    minFareid.setId(r.getId());
                    minFareid.setDestination(r.getDestination());
                    minFareid.setSource(r.getSource());
                    minFareid.setTravelTimeInHours(r.getTravelTimeInHours());
                    minFareid.setMode(r.getMode());
                    minFareid.setFare(r.getFare());
                    miniFare = r.getFare();
                }
//				System.out.println(miniTime+ " "+ r.getTravelTime());
                if(miniTime>r.getTravelTime()){
                    minTimeid.setId(r.getId());
                    minTimeid.setDestination(r.getDestination());
                    minTimeid.setSource(r.getSource());
                    minTimeid.setTravelTimeInHours(r.getTravelTimeInHours());
                    minTimeid.setMode(r.getMode());
                    minTimeid.setFare(r.getFare());
                    miniTime = r.getTravelTime();
                }
                fare.add(r.getFare());
                mode.add(r.getMode());
                travelTime.add(r.getTravelTime());


            }
        }

//		System.out.println("Mini" + minFareid.getId());

//		for(int i = 0;i< fare.size();i++){
//			System.out.println(mode.get(i) +" "+ fare.get(i));
//		}




    }


    public List<Object> showCheapestRoute(String src, String des) {

        Long sourceCityId = 1L;
        Long destinationCityId = 6L;

        for (City city : cityData) {
            if(src.equals(city.getName())){
                sourceCityId = city.getId();
            }
            if(des.equals(city.getName())){
                destinationCityId = city.getId();
            }
            cityMap.put(city.getId(), city.getName());
        }

        System.out.println(sourceCityId+" "+ destinationCityId);




        CityAdjacencyList adjacencyList1 = new CityAdjacencyList();
        adjacencyList1 = constructAdjacencyList(cityData, routeData);
        Map<Long, List<CityEdge>> adjacencyList= adjacencyList1.adjacencyList;
        printAdjacencyList(adjacencyList);
        List<List<Long>> allRouteIds = findAllRouteIds(sourceCityId, destinationCityId,adjacencyList);
        Iterator<List<Long>> outerIterator = allRouteIds.iterator();
//        List<String> tempFare = new ArrayList<>();
//        List<List<String>> tempFare = new ArrayList<>();
        List<List<Route>> tempFare = new ArrayList<>();
        double minimumFare= 100000.0;
        while (outerIterator.hasNext()) {
            List<Long> innerList = outerIterator.next();
            Long previousRouteId = null;
            double miniFare = 0.0;
            double miniTime = 0.0;
            List<Route> miniFareRoute = new ArrayList<>();
            List<Route> miniTimeRoute = new ArrayList<>();
            for (Long routeId : innerList) {
                if (previousRouteId != null) {
                    List<Double> fare = new ArrayList<>();
                    List<String> mode = new ArrayList<>();
                    List<Double> travelTime = new ArrayList<>();

//                    System.out.println(previousRouteId + " " + routeId);

                    Route minFareid=new Route();
                    Route minTimeid = new Route();
                    findFareandTime(previousRouteId,routeId,routeData,fare,mode,travelTime,minFareid,minTimeid);

//                    for(int i = 0;i < fare.size();i++){
//                        System.out.println(mode.get(i) +" "+ fare.get(i)+" "+travelTime.get(i));
//
//                    }


                    miniFareRoute.add(minFareid);
                    miniFare += minFareid.getFare();
//                    System.out.println(min);
//                    System.out.println(miniFare);
                    miniTime += minFareid.getTravelTime();



                } else {
//                    System.out.println("Route ID: " + routeId);
                }

                previousRouteId = routeId;
            }

            System.out.println(miniFareRoute);
//            for(Route x: miniFareRoute){
//                System.out.println(cityIdToNameMap.get(x.getSourceCityId())+ " "+ cityIdToNameMap.get(x.getDestinationCityId())+" "+fareMap.get(x.getId())+ " "+ x.getMode());
//            }



            // Initialize a StringBuilder to construct the string
            StringBuilder routeInfoBuilder = new StringBuilder();
            StringBuilder routeInfoBuilder1 = new StringBuilder();
            int i =1;
            List<String> innerRoute = new ArrayList<>();
            System.out.println(miniFareRoute);


//            showRoute showRoute = new showRoute

            List<Route> res= new ArrayList<>();

            for (Route x : miniFareRoute) {
                // Append the data for each route to the StringBuilder
                routeInfoBuilder.append("Route ").append(i).append(cityMap.get(x.getSourceCityId()))
                        .append(" ")
                        .append(cityMap.get(x.getDestinationCityId()))
                        .append(" ")
                        .append(x.getFare())
                        .append(" ")
                        .append(x.getMode())

                        .append("\n");  // Add a newline to separate routes
                String s = routeInfoBuilder.toString();
                innerRoute.add(s);
                i++;
                res.add(x);
            }



//            routeInfoBuilder.append(" Total time : ").append(miniTime).append(" Total Fare ").append(miniFare);

            routeInfoBuilder1.append(" Total time : ").append(miniTime).append(" Total Fare ").append(miniFare);
            String s = routeInfoBuilder1.toString();
            innerRoute.add(s);
            String routeInfo = routeInfoBuilder.toString();  // Convert the StringBuilder to a string
            if(minimumFare>miniFare){
                if(tempFare.isEmpty()){
                    tempFare.add(res);
                }
                else{
                    tempFare.clear();
                    tempFare.add(res);


                }
                minimumFare = miniFare;
            }



        }

        double totalTime = 0.0;
        double totalFare = 0.0;

        List<Object> data = new ArrayList<>();
        for (List<Route> routeList : tempFare) {
            String sr = null;
            String de = null;
            String mode = null;
            double travelTime = 0.0;
            double fare = 0.0;
            for (Route route : routeList) {
                // Access individual Route object within each inner list
                System.out.println(route);
                sr = cityMap.get(route.getSourceCityId());

                de = cityMap.get(route.getDestination());
                mode = route.getMode();
                travelTime = route.getTravelTime();
                fare = route.getFare();
                totalTime +=travelTime;
                totalFare +=fare;


                data.add(new Structure1(sr,de,mode,travelTime,fare));


                // Add logic to format and display the data as needed
            }

        }

        data.add(new Structure2(totalTime,totalFare));






        return data; // Return the name of the view template
    }


    public List<Object> showFastestRoute(String src , String des) {
        Long sourceCityId = 1L; // Replace with the actual source city ID
        Long destinationCityId = 3L; // Replace with the actual destination city ID
        for (City city : cityData) {
            if(city.getName().equals(src)){
                sourceCityId = city.getId();
            }
            if(city.getName().equals(des)){
                destinationCityId = city.getId();
            }
            cityMap.put(city.getId(), city.getName());
        }




        CityAdjacencyList adjacencyList1 = new CityAdjacencyList();
        adjacencyList1 = constructAdjacencyList(cityData, routeData);
        Map<Long, List<CityEdge>> adjacencyList= adjacencyList1.adjacencyList;

        List<List<Long>> allRouteIds = findAllRouteIds(sourceCityId, destinationCityId,adjacencyList);
        System.out.println(allRouteIds);
        Iterator<List<Long>> outerIterator = allRouteIds.iterator();
        List<List<Route>> tempFare = new ArrayList<>();
        double minimumFare= 100000.0;
        double minimumTime = 100000.0;
        while (outerIterator.hasNext()) {
            List<Long> innerList = outerIterator.next();
            Long previousRouteId = null;
            double miniFare = 0.0;
            double miniTime = 0.0;
            List<Route> miniFareRoute = new ArrayList<>();
            List<Route> miniTimeRoute = new ArrayList<>();
            for (Long routeId : innerList) {
                if (previousRouteId != null) {
                    List<Double> fare = new ArrayList<>();
                    List<String> mode = new ArrayList<>();
                    List<Double> travelTime = new ArrayList<>();


                    Route minFareid=new Route();
                    Route minTimeid = new Route();
                    findFareandTime(previousRouteId,routeId,routeData,fare,mode,travelTime,minFareid,minTimeid);


                    miniFareRoute.add(minFareid);
                    miniTimeRoute.add(minTimeid);
                    miniFare += minTimeid.getFare();
                    miniTime += minTimeid.getTravelTime();


                } else {
//                    System.out.println("Route ID: " + routeId);
                }

                previousRouteId = routeId;
            }

//

            List<Route> res= new ArrayList<>();


            // Initialize a StringBuilder to construct the string
            StringBuilder routeInfoBuilder = new StringBuilder();
            int i =1;
            for (Route x : miniTimeRoute) {
                // Append the data for each route to the StringBuilder
                routeInfoBuilder.append("Route ").append(i).append(cityMap.get(x.getSourceCityId()))
                        .append(" ")
                        .append(cityMap.get(x.getDestinationCityId()))
                        .append(" ")
                        .append(x.getFare())
                        .append(" ")
                        .append(x.getMode())
                        .append(x.getTravelTimeInHours())
                        .append("\n");  // Add a newline to separate routes
                        res.add(x);
                i++;
            }

            routeInfoBuilder.append("Total Time ").append(miniTime).append("Total Fare ").append(miniFare);


            String routeInfo = routeInfoBuilder.toString();  // Convert the StringBuilder to a string
            System.out.println(minimumTime+" "+miniTime);
            if(minimumTime>miniTime){
                if(tempFare.isEmpty()){
                    System.out.println("in mini");
                    tempFare.add(res);

                }
                else{
                    System.out.println("in mini");
                    tempFare.clear();
                    tempFare.add(res);


                }
                minimumTime = miniTime;
            }


        }



        double totalTime = 0.0;
        double totalFare = 0.0;

        List<Object> data = new ArrayList<>();
        for (List<Route> routeList : tempFare) {
            String sr = null;
            String de = null;
            String mode = null;
            double travelTime = 0.0;
            double fare = 0.0;
            for (Route route : routeList) {
                // Access individual Route object within each inner list
                System.out.println(route);
                sr = cityMap.get(route.getSourceCityId());

                de = cityMap.get(route.getDestination());
                mode = route.getMode();
                travelTime = route.getTravelTime();
                fare = route.getFare();
                totalTime += travelTime;
                totalFare += fare;


                data.add(new Structure1(sr, de, mode, travelTime, fare));


                // Add logic to format and display the data as needed
            }

        }

        data.add(new Structure2(totalTime,totalFare));




        // Add logic to display the cheapest rout





        return data; // Return the name of the view template
    }

    private List<Route> getRouteDetails(List<Long> routeIds) {
        List<Route> routeDetails = new ArrayList<>();
        for (Long routeId : routeIds) {
            Route route = routeRepository.findById(routeId).orElse(null);
            if (route != null) {
                routeDetails.add(route);
            }
        }
        return routeDetails;
    }

    private String formatRoutes(List<Route> routeDetails) {
        StringBuilder formattedRoutes = new StringBuilder();
        for(City c: cityData){
            cityMap.put(c.getId(),c.getName());
        }
        for (Route route : routeDetails) {
            formattedRoutes.append("Route ID: ").append(route.getId()).append("  ");
            formattedRoutes.append("Source City: ").append(cityMap.get(route.getSourceCityId())).append("\n");
            formattedRoutes.append("Destination City: ").append(cityMap.get(route.getDestinationCityId())).append("\n");
            formattedRoutes.append("Mode of Transport: ").append(route.getMode()).append("\n");
            formattedRoutes.append("Travel Time: ").append(route.getTravelTime()).append(" hours\n");
            formattedRoutes.append("Fare: $").append(route.getFare()).append("\n\n");
        }
        return formattedRoutes.toString();
    }

    public List<String> showAllRoutes(String src, String dest) {
        // Find the corresponding city IDs for src and dest using cityRepository
        City sourceCity = cityRepository.findByName(src);
        City destinationCity = cityRepository.findByName(dest);
        Long sourceCityId = 0L;
        Long destinationCityId =0L;

        for(City c: cityData){
            if(Objects.equals(c.getName(), src)){
                sourceCityId = c.getId();
            }
            if(Objects.equals(c.getName(), dest)){
                destinationCityId = c.getId();
            }
        }



        // Create an adjacency list of cities and their routes
        CityAdjacencyList adjacencyList1 = constructAdjacencyList(cityData, routeData);
        Map<Long, List<CityEdge>> adjacencyList= adjacencyList1.adjacencyList;
        System.out.println(adjacencyList1);
        printAdjacencyList(adjacencyList);
//        // Use graph traversal to find all routes
        List<List<Long>> allRouteIds = findAllRouteIds(sourceCityId, destinationCityId, adjacencyList);

        System.out.println(allRouteIds);
        List<String> allRoutes = new ArrayList<>();
        for (List<Long> routeIds : allRouteIds) {
            List<Route> routeDetails = getRouteDetails(routeIds); // Implement this method
            allRoutes.add(formatRoutes(routeDetails)); // Implement this method
        }
        return allRoutes;

    }


//    public List<String> showFastestRoute(String src , String des) {
//        Long sourceCityId = 1L; // Replace with the actual source city ID
//        Long destinationCityId = 3L; // Replace with the actual destination city ID
//
//
//
//        Map<Long, String> cityIdToNameMap = new HashMap<>();
//
//        for (City city : cityData) {
//
//            if(city.getName().equals(src)){
//
//                sourceCityId = city.getId();
//
//
//
//            }
//            if(city.getName().equals(des)){
//                destinationCityId = city.getId();
//            }
//            cityIdToNameMap.put(city.getId(), city.getName());
//        }
//
//
//
//
//
//
//
////        Map<Long, Double> fareMap = new HashMap<>();
////
////        for(Fare fa: fareData){
////            fareMap.put(fa.route,fa.fare);
////        }
//
//
//
//        CityAdjacencyList adjacencyList1 = new CityAdjacencyList();
//        adjacencyList1 = constructAdjacencyList(cityData, routeData);
//        Map<Long, List<CityEdge>> adjacencyList= adjacencyList1.adjacencyList;
//
//        System.out.println(adjacencyList);
////
//        List<List<Long>> allRouteIds = findAllRouteIds(sourceCityId, destinationCityId,adjacencyList);
//        System.out.println(allRouteIds);
////        Iterator<List<Long>> outerIterator = allRouteIds.iterator();
//        List<String> tempFare = new ArrayList<>();
////        double minimumFare= 100000.0;
////        double minimumTime = 100000.0;
////        while (outerIterator.hasNext()) {
////            List<Long> innerList = outerIterator.next();
////            Long previousRouteId = null;
////            double miniFare = 0.0;
////            double miniTime = 0.0;
////            List<Route> miniFareRoute = new ArrayList<>();
////            List<Route> miniTimeRoute = new ArrayList<>();
////            for (Long routeId : innerList) {
////                if (previousRouteId != null) {
////                    List<Double> fare = new ArrayList<>();
////                    List<String> mode = new ArrayList<>();
////                    List<Double> travelTime = new ArrayList<>();
////
////
////                    Route minFareid=new Route();
////                    Route minTimeid = new Route();
////                    findFareandTime(previousRouteId,routeId,routeData,fare,mode,travelTime,minFareid,minTimeid);
////
////
////
////
////                    miniFareRoute.add(minFareid);
////                    miniTimeRoute.add(minTimeid);
////                    miniFare += minTimeid.getFare();
////                    miniTime += minTimeid.getTravelTime();
////
////
////                } else {
//////                    System.out.println("Route ID: " + routeId);
////                }
////
////                previousRouteId = routeId;
////            }
////
//////
////
////
////
////            // Initialize a StringBuilder to construct the string
////            StringBuilder routeInfoBuilder = new StringBuilder();
////            int i =1;
////            for (Route x : miniTimeRoute) {
////                // Append the data for each route to the StringBuilder
////                routeInfoBuilder.append("Route ").append(i).append(cityIdToNameMap.get(x.getSourceCityId()))
////                        .append(" ")
////                        .append(cityIdToNameMap.get(x.getDestinationCityId()))
////                        .append(" ")
////                        .append(x.getFare())
////                        .append(" ")
////                        .append(x.getMode())
////                        .append(x.getTravelTimeInHours())
////                        .append("\n");  // Add a newline to separate routes
////                i++;
////            }
////
////            routeInfoBuilder.append("Total Fare ").append(miniFare);
////
////
////            String routeInfo = routeInfoBuilder.toString();  // Convert the StringBuilder to a string
////            System.out.println(minimumTime+" "+miniTime);
////            if(minimumTime>miniTime){
////                if(tempFare.isEmpty()){
////                    System.out.println("in mini");
////                    tempFare.add(routeInfo);
////
////                }
////                else{
////                    System.out.println("in mini");
////                    tempFare.clear();
////                    tempFare.add(routeInfo);
////
////
////                }
////                minimumTime = miniTime;
////            }
////
////
////        }
////
////
////        // Add logic to display the cheapest rout
////
////
////
////
//
//        return tempFare; // Return the name of the view template
//    }






}

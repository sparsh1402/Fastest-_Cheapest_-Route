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

        public Structure1(String sr, String de, String mode, double timeTravel, double Fare) {
            this.SourceCity = sr;
            this.destinationCity = de;
            this.ModeOfTransport = mode;
            this.TimeInHrs = timeTravel;
            this.Fare = Fare;
        }
    }

    public class Structure2 {

        public double TotalTime;
        public double TotalFare;


        public Structure2(double TotalTime, double TotalFare) {
            this.TotalFare = TotalFare;
            this.TotalTime = TotalTime;

        }

        // Constructors, getters, setters
    }

    public static List<Route> routeData;
    public static List<City> cityData;
    public static Map<Long, String> cityMap = new HashMap<>();


    public List<Route> getAllRouteData() {
//        System.out.println(routeRepository.findAll());
        return routeData = routeRepository.findAll();
    }

    public List<City> getCityData() {
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


    public static List<List<Long>> findAllRouteIds(Long sourceCityId, Long destinationCityId, Map<Long, List<CityEdge>> adj) {
        List<List<Long>> allRouteIds = new ArrayList<>();
        List<Long> currentRouteIds = new ArrayList<>();
        Set<Long> visitedCityIds = new HashSet<>();
//        printAdjacencyList(adj);
        findRouteIds(sourceCityId, destinationCityId, currentRouteIds, allRouteIds, visitedCityIds, adj);
        return allRouteIds;
    }

    private static void findRouteIds(Long currentCityId, Long destinationCityId, List<Long> currentRouteIds, List<List<Long>> allRouteIds, Set<Long> visitedCityIds, Map<Long, List<CityEdge>> adj) {
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


    public static void findFareandTime(Long source, Long dest, List<Route> routeData, List<Double> fare, List<String> mode, List<Double> travelTime, Route minFareid, Route minTimeid) {
        Double miniFare = 100000.0;


        Double miniTime = 100000.0;

        for (Route r : routeData) {
            if (Objects.equals(r.getSourceCityId(), source) && Objects.equals(r.getDestinationCityId(), dest)) {
//				System.out.println(r.getSourceCityId()+" "+r.getDestinationCityId());

                if (miniFare > r.getFare()) {
                    minFareid.setId(r.getId());
                    minFareid.setDestination(r.getDestination());
                    minFareid.setSource(r.getSource());
                    minFareid.setTravelTimeInHours(r.getTravelTimeInHours());
                    minFareid.setMode(r.getMode());
                    minFareid.setFare(r.getFare());
                    miniFare = r.getFare();
                }
//				System.out.println(miniTime+ " "+ r.getTravelTime());
                if (miniTime > r.getTravelTime()) {
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

    }

    public List<Object> showCheapestRoute(String src, String des) {
        Long sourceCityId = 1L; // Default source ID
        Long destinationCityId = 6L; // Default destination ID

        // Map source and destination names to their respective IDs
        for (City city : cityData) {
            if (src.equals(city.getName())) {
                sourceCityId = city.getId();
            }
            if (des.equals(city.getName())) {
                destinationCityId = city.getId();
            }
            cityMap.put(city.getId(), city.getName());
        }

        // Construct the adjacency list for Dijkstra's algorithm
        CityAdjacencyList adjacencyList1 = constructAdjacencyList(cityData, routeData);
        Map<Long, List<CityEdge>> adjacencyList = adjacencyList1.adjacencyList;

        // Use the Dijkstra function to find the cheapest route
        List<Long> cheapestPath = dijkstra(sourceCityId, destinationCityId, adjacencyList, true);

        // Prepare the result data
        List<Object> data = new ArrayList<>();
        double totalTime = 0.0;
        double totalFare = 0.0;

        if (cheapestPath.isEmpty()) {
            // Return empty if no path is found
            return data; // Or handle this case as needed
        }

        // Reconstruct the routes based on the cheapest path
        for (int i = 0; i < cheapestPath.size() - 1; i++) {
            Long fromCityId = cheapestPath.get(i);
            Long toCityId = cheapestPath.get(i + 1);
            Route route = findRoute(fromCityId, toCityId, routeData); // Implement this method to get the Route object
            if (route != null) {
                String sr = cityMap.get(route.getSourceCityId());
                String de = cityMap.get(route.getDestinationCityId());
                String mode = route.getMode();
                double travelTime = route.getTravelTime();
                double fare = route.getFare();
                totalTime += travelTime;
                totalFare += fare;

                data.add(new Structure1(sr, de, mode, travelTime, fare));
            }
        }

        data.add(new Structure2(totalTime, totalFare));
        return data;
    }

    public List<Object> showFastestRoute(String src, String des) {
        Long sourceCityId = null; // To be determined from the city name
        Long destinationCityId = null; // To be determined from the city name

        // Map for city IDs to names
        for (City city : cityData) {
            if (city.getName().equals(src)) {
                sourceCityId = city.getId();
            }
            if (city.getName().equals(des)) {
                destinationCityId = city.getId();
            }
            cityMap.put(city.getId(), city.getName());
        }

        // Build the adjacency list
        CityAdjacencyList adjacencyList = constructAdjacencyList(cityData, routeData);
        Map<Long, List<CityEdge>> graph = adjacencyList.adjacencyList;

        // Find the fastest route using Dijkstra's algorithm
        List<Long> fastestRoute = dijkstra(sourceCityId, destinationCityId, graph, false);
        double totalTravelTime = 0.0;
        double totalFare = 0.0;

        List<Object> data = new ArrayList<>(); // Use List<Object> to hold all results
        for (int i = 0; i < fastestRoute.size() - 1; i++) {
            Long currentCityId = fastestRoute.get(i);
            Long nextCityId = fastestRoute.get(i + 1);

            // Get route details
            Route route = findRoute(currentCityId, nextCityId, routeData);
            if (route != null) {
                totalTravelTime += route.getTravelTime();
                totalFare += route.getFare();
                // Add route details to the data
                data.add(new Structure1(cityMap.get(currentCityId), cityMap.get(nextCityId), route.getMode(), route.getTravelTime(), route.getFare()));
            }
        }

        // Add total time and fare
        data.add(new Structure2(totalTravelTime, totalFare)); // Adjust Structure2 as needed

        return data; // Return the data list containing route details and totals
    }

    // Dijkstra's Algorithm for finding the fastest route
    private List<Long> dijkstra(Long sourceCityId, Long destinationCityId, Map<Long, List<CityEdge>> graph, boolean minimizeFare) {
        Map<Long, Double> distances = new HashMap<>();
        Map<Long, Long> previous = new HashMap<>();
        PriorityQueue<Long> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));

        // Initialize distances
        for (Long cityId : graph.keySet()) {
            distances.put(cityId, Double.MAX_VALUE);
        }
        distances.put(sourceCityId, 0.0);
        priorityQueue.add(sourceCityId);

        while (!priorityQueue.isEmpty()) {
            Long currentCityId = priorityQueue.poll();

            if (currentCityId.equals(destinationCityId)) {
                break; // Found the destination
            }

            for (CityEdge edge : graph.get(currentCityId)) {
                Long neighborCityId = edge.getDestinationCityId();
                double edgeWeight = minimizeFare ? edge.getFare() : edge.getTravelTime();

                // Relaxation step
                double newDistance = distances.get(currentCityId) + edgeWeight;
                if (newDistance < distances.get(neighborCityId)) {
                    distances.put(neighborCityId, newDistance);
                    previous.put(neighborCityId, currentCityId);
                    priorityQueue.add(neighborCityId);
                }
            }
        }

        // Reconstruct the path
        List<Long> path = new ArrayList<>();
        for (Long at = destinationCityId; at != null; at = previous.get(at)) {
            path.add(at);
        }
        Collections.reverse(path); // Reverse to get the correct order
        return path;
    }

    // Helper method to find route details between two cities
    private Route findRoute(Long sourceId, Long destinationId, List<Route> routes) {
        for (Route route : routes) {
            if (route.getSourceCityId().equals(sourceId) && route.getDestinationCityId().equals(destinationId)) {
                return route;
            }
        }
        return null; // No route found
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
        for (City c : cityData) {
            cityMap.put(c.getId(), c.getName());
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
        Long destinationCityId = 0L;

        for (City c : cityData) {
            if (Objects.equals(c.getName(), src)) {
                sourceCityId = c.getId();
            }
            if (Objects.equals(c.getName(), dest)) {
                destinationCityId = c.getId();
            }
        }


        // Create an adjacency list of cities and their routes
        CityAdjacencyList adjacencyList1 = constructAdjacencyList(cityData, routeData);
        Map<Long, List<CityEdge>> adjacencyList = adjacencyList1.adjacencyList;
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
}

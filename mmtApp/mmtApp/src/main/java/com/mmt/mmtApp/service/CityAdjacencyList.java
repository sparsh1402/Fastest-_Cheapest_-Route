package com.mmt.mmtApp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityAdjacencyList {
    public Map<Long, List<CityEdge>> adjacencyList;

    public CityAdjacencyList() {
        adjacencyList = new HashMap<>();
    }

    public void addEdge(Long sourceCityId, Long destinationCityId, double travelTime, double fare) {
        CityEdge edge = new CityEdge(destinationCityId, travelTime, fare);
        if (!adjacencyList.containsKey(sourceCityId)) {
            adjacencyList.put(sourceCityId, new ArrayList<>());
        }
        adjacencyList.get(sourceCityId).add(edge);
    }



    public void addEdge(Long sourceCityId, Long destinationCityId, double travelTime, double fare,String mode) {
        CityEdge edge = new CityEdge(destinationCityId, travelTime, fare,mode);
        if (!adjacencyList.containsKey(sourceCityId)) {
            adjacencyList.put(sourceCityId, new ArrayList<>());
        }
        adjacencyList.get(sourceCityId).add(edge);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Long, List<CityEdge>> entry : adjacencyList.entrySet()) {
            sb.append("City ID: ").append(entry.getKey()).append(" -> ");
            List<CityEdge> edges = entry.getValue();
            for (CityEdge edge : edges) {
                sb.append("Destination: ").append(edge.getDestinationCityId())
                        .append(" Mode: ").append(edge.getMode())
                        .append(" Time: ").append(edge.getTravelTime())
                        .append(" Fare: ").append(edge.getFare())
                        .append(" | ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }



}

package com.yolotengo.advertisementApp.service;

import org.geonames.Toponym;
import org.geonames.WebService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dgallego on 29/11/2017.
 */
@Service
@Scope("singleton")
public class GeoLocationService {

    public List<String> getNearbyPlace(double lat, double lon, double radius) throws Exception {
        List<String> cityList = new ArrayList<>();

        WebService.setUserName("diegoezequielgallego");
        //lat log radio & cant
        List<Toponym> geonamesList = WebService.findNearbyPlaceName(lat, lon, radius, Integer.MAX_VALUE);

        for (Toponym city : geonamesList) {
            cityList.add(city.getName());
        }

        return cityList;
    }


    public double calculateDistance(double lat1, double lat2, double lon1, double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2) + Math.pow(0, 2);

        return Math.sqrt(distance);
    }
}

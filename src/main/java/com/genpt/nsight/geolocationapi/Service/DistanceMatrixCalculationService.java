package com.genpt.nsight.geolocationapi.Service;

import com.genpt.nsight.geolocationapi.Entities.DistanceMatrixByZipCodeRadius;
import com.genpt.nsight.geolocationapi.Entities.DistanceMatrixByZipCodeRadiusKey;
import com.genpt.nsight.geolocationapi.Entities.SiteWithExtendedAttributes;
import com.genpt.nsight.geolocationapi.Repository.DistanceMatrixByZipCodeRadiusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DistanceMatrixCalculationService {

    @Autowired
    DistanceMatrixByZipCodeRadiusRepository distanceMatrixByZipCodeRadiusRepository;

    @Autowired
    SiteService siteService;

    //Method that returns a List of ExtendedSiteWithAttributes type that takes in a zipCode and a radius
    public List<SiteWithExtendedAttributes> findSitesMatchingZipCodeAndRadius(String sourceZipCode, int radiusDistance) {
        return null;
    }
    //Method to return siteWithExtendedAttributesList
    public List<SiteWithExtendedAttributes> siteWithExtendedAttributesList(){
        return siteService.fetchAllSites();
    }
    //Method to return list containing sourceSites
    public List<SiteWithExtendedAttributes> sourceSites(String zipCode){
        return siteWithExtendedAttributesList().stream()
                .filter(siteWithExtendedAttributes -> siteWithExtendedAttributes.getSite().
                        getZipCode().equalsIgnoreCase(zipCode))
                .collect(Collectors.toList());
    }
    //Method to calculate distance via Haversine Formula
    public double Haversine(double sourceLatitude, double sourceLongitude, double destinationLatitude,
                            double destinationLongitude, SiteWithExtendedAttributes sourceSite,
                            SiteWithExtendedAttributes destinationSite){
        //Variables for Haversine formula
        final double earthRadius = 6371;
        double distance;
        double latitudeDistance = Math.toRadians((destinationSite.getLatitude()).doubleValue()) -
                Math.toRadians((sourceSite.getLatitude()).doubleValue());
        double longitudeDistance = Math.toRadians((destinationSite.getLongitude()).doubleValue()) -
                Math.toRadians((sourceSite.getLongitude()).doubleValue());
        double a = Math.pow(Math.sin(latitudeDistance / 2), 2) + Math.pow(Math.sin(longitudeDistance / 2), 2)
                * Math.cos((sourceSite.getLatitude().doubleValue()) * Math.cos((sourceSite.getLatitude()).doubleValue()));
        double b = 2 * Math.asin(Math.sqrt(a));
        distance = earthRadius * b;
        return distance;
    }
    public List<SiteWithExtendedAttributes> destinationSites(){
        for (SiteWithExtendedAttributes destinationSite: siteWithExtendedAttributesList()){
            Haversine(sourceSites());
        }
    }
}

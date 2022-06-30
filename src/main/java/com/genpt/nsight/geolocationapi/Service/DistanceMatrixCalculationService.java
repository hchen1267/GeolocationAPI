package com.genpt.nsight.geolocationapi.Service;

import com.genpt.nsight.geolocationapi.Entities.*;
import com.genpt.nsight.geolocationapi.Repository.DistanceMatrixByZipCodeRadiusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DistanceMatrixCalculationService {

    @Autowired
    DistanceMatrixByZipCodeRadiusRepository distanceMatrixByZipCodeRadiusRepository;

    @Autowired
    SiteService siteService;

    //Method that returns a List of ExtendedSiteWithAttributes type that takes in a zipCode and a radius
    public List<SiteWithExtendedAttributes> findSitesMatchingZipCodeAndRadius(String sourceZipCode, int radiusDistance) {
        //find whether results exist in DB
        return queryDatabase(sourceZipCode, "Miles", radiusDistance);
        //Create DistanceMatrix Table Key Class
        //DistanceMatrix findByID(Distance Matrix Key Class)
    }

    //Method that returns only the sites where zipcode matches
    public List<SiteWithExtendedAttributes> getSiteMatchingZipCode(String zipCode){
        return siteService.fetchAllSites().stream()
                .filter(siteWithExtendedAttributes -> siteWithExtendedAttributes.getShortZipCode()
                        .equalsIgnoreCase(zipCode)).collect(Collectors.toList());
    }

    //Method that checks if the given zipCode and radiusDistance has already calculated destinationZipCodes
    public List<SiteWithExtendedAttributes> queryDatabase(String sourceZipCode, String distanceUnit, int radiusDistance){
        //Create distanceMatrixKey that stores the given parameters
        List<SiteWithExtendedAttributes> siteWithExtendedAttributesList = new ArrayList<>();
        DistanceMatrixByZipCodeRadiusKey distanceMatrixByZipCodeRadiusKey =
                new DistanceMatrixByZipCodeRadiusKey(sourceZipCode, distanceUnit,radiusDistance);
        List<DistanceMatrixByZipCodeRadiusKey> distanceMatrixByZipCodeRadiusKeyList = new ArrayList<>();
        if(radiusDistance<=5){
            DistanceMatrixByZipCodeRadiusKey distanceMatrixByZipCodeRadiusKey5Mile =
                    new DistanceMatrixByZipCodeRadiusKey(sourceZipCode,distanceUnit,radiusDistance);
            distanceMatrixByZipCodeRadiusKeyList.add(distanceMatrixByZipCodeRadiusKey5Mile);
        }else if(radiusDistance<=10){
            DistanceMatrixByZipCodeRadiusKey distanceMatrixByZipCodeRadiusKey10Mile =
                    new DistanceMatrixByZipCodeRadiusKey(sourceZipCode,distanceUnit, radiusDistance);
            distanceMatrixByZipCodeRadiusKeyList.add(distanceMatrixByZipCodeRadiusKey10Mile);
        }else if(radiusDistance<=25){
            DistanceMatrixByZipCodeRadiusKey distanceMatrixByZipCodeRadiusKey25Mile =
                    new DistanceMatrixByZipCodeRadiusKey(sourceZipCode,distanceUnit,radiusDistance);
            distanceMatrixByZipCodeRadiusKeyList.add(distanceMatrixByZipCodeRadiusKey25Mile);
        }else if (radiusDistance<=50){
            DistanceMatrixByZipCodeRadiusKey distanceMatrixByZipCodeRadiusKey50Mile =
                    new DistanceMatrixByZipCodeRadiusKey(sourceZipCode,distanceUnit,radiusDistance);
            distanceMatrixByZipCodeRadiusKeyList.add(distanceMatrixByZipCodeRadiusKey50Mile);
        }
        List<DistanceMatrixByZipCodeRadius> distanceMatrixByZipCodeRadius = distanceMatrixByZipCodeRadiusRepository
                .findAllRowsByKeyMatchingElements(sourceZipCode,distanceUnit,radiusDistance);
        if (distanceMatrixByZipCodeRadius.isEmpty()){
            calculateDistanceMatrixComputationResults(sourceZipCode,distanceUnit);
        }else{
            log.info("calculated");
            Iterator<DistanceMatrixByZipCodeRadius> iterator = distanceMatrixByZipCodeRadius.stream().iterator();
            DistanceMatrixByZipCodeRadius distanceMatrixByZipCodeRadius2=null;
            while(iterator.hasNext()){
                distanceMatrixByZipCodeRadius2= iterator.next();
                siteWithExtendedAttributesList.
                        addAll(getSiteMatchingZipCode(distanceMatrixByZipCodeRadius2.getDestinationZipCode()));
            }
        }
        return siteWithExtendedAttributesList;
    }
    //Method doing the actual calculation and storing of stores within X radius
    public void calculateDistanceMatrixComputationResults(String sourceZipCode, String distanceUnit){
        //Create the source site
        SiteWithExtendedAttributes sourceSite = getSiteMatchingZipCode(sourceZipCode).get(0);
        //Create and initialize double variable storing actualDistance
        double actualDistance = 0.0;
        //Create distanceMatrixKey and initialize to null
        DistanceMatrixByZipCodeRadiusKey distanceMatrixByZipCodeRadiusKey = null;
        boolean isValidRecord;
        int radiusDistance=0;
        List<SiteWithExtendedAttributes> uniqueZipCodeSites = uniqueZipCodesSites();
        for (SiteWithExtendedAttributes destinationSite: uniqueZipCodeSites){
            isValidRecord = false;
            if (sourceSite.getShortZipCode().substring(0,1).equals(destinationSite.getShortZipCode().substring(0,1))){
                if (sourceSite.getLatitude()!= null && sourceSite.getLongitude()!=null  &&
                        destinationSite.getLatitude()!=null && destinationSite.getLongitude()!=null){
                    actualDistance = Haversine((sourceSite.getLatitude()).doubleValue(),(sourceSite.getLongitude()).doubleValue(),
                            (destinationSite.getLatitude()).doubleValue(), (destinationSite.getLongitude()).doubleValue());

                    if (actualDistance >= 0 && actualDistance<=5){
                       distanceMatrixByZipCodeRadiusKey=new DistanceMatrixByZipCodeRadiusKey(sourceZipCode,distanceUnit,5);
                        isValidRecord = true;
                    }
                    else if (actualDistance >5 && actualDistance <=10){
                        distanceMatrixByZipCodeRadiusKey = new DistanceMatrixByZipCodeRadiusKey(sourceZipCode, distanceUnit, 10);
                        isValidRecord = true;
                    }
                    else if (actualDistance>10 && actualDistance <=25){
                        distanceMatrixByZipCodeRadiusKey = new DistanceMatrixByZipCodeRadiusKey(sourceZipCode,distanceUnit, 25);
                        isValidRecord = true;
                    }
                    else if (actualDistance>25 && actualDistance <=50){
                        distanceMatrixByZipCodeRadiusKey = new DistanceMatrixByZipCodeRadiusKey(sourceZipCode,distanceUnit,50);
                        isValidRecord = true;
                    }
                    else{
                        System.out.println("No stores within 50 miles");
                    }
                    if (isValidRecord){
                        DistanceMatrixByZipCodeRadius distanceMatrixByZipCodeRadius = constructDistanceMatrixRecord(distanceMatrixByZipCodeRadiusKey,
                                actualDistance, UUID.randomUUID(), destinationSite.getShortZipCode());
                        distanceMatrixByZipCodeRadiusRepository.save(distanceMatrixByZipCodeRadius);
                    }
                }
            }
        }
    }

    public List<SiteWithExtendedAttributes> uniqueZipCodesSites(){
        //Create List to store all sites/ matching zipcodes from SiteService
        List<SiteWithExtendedAttributes> getAllSites = siteService.fetchAllSites();
        //Create new hashSet that stores objects w/ unique zipCodes
        Set<UniqueZipCodesSite> uniqueZipCodeSet = new HashSet<>();
        for(SiteWithExtendedAttributes allSite: getAllSites){
            String shortZipCode = allSite.getShortZipCode();
            BigDecimal latitude = allSite.getLatitude();
            BigDecimal longitude = allSite.getLongitude();

            UniqueZipCodesSite uniqueZipCodeObj= new UniqueZipCodesSite(shortZipCode,latitude,longitude);
            uniqueZipCodeSet.add(uniqueZipCodeObj);
        }
        List<SiteWithExtendedAttributes> uniqueZipCodeSiteList = new ArrayList<>();
        for(UniqueZipCodesSite zipCodeObj: uniqueZipCodeSet){
            String shortZipCode = zipCodeObj.getShortZipCode();
            BigDecimal latitude = zipCodeObj.getLatitude();
            BigDecimal longitude = zipCodeObj.getLongitude();

            SiteWithExtendedAttributes uniqueZip = new SiteWithExtendedAttributes(shortZipCode,latitude,longitude);
            uniqueZipCodeSiteList.add(uniqueZip);
        }
        return uniqueZipCodeSiteList;
    }
    //Method to calculate distance via Haversine Formula
    public double Haversine(double sourceLatitude, double sourceLongitude, double destinationLatitude,
                            double destinationLongitude){
        //Variables for Haversine formula
        final double earthRadius = 3961;
        double distance;
        double latitudeDistance = Math.toRadians(destinationLatitude - sourceLatitude);
        double longitudeDistance = Math.toRadians(destinationLongitude - sourceLongitude);
        double a = Math.pow(Math.sin(latitudeDistance / 2), 2) + Math.pow(Math.sin(longitudeDistance / 2), 2)
                * Math.cos(Math.toRadians(sourceLatitude)) * Math.cos(Math.toRadians(destinationLatitude));
        double b = 2 * Math.asin(Math.sqrt(a));
        distance = earthRadius * b;
        return distance;
    }

    //Method creating new DistanceMatrixRecord
    private DistanceMatrixByZipCodeRadius constructDistanceMatrixRecord(DistanceMatrixByZipCodeRadiusKey distanceMatrixByZipCodeRadiusKey,
                                                                        double actualDistance, UUID matrixID, String destinationZipCode){
        DistanceMatrixByZipCodeRadius distanceMatrixByZipCodeRadius1=new DistanceMatrixByZipCodeRadius(distanceMatrixByZipCodeRadiusKey,destinationZipCode);
        distanceMatrixByZipCodeRadius1.setMatrixID(matrixID);
        distanceMatrixByZipCodeRadius1.setActualDistance(BigDecimal.valueOf(actualDistance));
        return distanceMatrixByZipCodeRadius1;
    }

}

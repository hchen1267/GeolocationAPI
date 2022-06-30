package com.genpt.nsight.geolocationapi.Controller;

import com.genpt.nsight.geolocationapi.Entities.SiteWithExtendedAttributes;
import com.genpt.nsight.geolocationapi.Service.DistanceMatrixCalculationService;
import com.genpt.nsight.geolocationapi.Service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class SiteController {
    @Autowired
    SiteService siteService;

    @Autowired
    DistanceMatrixCalculationService distanceMatrixCalculationService;

    @GetMapping("/allSites")
    public List<SiteWithExtendedAttributes> findAllSites(){
        return siteService.fetchAllSites();
    }

    @GetMapping("/get")
//    public List<SiteWithExtendedAttributes> findSitesMatchingZipCodeAndRadius(@RequestParam String zipCode, @RequestParam int radius){
//        return distanceMatrixCalculationService.findSitesMatchingZipCodeAndRadius(zipCode, radius);
//    }
    public List<SiteWithExtendedAttributes> queryDatabase(@RequestParam(value = "sourceZipCode") String sourceZipCode,
                                                          @RequestParam(value = "radiusDistance") int radiusDistance,
                                                          @RequestParam(value = "distanceUnit") String distanceUnit){
        return distanceMatrixCalculationService.queryDatabase(sourceZipCode, distanceUnit, radiusDistance);
    }

    @GetMapping("/testUniqueZipCodes")
    public List<SiteWithExtendedAttributes> findUniqueZip (){
        return distanceMatrixCalculationService.uniqueZipCodesSites();
    }


}

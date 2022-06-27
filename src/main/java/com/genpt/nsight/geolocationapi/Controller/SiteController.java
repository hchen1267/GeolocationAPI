package com.genpt.nsight.geolocationapi.Controller;

import com.genpt.nsight.geolocationapi.Entities.SiteWithExtendedAttributes;
//import com.genpt.nsight.geolocationapi.Service.DistanceMatrixCalculationService;
import com.genpt.nsight.geolocationapi.Service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SiteController {
    @Autowired
    SiteService siteService;
//
//    @Autowired
//    DistanceMatrixCalculationService distanceMatrixCalculationService;

    @GetMapping("/allSites")
    public List<SiteWithExtendedAttributes> findAllSites(){
        return siteService.fetchAllSites();
    }

    @GetMapping("/findSitesInZipCodeRadius")
    public List<SiteWithExtendedAttributes> findSitesInRadius(@RequestParam String zipCode, @RequestParam int radius){
        return null;
    }
}

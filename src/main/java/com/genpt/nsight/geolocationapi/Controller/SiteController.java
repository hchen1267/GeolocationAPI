package com.genpt.nsight.geolocationapi.Controller;

import com.genpt.nsight.geolocationapi.Entities.ExtendedSiteWithAttributes;
import com.genpt.nsight.geolocationapi.Entities.Site;
import com.genpt.nsight.geolocationapi.Service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SiteController {
    @Autowired
    SiteService siteService;

    @GetMapping("/allSites")
    public List<ExtendedSiteWithAttributes> findAllSites(){
        return siteService.fetchAllSites();
    }
}

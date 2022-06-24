package com.genpt.nsight.geolocationapi.Service;

import com.genpt.nsight.geolocationapi.Entities.ExtendedSiteWithAttributes;
import com.genpt.nsight.geolocationapi.Entities.Site;
import com.genpt.nsight.geolocationapi.Entities.SiteKey;
import com.genpt.nsight.geolocationapi.Entities.SiteTimeZone;
import com.genpt.nsight.geolocationapi.Repository.SiteRepository;
import com.genpt.nsight.geolocationapi.Repository.SiteTimeZoneRepository;
import jnr.ffi.mapper.ToNativeType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class SiteService {
    @Autowired
    SiteRepository siteRepository;

    @Autowired
    SiteTimeZoneRepository siteTimeZoneRepository;

    @Cacheable("sites")
    //Method that returns a list of Sites
    public List<ExtendedSiteWithAttributes> fetchAllSites() {
        log.info("Method to fetch all sites called");

        //Create List of SiteTimeZone objects and find all siteTimeZones
        List<SiteTimeZone> siteTimeZones = siteTimeZoneRepository.findAll();

        //Create list of Sites and find all sites
        List<Site> sites = siteRepository.findAll();

        //List of Sites to store Stores w/o duplicates
        List<Site> storeList = new ArrayList<>();

        //List of Sites to store DC w/o duplicates
        List<Site> dcList = new ArrayList<>();

        //List of Sites to store suppliers wo duplicates
        List<Site> supplierList = new ArrayList<>();

        //List of ExtendedSites
        List<ExtendedSiteWithAttributes> extendedSiteWithAttributesList = new ArrayList<>();

        //Stream to get stores via their 9 digit store number & add it to siteList
        sites.stream()
                .filter(site -> site.getSiteKey().getAliasName().equals("9 Digit Store Number"))
                .forEach(site -> storeList.add(site));

        //Stream to get suppliers & add it to supplierList
        sites.stream()
                .filter(site -> site.getSiteKey().getAliasName().equals("Supplier Abbreviation Location"))
                .forEach(site -> supplierList.add(site));

        //Stream to get DC & add it to dcList
        sites.stream()
                .filter(site -> site.getSiteKey().getAliasName().equals("DC Abbreviation Code"))
                .forEach(site -> dcList.add(site));

        //Add all unique sites added into an aggregate list called allUniqueSites
        List<Site> allUniqueSites = Stream.of(storeList,supplierList,dcList)
                                            .flatMap(Collection::stream)
                                            .collect(Collectors.toList());
        //List for siteTimeZone
        List<SiteTimeZone> siteTimeZoneList = new ArrayList<>();

        //forEach site in storeList set zipCode variable to be zipCode of the site
        for (Site uniqueSite: allUniqueSites){
            String zipCode = uniqueSite.getZipCode();
            if (zipCode==null){
                continue;
            }
            else{
                if (zipCode.contains("-")) {
                    zipCode = zipCode.replace("-", "");
                }
                String finalZipCode = zipCode;

                List<ExtendedSiteWithAttributes> finalExtendedSiteWithAttributesList = extendedSiteWithAttributesList;
                extendedSiteWithAttributesList = siteTimeZones.stream()
                        .filter(siteTimeZone -> siteTimeZone.getZipCode().equalsIgnoreCase(finalZipCode))
                        .map(siteTimeZone -> new ExtendedSiteWithAttributes
                                (uniqueSite,siteTimeZone.getLatitude(),siteTimeZone.getLongitude()))
                        //.collect(Collectors.toList());
                        .sequential().collect(Collectors.toCollection(() -> finalExtendedSiteWithAttributesList));


            }
        }
        return extendedSiteWithAttributesList;
    }
}

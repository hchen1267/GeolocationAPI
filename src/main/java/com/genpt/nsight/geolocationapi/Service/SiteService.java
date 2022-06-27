package com.genpt.nsight.geolocationapi.Service;

import com.genpt.nsight.geolocationapi.Entities.SiteWithExtendedAttributes;
import com.genpt.nsight.geolocationapi.Entities.Site;
import com.genpt.nsight.geolocationapi.Entities.SiteTimeZone;
import com.genpt.nsight.geolocationapi.Repository.SiteRepository;
import com.genpt.nsight.geolocationapi.Repository.SiteTimeZoneRepository;
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
    public List<SiteWithExtendedAttributes> fetchAllSites() {
        log.info("Method to fetch all sites called");
        List<Site> siteList = fetchAllSitesFromDataStore();
        List<SiteTimeZone> siteTimeZoneList= fetchAllTimeZoneRecordsFromDataStore();

        List<Site> filteredStoreList = filterSitesByAliasName("9 Digit Store Number", siteList);
        List<Site> filteredDCList = filterSitesByAliasName("DC Abbreviation Code", siteList);
        List<Site> filteredSupplierList = filterSitesByAliasName("Supplier Abbreviation Location", siteList);

        List<Site> combinedSiteList = combinedSiteList(filteredStoreList,filteredDCList,filteredSupplierList);

        List<SiteWithExtendedAttributes> siteWithExtendedAttributesList = createSiteWithExtendedAttributesList
                (combinedSiteList, siteTimeZoneList);

        return siteWithExtendedAttributesList;
    }
    private List<Site> fetchAllSitesFromDataStore(){
        return siteRepository.findAll();
    }
    private List<SiteTimeZone> fetchAllTimeZoneRecordsFromDataStore(){
        return siteTimeZoneRepository.findAll();
    }
    private List<Site> filterSitesByAliasName(String aliasName, List<Site> siteListName){
        return siteListName.stream()
                .filter(site -> site.getSiteKey().getAliasName().equalsIgnoreCase(aliasName))
                .filter(site -> site.getIsSiteActive() != null)
                .filter(site -> site.getIsSiteActive().equalsIgnoreCase("true"))
                .filter(site -> site.getZipCode() != null)
                .filter(site -> !(site.getZipCode().equals("00000")))
                .collect(Collectors.toList());
    }
    private List<Site> combinedSiteList(List<Site> filteredStoreList, List<Site> filteredDCList, List<Site> filteredSupplierList){
        return Stream.of(filteredStoreList,filteredDCList,filteredSupplierList)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<SiteWithExtendedAttributes> createSiteWithExtendedAttributesList(List<Site> combinedSiteList, List<SiteTimeZone> siteTimeZoneList) {
        List<SiteWithExtendedAttributes> siteWithExtendedAttributesList = new ArrayList<>();

        for (Site site : combinedSiteList) {
            String zipCode = site.getZipCode();
            if (zipCode.contains("-")) {
                zipCode = zipCode.split("-")[0];
            }
            String finalZipCode = zipCode;
            siteTimeZoneList.stream()
                    .filter(siteTimeZone -> siteTimeZone.getZipCode().equalsIgnoreCase(finalZipCode))
                    .map(siteTimeZone -> new SiteWithExtendedAttributes
                            (site,siteTimeZone.getLatitude(),siteTimeZone.getLongitude()))
                    .sequential().collect(Collectors.toCollection(() -> siteWithExtendedAttributesList));

        }
        return siteWithExtendedAttributesList;
    }
}

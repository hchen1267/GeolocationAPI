package com.genpt.nsight.geolocationapi.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class SiteWithExtendedAttributes {
    private Site site;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String shortZipCode;

    public SiteWithExtendedAttributes(Site site, BigDecimal latitude, BigDecimal longitude, String shortZipCode){
        this.site = site;
        this.latitude = latitude;
        this.longitude = longitude;
        this.shortZipCode = shortZipCode;
    }

    public SiteWithExtendedAttributes(String shortZipCode, BigDecimal latitude, BigDecimal longitude){
        this.latitude = latitude;
        this.longitude = longitude;
        this.shortZipCode = shortZipCode;
    }
}

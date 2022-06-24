package com.genpt.nsight.geolocationapi.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExtendedSiteWithAttributes {
    private Site site;
    private BigDecimal latitude;
    private BigDecimal longitude;
}

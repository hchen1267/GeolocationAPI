package com.genpt.nsight.geolocationapi.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DistanceMatrixMapping {
    //Create variables for DistanceMatrixMappings object
    private String sourceZipCode;
    private String distanceUnit;
    private int radiusDistance;
    private int destinationZipCode;
}

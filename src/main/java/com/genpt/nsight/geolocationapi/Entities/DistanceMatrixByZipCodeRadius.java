package com.genpt.nsight.geolocationapi.Entities;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table (value = "distancematrix_by_zipcodeandradius")
public class DistanceMatrixByZipCodeRadius {
    @PrimaryKey
    private DistanceMatrixByZipCodeRadiusKey distanceMatrixByZipCodeRadiusKey;
    private int destination_zipCode;


}

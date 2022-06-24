package com.genpt.nsight.geolocationapi.Entities;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class DistanceMatrixByZipCodeRadius {
    @PrimaryKey
    private DistanceMatrixByZipCodeRadiusKey distanceMatrixByZipCodeRadiusKey;
    private int destination_zipCode;
}

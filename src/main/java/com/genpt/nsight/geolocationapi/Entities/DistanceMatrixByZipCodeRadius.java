package com.genpt.nsight.geolocationapi.Entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;
@Getter
@Setter
@Table (value = "distancematrix_by_zipcodeandradius")
public class DistanceMatrixByZipCodeRadius {
    @PrimaryKey
    private DistanceMatrixByZipCodeRadiusKey distanceMatrixByZipCodeRadiusKey;
    @Column(value = "actual_distance")
    private BigDecimal actualDistance;
    @Column(value = "matrix_id")
    private UUID matrixID;
    @Column(value = "destination_zipcode")
    private String destinationZipCode;

    public DistanceMatrixByZipCodeRadius(DistanceMatrixByZipCodeRadiusKey distanceMatrixByZipCodeRadiusKey,
                                         String destinationZipCode) {
        this.distanceMatrixByZipCodeRadiusKey = distanceMatrixByZipCodeRadiusKey;
        this.destinationZipCode = destinationZipCode;
    }

}

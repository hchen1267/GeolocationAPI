package com.genpt.nsight.geolocationapi.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@PrimaryKeyClass
@Getter
@Setter

public class DistanceMatrixByZipCodeRadiusKey {
    @PrimaryKeyColumn(type= PrimaryKeyType.PARTITIONED,value="source_zipcode")
    private String sourceZipCode;
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, value = "distance_unit")
    private String distanceUnit;
    @PrimaryKeyColumn(type= PrimaryKeyType.PARTITIONED, value = "radius_distance")
    private int radiusDistance;


    public DistanceMatrixByZipCodeRadiusKey(String sourceZipCode, String distanceUnit, int radiusDistance){
        this.sourceZipCode = sourceZipCode;
        this.distanceUnit = distanceUnit;
        this.radiusDistance = radiusDistance;
    }

}

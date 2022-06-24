package com.genpt.nsight.geolocationapi.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@PrimaryKeyClass
@Getter
@Setter
@AllArgsConstructor
public class DistanceMatrixByZipCodeRadiusKey {
    @PrimaryKeyColumn(type= PrimaryKeyType.PARTITIONED)
    private String sourceZipCode;
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String distanceUnit;
    @PrimaryKeyColumn(type= PrimaryKeyType.PARTITIONED)
    private String radiusDistance;
}

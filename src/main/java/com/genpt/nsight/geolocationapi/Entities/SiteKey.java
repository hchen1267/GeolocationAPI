package com.genpt.nsight.geolocationapi.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
@Getter
@Setter
@AllArgsConstructor
public class SiteKey {
    @PrimaryKeyColumn(value = "alias_name", type = PrimaryKeyType.PARTITIONED)
    private String aliasName;
    @PrimaryKeyColumn(value = "alias_value", type = PrimaryKeyType.PARTITIONED)
    private String aliasValue;
}

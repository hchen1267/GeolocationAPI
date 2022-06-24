package com.genpt.nsight.geolocationapi.Entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;

@Table("site_timezone")
@Getter
@Setter
public class SiteTimeZone {
    @PrimaryKeyColumn(value = "zip_code", type = PrimaryKeyType.PARTITIONED)
    private String zipCode;
    @Column
    private BigDecimal latitude;
    @Column
    private BigDecimal longitude;
}

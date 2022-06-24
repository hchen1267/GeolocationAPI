package com.genpt.nsight.geolocationapi.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value="site")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Site {
    @PrimaryKey
    private SiteKey siteKey;
    @Column(value = "site_identifier")
    private String siteIdentifier;
    @Column(value = "zip_code")
    private String zipCode;
    @Column(value = "site_type")
    private String siteType;
    @Column(value="site_active_flag")
    private String isSiteActive;
    @Column(value="ibs_hub_flag")
    private String isIBSHub;
}

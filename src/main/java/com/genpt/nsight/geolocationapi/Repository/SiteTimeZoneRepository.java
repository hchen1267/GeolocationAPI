package com.genpt.nsight.geolocationapi.Repository;

import com.genpt.nsight.geolocationapi.Entities.SiteTimeZone;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteTimeZoneRepository extends CassandraRepository<SiteTimeZone, String> {

}

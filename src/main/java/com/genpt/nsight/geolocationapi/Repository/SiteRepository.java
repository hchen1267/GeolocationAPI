package com.genpt.nsight.geolocationapi.Repository;

import com.genpt.nsight.geolocationapi.Entities.Site;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteRepository extends CassandraRepository<Site, String> {

}

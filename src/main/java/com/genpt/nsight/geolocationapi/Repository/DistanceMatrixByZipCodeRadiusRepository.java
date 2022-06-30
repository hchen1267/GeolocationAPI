package com.genpt.nsight.geolocationapi.Repository;

import com.genpt.nsight.geolocationapi.Entities.DistanceMatrixByZipCodeRadius;
import com.genpt.nsight.geolocationapi.Entities.DistanceMatrixByZipCodeRadiusKey;
import com.genpt.nsight.geolocationapi.Entities.SiteWithExtendedAttributes;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistanceMatrixByZipCodeRadiusRepository
        extends CassandraRepository <DistanceMatrixByZipCodeRadius, DistanceMatrixByZipCodeRadiusKey> {

    @Query("SELECT * FROM distancematrix_by_zipcodeandradius WHERE source_zipcode=?0 and " +  "distance_unit=?1 and radius_distance=?2")
    List<DistanceMatrixByZipCodeRadius> findAllRowsByKeyMatchingElements(String zipCode, String distanceUnit, int radiusDistance);

  

}

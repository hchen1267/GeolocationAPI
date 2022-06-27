package com.genpt.nsight.geolocationapi.Repository;

import com.genpt.nsight.geolocationapi.Entities.DistanceMatrixByZipCodeRadius;
import com.genpt.nsight.geolocationapi.Entities.DistanceMatrixByZipCodeRadiusKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistanceMatrixByZipCodeRadiusRepository
        extends CassandraRepository <DistanceMatrixByZipCodeRadius, DistanceMatrixByZipCodeRadiusKey> {

//    @Query("select source")
//    public Optional<DistanceMatrixByZipCodeRadius> findByPartition(DistanceMatrixByZipCodeRadiusKey){
//
//    }
}

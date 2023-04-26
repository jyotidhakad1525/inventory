package com.dms.inventory.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.dms.inventory.entities.VehicleModelDetails;

@Repository
public interface VehicleDetailRepository extends PagingAndSortingRepository<VehicleModelDetails, Integer> {

    @Query(value = "SELECT * FROM `vehicle-management_live_backup`.vehicle_details_new where model=:modelName and organization_id=:orgId", nativeQuery = true)
    Integer findAllByVehicleIdAndOriganistionId(String modelName, int orgId);
}

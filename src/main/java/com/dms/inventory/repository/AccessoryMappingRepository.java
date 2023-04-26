package com.dms.inventory.repository;

import com.dms.inventory.entities.AccessoryMapping;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessoryMappingRepository extends CrudRepository<AccessoryMapping, Integer> {

    List<AccessoryMapping> findAllByVehicleIdAndOrganisationId(Integer vehicleId, Integer orgId);

}

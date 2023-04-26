package com.dms.inventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dms.inventory.entities.VehicleSoldInfo;

@Repository
public interface VehicleSoldCarServiceRepository extends JpaRepository<VehicleSoldInfo, Integer> {
	
	@Query(value = "select * from vehicle_sold_info where inventory_used_car_id=:id",nativeQuery = true)
    Optional<VehicleSoldInfo> findByInventoryUsedCarId(int id);
	

}

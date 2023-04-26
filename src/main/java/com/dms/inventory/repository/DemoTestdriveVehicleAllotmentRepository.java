package com.dms.inventory.repository;

import com.dms.inventory.entities.DemoTestdriveVehicleAllotment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DemoTestdriveVehicleAllotmentRepository extends JpaRepository<DemoTestdriveVehicleAllotment,
        Integer>, JpaSpecificationExecutor<DemoTestdriveVehicleAllotment> {

    @Query(value = " from DemoTestdriveVehicleAllotment where demoVehicleId=:vhehicleId and plannedEndDatetime " +
            "=DATE_FORMAT(:plannedStartDatetime, 'yyyy-MM-dd')")
    List<DemoTestdriveVehicleAllotment> findByVehicleIdAndDate(@Param("vhehicleId") Integer vhehicleId, @Param(
            "plannedStartDatetime") String plannedStartDatetime);

    List<DemoTestdriveVehicleAllotment> findByDemoVehicleId(Integer vehicleId);

    DemoTestdriveVehicleAllotment findById(int id);
}

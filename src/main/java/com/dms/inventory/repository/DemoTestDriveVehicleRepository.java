package com.dms.inventory.repository;

import com.dms.inventory.entities.DemoTestDriveVehicle;
import com.dms.inventory.entities.VehicleInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DemoTestDriveVehicleRepository extends JpaRepository<DemoTestDriveVehicle, Integer>,
        JpaSpecificationExecutor<DemoTestDriveVehicle> {

    DemoTestDriveVehicle findById(int id);

    @Query(value = "select chassis_no from demo_test_drive_vehicles where chassis_no = :chassis_no" , nativeQuery = true)
    List<String> getChassisNumber(@Param(value = "chassis_no") String chassis_no);

    @Query(value = "select rc_no from demo_test_drive_vehicles where rc_no = :rc_no" , nativeQuery = true)
    List<String> getRcNumber(@Param(value = "rc_no") String rc_no);
    
    
    @Query(value = "select id from demo_test_drive_vehicles where chassis_no = :chassis_no" , nativeQuery = true)
    List<Integer> getChassisNumberRecordId(@Param(value = "chassis_no") String chassis_no);

    @Query(value = "select id from demo_test_drive_vehicles where rc_no = :rc_no" , nativeQuery = true)
    List<Integer> getRcNumberRecordId(@Param(value = "rc_no") String rc_no);
    

}

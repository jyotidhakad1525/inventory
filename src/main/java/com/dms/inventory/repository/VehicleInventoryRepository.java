package com.dms.inventory.repository;

import com.dms.inventory.entities.VehicleInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleInventoryRepository extends JpaRepository<VehicleInventoryEntity, Integer> {

    @Query(value = "SELECT * FROM inventory.vehicles_inventory as inv WHERE inv.org_id=:org_id AND inv" +
            ".branch_id=:branch_id AND inv.model=:model AND inv.created_datetime between CAST(:allotedDate as date) " +
            "AND CAST(:deAllotedDate as date);", nativeQuery = true)
    List<VehicleInventoryEntity> searchInventoryBy(long org_id, long branch_id, String model, String allotedDate,
                                                   String deAllotedDate);

    @Query(value = "SELECT * FROM inventory.vehicles_inventory as inv WHERE inv.model=:model AND inv.variant=:varient" +
            " AND inv.fuel=:fuel AND inv.colour=:color AND inv.transmission=:transmission_type AND inv" +
            ".alloted=:alloted", nativeQuery = true)
    List<VehicleInventoryEntity> getInventoryBy(@Param("model") String model, @Param("varient") String varient,
                                                @Param("fuel") String fuel, @Param("color") String color, @Param(
                                                        "transmission_type") String transmission_type, @Param("alloted") int alloted);

    @Query(value = "SELECT * FROM inventory.vehicles_inventory as inv WHERE inv.model=:model AND inv.varient=:varient" +
            " AND inv.vin_no=:vin AND inv.engineno=:engineno AND inv.chassis_no=:chassisno AND inv" +
            ".alloted=:alloted", nativeQuery = true)
    List<VehicleInventoryEntity> getInventoryByVinNo(@Param("model") String model, @Param("varient") String varient,
                                                @Param("vin") String fuel, @Param("engineno") String color, @Param(
                                                        "chassisno") String transmission_type, @Param("alloted") int alloted);

    
    @Query(value = "SELECT * FROM inventory.vehicles_inventory as inv WHERE inv.model=:model AND inv.varient=:varient" +
            " AND inv.fuel=:fuel AND inv.color=:color AND inv.transmission_type=:transmission_type AND inv" +
            ".alloted=:alloted order by purchase_date desc", nativeQuery = true)
    List<VehicleInventoryEntity> getInventoryByAgeing(@Param("model") String model, @Param("varient") String varient,
                                                @Param("fuel") String fuel, @Param("color") String color, @Param(
                                                        "transmission_type") String transmission_type, @Param("alloted") int alloted);
    
    @Query(value = "SELECT * FROM inventory.vehicles_inventory WHERE org_id = ?1 and alloted = 0  and alloted_date Is NULL" , nativeQuery = true)
    List<VehicleInventoryEntity> findAllById(String org_id);
    
    @Query(value = "SELECT * FROM inventory.vehicles_inventory WHERE org_id = ?1 and alloted = 0  and alloted_date Is NULL" , nativeQuery = true)
    List<VehicleInventoryEntity> findAllvariant(String org_id,String model);
    
    @Query(value = "SELECT * FROM inventory.vehicles_inventory WHERE org_id = ?1 and alloted = 0  and alloted_date Is NULL" , nativeQuery = true)
    List<VehicleInventoryEntity> findAllVehicleColour(String org_id,String variant);
    
    @Query(value = "SELECT * FROM inventory.vehicles_inventory WHERE org_id = ?1 and alloted = 0   and alloted_date Is NULL" , nativeQuery = true)
    List<VehicleInventoryEntity> findAllVehicleFuel(String org_id,String variant);
    
    @Query(value = "SELECT * FROM inventory.vehicles_inventory WHERE org_id = ?1 and model = ?2 and variant = ?3 and colour = ?4 and fuel = ?5 and alloted = 0 and alloted_date Is NULL" , nativeQuery = true)
 	List<VehicleInventoryEntity> findAllVehicleInventoryDetails(String org_id,String model,String variant,String colour,String fuel);

}

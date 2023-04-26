/**
 * 
 */
package com.dms.inventory.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dms.inventory.entities.VehicleStockInventoryEntity;

/**
 * @author bhara
 *
 */
@Repository
public interface VehicleStockInventoryRepo  extends CrudRepository<VehicleStockInventoryEntity, BigInteger> {
	
	
	@Query(value = "SELECT * FROM inventory.vehicle_stock_inventory as vsi WHERE vsi.org_id=:org_id order by created_date desc limit :limit offset :offset  ", nativeQuery = true)
    List<VehicleStockInventoryEntity> getVehicleInventory(int org_id, int limit, int offset);
	
	
	VehicleStockInventoryEntity    findByVinNum(String vinNum);
	
	
	

}

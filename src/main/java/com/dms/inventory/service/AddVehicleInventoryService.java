/**
 * 
 */
package com.dms.inventory.service;

import com.dms.inventory.common.BaseResponse;
import com.dms.inventory.model.AddVehicleInventoryRequest;
import com.dms.inventory.model.UpdateVehicleInventoryrequest;

/**
 * @author bhara
 *
 */
public interface AddVehicleInventoryService {
	
	
	public BaseResponse addVehicleToInventory(AddVehicleInventoryRequest addVehicleInventoryRequest) throws Exception;
	
	public BaseResponse transferVehicleInventory(UpdateVehicleInventoryrequest updateVehicleInventoryrequest);

}

/**
 * 
 */
package com.dms.inventory.service;

import com.dms.inventory.model.SearchVehicleInventoryRequest;
import com.dms.inventory.model.SearchVehicleInventoryResponse;

/**
 * @author bhara
 *
 */
public interface SearchVehicleInventoryService {
	
	
	public SearchVehicleInventoryResponse searchVehicleInventorySource(SearchVehicleInventoryRequest searchVehicleInventoryRequest);
	public SearchVehicleInventoryResponse getVehicleInventory(int orgId, int limit, int offset);

}

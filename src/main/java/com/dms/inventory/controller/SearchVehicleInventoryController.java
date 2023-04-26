/**
 * 
 */
package com.dms.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dms.inventory.model.SearchVehicleInventoryRequest;
import com.dms.inventory.model.SearchVehicleInventoryResponse;
import com.dms.inventory.service.SearchVehicleInventoryService;


/**
 * @author bhara
 *
 */
@RestController
public class SearchVehicleInventoryController {
	
	
	@Autowired
	private SearchVehicleInventoryService searchVehicleInventoryServiceImpl;
	
	@PostMapping(value = "/searchVehicleInventory")
	public SearchVehicleInventoryResponse searchVehicleInventorySource(@RequestBody SearchVehicleInventoryRequest searchVehicleInventoryRequest){
		SearchVehicleInventoryResponse response = searchVehicleInventoryServiceImpl.searchVehicleInventorySource(searchVehicleInventoryRequest);
		return response;
		
	}
	//allnewcarfinance
	@GetMapping(value = "/getVehicleInventory")	
	public SearchVehicleInventoryResponse getVehicleInventory(
			@RequestParam(value = "orgId", required = true) int orgId,          
            @RequestParam(value = "limit", required = true) int limit,
            @RequestParam(value = "offSet", required = false, defaultValue = "0") int offSet){
		SearchVehicleInventoryResponse response = searchVehicleInventoryServiceImpl.getVehicleInventory(orgId, limit, offSet);
		return response;
		
	}
	

}

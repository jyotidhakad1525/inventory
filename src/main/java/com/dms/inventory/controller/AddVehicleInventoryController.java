/**
 * 
 */
package com.dms.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dms.inventory.common.BaseResponse;
import com.dms.inventory.model.AddVehicleInventoryRequest;
import com.dms.inventory.model.UpdateVehicleInventoryrequest;
import com.dms.inventory.service.AddVehicleInventoryService;
import com.dms.inventory.common.Utils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author bhara
 *
 */
@RestController
@Slf4j
public class AddVehicleInventoryController {

	@Autowired
	private AddVehicleInventoryService addVehicleInventoryService;

	@PostMapping(value = "/addVehicleInventory")
	public ResponseEntity<BaseResponse> addVehicleInventory(@RequestBody AddVehicleInventoryRequest addVehicleInventoryRequest) {
		log.debug("Entry Controller :addVehicleInventory :::" + addVehicleInventoryRequest.toString());
		BaseResponse response = null;
		try {
			response = addVehicleInventoryService.addVehicleToInventory(addVehicleInventoryRequest);
		} catch (Exception e) {
			//
			log.error("Could save vehicle into inventory"+e.getMessage());
			response =  Utils.failureResponse(e.getMessage());
			return new ResponseEntity<BaseResponse>(response, HttpStatus.BAD_REQUEST);
		}		
		log.debug("Entry Controller :addVehicleInventory : completed");
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);

	}
	
	@PostMapping(value = "/transferVehicle")
	public ResponseEntity<BaseResponse> transferVehicleInventory(@RequestBody UpdateVehicleInventoryrequest updateVehicleInventoryrequest) {
		log.debug("Entry Controller :addVehicleInventory :::" + updateVehicleInventoryrequest.toString());
		BaseResponse successResponse = addVehicleInventoryService.transferVehicleInventory(updateVehicleInventoryrequest);
		log.debug("Entry Controller :addVehicleInventory : completed");
		return new ResponseEntity<BaseResponse>(successResponse, HttpStatus.OK);
	}

}

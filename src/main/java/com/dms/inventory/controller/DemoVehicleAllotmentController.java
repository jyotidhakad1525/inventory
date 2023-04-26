package com.dms.inventory.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dms.inventory.common.BaseResponse;
import com.dms.inventory.entities.VehicleInventoryEntity;
import com.dms.inventory.model.AllotmentSearch;
import com.dms.inventory.model.DropdownReq;
import com.dms.inventory.model.VehicleAllotmentRequest;
import com.dms.inventory.model.VehicleAllotmentResponse;
import com.dms.inventory.model.VehicleInventoryRequest;
import com.dms.inventory.model.VehicleInventoryResponse;
import com.dms.inventory.requestImpl.DMSDemoVehicleRequestImpl;
import com.dms.inventory.service.VehicleService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = {"allotment"})
public class DemoVehicleAllotmentController {

    private final DMSDemoVehicleRequestImpl helper;
    @Autowired(required=true)
    VehicleService vehicleservice;

    public DemoVehicleAllotmentController(DMSDemoVehicleRequestImpl helper) {
        this.helper = helper;
    }

    @RequestMapping(value = {"vehicle/allotments"}, method = {RequestMethod.GET})
    public ResponseEntity<VehicleAllotmentResponse> vehicleAllotments(AllotmentSearch request) {
        VehicleAllotmentResponse response = helper.vehicleAllotments(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = {"vehicle/allotment"}, method = {RequestMethod.GET})
    public ResponseEntity<VehicleAllotmentResponse> vehicleAllotment(AllotmentSearch request) {
        VehicleAllotmentResponse response = helper.vehicleAllotment(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = {"vehicle/allotment"}, method = {RequestMethod.POST})
    public ResponseEntity<BaseResponse> vehicleAllotmentCreate(@RequestBody VehicleAllotmentRequest request) {
        BaseResponse response = helper.vehicleAllotmentCreate(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = {"vehicle/allotment"}, method = {RequestMethod.PUT})
    public ResponseEntity<BaseResponse> vehicleAllotmentUpdate(@RequestBody VehicleAllotmentRequest request) {
        BaseResponse response = helper.vehicleAllotmentUpdate(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = {"vehicle/validatebooking"}, method = {RequestMethod.POST})
    public ResponseEntity<BaseResponse> vehicleAllotmentBookings(@RequestBody VehicleAllotmentRequest request) {
        BaseResponse response = helper.vehicleAllotmentBookings(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    private Map<String, Object> toVehicleMap(final VehicleInventoryEntity vehicle){
    	Map<String, Object> vehicleInventory = new HashMap<>();
    	vehicleInventory.put("id", vehicle.getId());
    	vehicleInventory.put("key", vehicle.getModel());
    	vehicleInventory.put("value", vehicle.getModel());
    	return vehicleInventory;
    }
  
    @Operation(summary = "Get Model details", description = "Get Model details")
    @PostMapping(value = "/vehicle_models", produces = "application/json")
    public ResponseEntity<List<Map<String, Object>>> getVehicleModels(@RequestBody DropdownReq dropdownReq) {
    	//logger.info("Getting all Vehicles :: Models");

    	ResponseEntity<List<Map<String, Object>>> response = null;
    	final List<VehicleInventoryEntity> vehicles = vehicleservice.getAllVehicles((dropdownReq.getBu()));

    	if(null != vehicles) {
    		final List<Map<String, Object>> models = vehicles.stream()
    				.map(this::toVehicleMap)
    				.collect(Collectors.toList());
    		response = ResponseEntity.ok(models);
    	}
    	else {
    		response = ResponseEntity.noContent().build();
    	}
    	return response;
    }
    
    private Map<String, Object> toVehicleVaraiantMap(final VehicleInventoryEntity vehicle){
    	Map<String, Object> vehiclevariants = new HashMap<>();
    	vehiclevariants.put("id", vehicle.getId());
    	vehiclevariants.put("key", vehicle.getVariant());
    	vehiclevariants.put("value", vehicle.getVariant());
    	return vehiclevariants;
    }
  
    @Operation(summary = "Get variants details", description = "Get variants details")
    @PostMapping(value = "/vehicle_variants", produces = "application/json")
    public ResponseEntity<List<Map<String, Object>>> getVehiclevariantsModels(@RequestBody DropdownReq dropdownReq) {
   
    	ResponseEntity<List<Map<String, Object>>> response = null;
    	final List<VehicleInventoryEntity> vehicles = vehicleservice.getAllVariants((dropdownReq.getBu()),dropdownReq.getParentId());

    	if(null != vehicles) {
    		final List<Map<String, Object>> models = vehicles.stream()
    				.map(this::toVehicleVaraiantMap)
    				.collect(Collectors.toList());
    		response = ResponseEntity.ok(models);
    	}
    	else {
    		response = ResponseEntity.noContent().build();
    	}
    	return response;
    }
     
    private Map<String, Object> toVehicleColourMap(final VehicleInventoryEntity vehicle){
    	Map<String, Object> vehicleColour = new HashMap<>();
    	vehicleColour.put("id", vehicle.getId());
    	vehicleColour.put("key", vehicle.getColour());
    	vehicleColour.put("value", vehicle.getColour());
    	return vehicleColour;
    }
  
    @Operation(summary = "Get colour details", description = "Get colour details")
    @PostMapping(value = "/vehicle_colour", produces = "application/json")
    public ResponseEntity<List<Map<String, Object>>> getVehicleColours(@RequestBody DropdownReq dropdownReq) {
    	//logger.info("Getting all Vehicles :: Models");

    	ResponseEntity<List<Map<String, Object>>> response = null;
    	final List<VehicleInventoryEntity> vehicles = vehicleservice.getAllVehicleColour((dropdownReq.getBu()),dropdownReq.getParentId());

    	if(null != vehicles) {
    		final List<Map<String, Object>> models = vehicles.stream()
    				.map(this::toVehicleColourMap)
    				.collect(Collectors.toList());
    		response = ResponseEntity.ok(models);
    	}
    	else {
    		response = ResponseEntity.noContent().build();
    	}
    	return response;
    }
    
    
    private Map<String, Object> toVehicleFuelMap(final VehicleInventoryEntity vehicle){
    	Map<String, Object> vehicleFuel = new HashMap<>();
    	vehicleFuel.put("id", vehicle.getId());
    	vehicleFuel.put("key", vehicle.getFuel());
    	vehicleFuel.put("value", vehicle.getFuel());
    	return vehicleFuel;
    }
  
    @Operation(summary = "Get Fuel details", description = "Get Fuel details")
    @PostMapping(value = "/vehicle_Fuel", produces = "application/json")
    public ResponseEntity<List<Map<String, Object>>> getVehicleFuel(@RequestBody DropdownReq dropdownReq) {
    	//logger.info("Getting all Vehicles :: Models");

    	ResponseEntity<List<Map<String, Object>>> response = null;
    	final List<VehicleInventoryEntity> vehicles = vehicleservice.getAllVehicleFuel((dropdownReq.getBu()),dropdownReq.getParentId());

    	if(null != vehicles) {
    		final List<Map<String, Object>> models = vehicles.stream()
    				.map(this::toVehicleFuelMap)
    				.collect(Collectors.toList());
    		response = ResponseEntity.ok(models);
    	}
    	else {
    		response = ResponseEntity.noContent().build();
    	}
    	return response;
    }
    
    @Operation(summary = "Get Vehicle details", description = "Get Vehicle details")
    @PostMapping(value = "/vehicle_Details_Inventory", produces = "application/json")
    public ResponseEntity<?> getVehicleDetails(@RequestBody VehicleInventoryRequest request) {

    	List<VehicleInventoryResponse> response = null;
    	
		response = vehicleservice.getAllVehicleInvetoryDetails(request);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
    	


}

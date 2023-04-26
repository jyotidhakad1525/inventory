package com.dms.inventory.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dms.inventory.entities.Accessory;
import com.dms.inventory.model.BulkUploadAccessories;
import com.dms.inventory.model.BulkUploadModel;
import com.dms.inventory.model.BulkUploadResponse;
import com.dms.inventory.requestImpl.AccessoryService;
import com.dms.inventory.response.AccessoryResponseModel;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@CrossOrigin
@RequestMapping("/inventory/accessories")
public class AccessoryRequest {

    private final AccessoryService accessoryService;

    public AccessoryRequest(AccessoryService accessoryService) {
        this.accessoryService = accessoryService;
    }

    @Operation(summary = "Add accessories with image file")
    @PostMapping(value = "/add")
    public ResponseEntity<AccessoryResponseModel> addAccessory(@RequestBody Accessory accessory) {
        Accessory accessorydata = accessoryService.saveAllAccessories(accessory);
        AccessoryResponseModel respModel = generateResponseObject("Accessory successfully created", true, false);
        respModel.setAccessory(accessorydata);
        respModel.setAccessorylist(null);
        respModel.setAccessoryMappingDto(null);
        return new ResponseEntity<>(respModel, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Add accessories with image file")
    @PostMapping(value = "/add_bulkupload")
    public ResponseEntity<AccessoryResponseModel> addAccessory(@RequestBody List<Accessory> accessoryList) {
    	AccessoryResponseModel respModel=null;
    	for(Accessory accessory:accessoryList) {
    		
    
        Accessory accessorydata = accessoryService.saveAllAccessories(accessory);
        respModel = generateResponseObject("Accessory successfully created", true, false);
        respModel.setAccessory(accessorydata);
        respModel.setAccessorylist(null);
        respModel.setAccessoryMappingDto(null);
        
    	}
        return new ResponseEntity<>(respModel, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<AccessoryResponseModel> updateAccessory(@RequestBody Accessory accessory) {
        AccessoryResponseModel respModel = null;
        if (Objects.nonNull(accessory.getId())) {
            respModel = accessoryService.updateAccessory(accessory);
            respModel = generateResponseObject("Accessory details updated", true, false);
            respModel.setAccessory(accessory);
            respModel.setAccessorylist(null);
            respModel.setAccessoryMappingDto(null);
        } else {
            respModel = generateResponseObject("Accessory ID should not be a null", false, true);
        }
        return new ResponseEntity<>(respModel, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public ResponseEntity<AccessoryResponseModel> deleteAccessoriesById(@PathVariable Integer id) {
        AccessoryResponseModel respModel = accessoryService.deleteAccessoriesById(id);
        return new ResponseEntity<>(respModel, HttpStatus.OK);
    }

    @GetMapping(value = "/{vehicleId}", produces = "application/json")
    @Operation(summary = "Get all accessories by vehicle", description = "Get all accessories by Vehilce")
    public ResponseEntity<AccessoryResponseModel> getAllAccessoriesByVehicleId(
            @RequestHeader(value = "orgId", required = true) Integer ordId, @PathVariable Integer vehicleId,
            @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        AccessoryResponseModel respModel = accessoryService.getAllAccessoriesByVehicleId(vehicleId, ordId, pageNo,
                pageSize);
        return new ResponseEntity<>(respModel, HttpStatus.OK);
    }

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<AccessoryResponseModel> getAllAccessories(
            @RequestHeader(value = "orgId", required = true) Integer ordId,
            @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        AccessoryResponseModel respModel = null;
        List<Accessory> accessorylist = accessoryService.getAllAccessories(pageNo, pageSize, ordId);
        if (Objects.nonNull(accessorylist)) {
            respModel = generateResponseObject("Success", true, false);
            respModel.setAccessory(null);
            respModel.setAccessorylist(accessorylist);
            respModel.setAccessoryMappingDto(null);
        } else {
            respModel = generateResponseObject("Unable to find", false, true);
        }
        return new ResponseEntity<>(respModel, HttpStatus.OK);
    }

    @GetMapping(value = "/getbyid/{id}", produces = "application/json")
    public ResponseEntity<AccessoryResponseModel> getAccessoryById(
            @RequestHeader(value = "orgId", required = true) Integer ordId, @PathVariable Integer id) {
        AccessoryResponseModel respModel = null;
        List<Accessory> accessorylist = accessoryService.getAllAccessoriesId(id, ordId);
        if (Objects.nonNull(accessorylist) && accessorylist.size() > 0) {
            respModel = generateResponseObject("Success", true, false);
            respModel.setAccessory(null);
            respModel.setAccessorylist(accessorylist);
            respModel.setAccessoryMappingDto(null);
        } else {
            respModel = generateResponseObject("No data available", false, true);
        }
        return new ResponseEntity<>(respModel, HttpStatus.OK);
    }

    private AccessoryResponseModel generateResponseObject(String message, boolean success, boolean error) {
        AccessoryResponseModel respModel = new AccessoryResponseModel();
        if (success) {
            respModel.setSuccess(success);
            respModel.setSuccessMessage(message);
        } else {
            respModel.setError(error);
            respModel.setAccessory(null);
            respModel.setAccessorylist(null);
            respModel.setAccessoryMappingDto(null);
            respModel.setErrorMessage(message);
        }
        return respModel;
    }
    
    @CrossOrigin
	@PostMapping(value = "/uploadBulkUploadForModelAccessory")
	public ResponseEntity<?> uploadBulkUploadForAccessory(@RequestPart("file") MultipartFile bulkExcel,
		    @RequestPart("bumodel") BulkUploadModel bUModel) {
    	BulkUploadAccessories bulkUploadReq = new BulkUploadAccessories();
    	Integer empId=bUModel.getEmpId();
		Integer orgId=bUModel.getOrgid();
		BulkUploadResponse  response =null;
		if(null != empId) {
		bulkUploadReq.setEmpId(empId);
		}
		if(null != orgId) {
		bulkUploadReq.setOriganistionId(Long.valueOf(orgId));
		}
		AccessoryResponseModel  accessorydata =null;
		try {	
		
		response = accessoryService.processBulkExcelForAccesory(bulkUploadReq,bulkExcel);
		 
		} catch (Exception e) {
			BulkUploadResponse res = new BulkUploadResponse();
			List<String> FailedRecords =new ArrayList<>();
			String resonForFailure = e.getMessage();
			FailedRecords.add(resonForFailure);
			res.setFailedCount(0);
			res.setFailedRecords(FailedRecords);
			res.setSuccessCount(0);
			res.setTotalCount(0);
			return new ResponseEntity<>(res, HttpStatus.OK);
		}
		return new ResponseEntity<>(accessorydata, HttpStatus.CREATED);
		
    }
    
//    @CrossOrigin
//	@PostMapping(value = "/uploadBulkUploadForAccessoryVarient/{vehcileId}")
//	public ResponseEntity<?> uploadBulkUploadForAccessory2(@PathVariable(name="vehcileId") Integer vehcileId,@RequestPart("file") MultipartFile bulkExcel,
//		    @RequestPart("bumodel") BulkUploadModel bUModel) {
//    	BulkUploadAccessories bulkUploadReq = new BulkUploadAccessories();
//    	Integer empId=bUModel.getEmpId();
//		Integer orgId=bUModel.getOrgid();
//		BulkUploadResponse  response =null;
//    	if(null != empId) {
//		bulkUploadReq.setEmpId(empId);
//		}
//		if(null != orgId) {
//		bulkUploadReq.setOriganistionId(Long.valueOf(orgId));
//		}
//		if(null != vehcileId) {
//		bulkUploadReq.setVehicleId(vehcileId);
//		}
//		AccessoryResponseModel  accessorydata =null;
//		try {	
//			response = accessoryService.processBulkExcelForAccesory2(bulkUploadReq,bulkExcel);
//		} catch (Exception e) {
//			BulkUploadResponse res = new BulkUploadResponse();
//			List<String> FailedRecords =new ArrayList<>();
//			String resonForFailure = e.getMessage();
//			FailedRecords.add(resonForFailure);
//			res.setFailedCount(0);
//			res.setFailedRecords(FailedRecords);
//			res.setSuccessCount(0);
//			res.setTotalCount(0);
//			return new ResponseEntity<>(res, HttpStatus.OK);
//		}
//		return new ResponseEntity<>(accessorydata, HttpStatus.CREATED);
//		
//    }
//    
    @CrossOrigin
   	@PostMapping(value = "/uploadBulkUploadForAccessoryVarient/{vehcileId}")
   	public ResponseEntity<?> uploadBulkUploadForAccessory2(@PathVariable(name="vehcileId") Integer vehcileId,@RequestPart("file") MultipartFile bulkExcel,
   			@RequestParam Integer orgId,@RequestParam Integer empId,@RequestParam Integer branchId) {
       	BulkUploadAccessories bulkUploadReq = new BulkUploadAccessories();
//       	Integer empId=bUModel.getEmpId();
//   		Integer orgId=bUModel.getOrgid();
   		BulkUploadResponse  response =null;
       	if(null != empId) {
   		bulkUploadReq.setEmpId(empId);
   		}
   		if(null != orgId) {
   		bulkUploadReq.setOriganistionId(Long.valueOf(orgId));
   		}
   		if(null != vehcileId) {
   		bulkUploadReq.setVehicleId(vehcileId);
   		}
   		AccessoryResponseModel  accessorydata =null;
   		try {	
   			response = accessoryService.processBulkExcelForAccesory2(bulkUploadReq,bulkExcel);
  
   		
   		} catch (Exception e) {
   			BulkUploadResponse res = new BulkUploadResponse();
   			List<String> FailedRecords =new ArrayList<>();
   			String resonForFailure = e.getMessage();
   			FailedRecords.add(resonForFailure);
   			res.setFailedCount(0);
   			res.setFailedRecords(FailedRecords);
   			res.setSuccessCount(0);
   			res.setTotalCount(0);
   			return new ResponseEntity<>(res, HttpStatus.OK);
   		}
   		return new ResponseEntity<>(response, HttpStatus.CREATED);
   		
       }
}

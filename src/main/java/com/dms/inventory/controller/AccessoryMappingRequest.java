package com.dms.inventory.controller;

import com.dms.inventory.entities.AccessoryMapping;
import com.dms.inventory.repository.AccessoryMappingRepository;
import com.dms.inventory.response.AccessoryMappingResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.Objects;

@RestController
@RequestMapping("/inventory/accessoriesMapping")
public class AccessoryMappingRequest {

    private final AccessoryMappingRepository accessoryMappingRepo;

    public AccessoryMappingRequest(AccessoryMappingRepository accessoryMappingRepo) {
        this.accessoryMappingRepo = accessoryMappingRepo;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<AccessoryMappingResponseModel> addAccessorymapping(
            @RequestBody() AccessoryMapping accessorymapping) {
        AccessoryMapping accessorymappingmodel = accessoryMappingRepo.save(accessorymapping);
        AccessoryMappingResponseModel respModel = generateResponseObject(
                "Accessory mapping details successfully created", true, false);
        respModel.setAccessoryMapping(accessorymappingmodel);
        respModel.setAccessoryMappinglist(null);
        return new ResponseEntity<>(respModel, HttpStatus.CREATED);
    }
    
    @PostMapping(value = "/create_bulkupload")
    public ResponseEntity<AccessoryMappingResponseModel> addAccessorymapping(
            @RequestBody() List<AccessoryMapping> accessorymappingList) {
    	AccessoryMapping accessorymappingmodel=null;
    	AccessoryMappingResponseModel respModel=null;
    	for(AccessoryMapping accessorymapping:accessorymappingList) {
        accessorymappingmodel = accessoryMappingRepo.save(accessorymapping);
        respModel = generateResponseObject(
                "Accessory mapping details successfully created", true, false);
        respModel.setAccessoryMapping(accessorymappingmodel);
        respModel.setAccessoryMappinglist(null);
        
    	}
        return new ResponseEntity<>(respModel, HttpStatus.CREATED);
    }

    @CrossOrigin
    @PutMapping(value = "/update")
    public ResponseEntity<AccessoryMappingResponseModel> updateaccessorymappingDetails(
            @RequestBody() AccessoryMapping accessorymapping) {
        AccessoryMappingResponseModel respModel = null;
        AccessoryMapping accessMapping = null;
        if (Objects.nonNull(accessorymapping.getId())) {
            accessMapping = updateaccessorymapping(accessorymapping);
            respModel = generateResponseObject("Accessory Mapping details updated", true, false);
            respModel.setAccessoryMapping(accessMapping);
            respModel.setAccessoryMappinglist(null);
        } else {
            respModel = generateResponseObject("Accessory Mapping ID should not be a null", false, true);
        }
        return new ResponseEntity<>(respModel, HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping(value = "/delete", produces = "application/json")
    public ResponseEntity<AccessoryMappingResponseModel> deleteAccessoryMappingDetails(
            @RequestParam(required = true) int accessoryMappingID) {
        accessoryMappingRepo.deleteById(accessoryMappingID);
        AccessoryMappingResponseModel respModel = generateResponseObject(
                "Accessory Mapping details Deleted Successfully", true, false);
        return new ResponseEntity<>(respModel, HttpStatus.OK);
    }

    private AccessoryMapping updateaccessorymapping(AccessoryMapping accessorymapping) {
        AccessoryMapping accMaaping = accessoryMappingRepo.findById(accessorymapping.getId()).get();
        accMaaping.setAccessoriesList(accessorymapping.getAccessoriesList());
        accMaaping.setCost(accessorymapping.getCost());
        accMaaping.setKitName(accessorymapping.getKitName());
        accMaaping.setOrganisationId(accessorymapping.getOrganisationId());
        accMaaping.setVehicleId(accessorymapping.getVehicleId());
        accessoryMappingRepo.save(accMaaping);

        return accMaaping;
    }

    private AccessoryMappingResponseModel generateResponseObject(String message, boolean success, boolean error) {
        AccessoryMappingResponseModel respModel = new AccessoryMappingResponseModel();
        if (success) {
            respModel.setSuccess(success);
            respModel.setSuccessMessage(message);
        } else {
            respModel.setError(error);
            respModel.setAccessoryMapping(null);
            respModel.setAccessoryMappinglist(null);
            respModel.setErrorMessage(message);
        }
        return respModel;
    }

}

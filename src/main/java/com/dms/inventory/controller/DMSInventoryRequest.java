package com.dms.inventory.controller;

import com.dms.inventory.model.VehicleInventoryModel;
import com.dms.inventory.model.VehicleInventoryResposeModel;
import com.dms.inventory.repository.VehicleInventoryRepository;
import com.dms.inventory.requestImpl.DMSInventoryRequestImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/dms/inventory")
public class DMSInventoryRequest {

    private final static Logger logger = Logger.getLogger(DMSInventoryRequest.class.getName());

    private final VehicleInventoryRepository inventoryRepo;

    private final
    DMSInventoryRequestImpl inventoryReqImpl;

    public DMSInventoryRequest(VehicleInventoryRepository inventoryRepo, DMSInventoryRequestImpl inventoryReqImpl) {
        this.inventoryRepo = inventoryRepo;
        this.inventoryReqImpl = inventoryReqImpl;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleInventoryResposeModel> addVechicleDetails(
            @RequestBody() VehicleInventoryModel vehicleDetails) {
        logger.log(Level.INFO, "create new entry");
        VehicleInventoryModel inventoryModel = inventoryReqImpl.createVehicleEntry(vehicleDetails);
        VehicleInventoryResposeModel respModel = generateResponseObject("Vehicle details successfully created", true,
                false);
        respModel.setVehicleInventory(inventoryModel);
        respModel.setInventoryList(null);
        return new ResponseEntity<>(respModel, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleInventoryResposeModel> updateVehicleDetails(
            @RequestBody() VehicleInventoryModel vehicleDetails) {
        logger.log(Level.INFO, "update Details Vehicle Details");
        VehicleInventoryResposeModel respModel = null;
        VehicleInventoryModel inventoryModel = null;
        if (Objects.nonNull(vehicleDetails.getId())) {
            inventoryModel = inventoryReqImpl.updateVehicleEntry(vehicleDetails);
            respModel = generateResponseObject("Vehicle details updated", true, false);
            respModel.setVehicleInventory(inventoryModel);
            respModel.setInventoryList(null);
        } else {
            respModel = generateResponseObject("Vehicle ID should not be a null", false, true);
        }
        return new ResponseEntity<>(respModel, HttpStatus.OK);
    }

    @RequestMapping(value = "/listall", method = RequestMethod.GET)
    public ResponseEntity<VehicleInventoryResposeModel> getAllVehicleDetails() {
        logger.log(Level.INFO, "List All Vehicle Details");
        VehicleInventoryResposeModel respModel = null;
        List<VehicleInventoryModel> vehicleDetails = inventoryReqImpl.getAllVehicleDetails();
        logger.log(Level.INFO, "List Vehicle Details count " + vehicleDetails.size());
        if (vehicleDetails.size() > 0) {
            respModel = generateResponseObject("Vehicles Listed Successfully", true, false);
            respModel.setInventoryList(vehicleDetails);
            respModel.setVehicleInventory(null);
        } else {
            respModel = generateResponseObject("No Vehicle Details found", false, true);
        }
        return new ResponseEntity<>(respModel, HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<Object> getVehicleDetails(@RequestParam(required = true) int vehicleId) {
        logger.log(Level.INFO, "Get Vehicle details for id " + vehicleId);
        VehicleInventoryResposeModel respModel = null;
        VehicleInventoryModel vehicleDetails = inventoryReqImpl.getVehiclebyId(vehicleId);
        if (Objects.nonNull(vehicleDetails)) {
            respModel = generateResponseObject("Vehicle Details Listed Successfully", true, false);
            respModel.setInventoryList(null);
            respModel.setVehicleInventory(vehicleDetails);
        } else {
            respModel = generateResponseObject("No Vehicle Details found", false, true);
        }
        return new ResponseEntity<>(respModel, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<VehicleInventoryResposeModel> deleteVehicleDetails(
            @RequestParam(required = true) int vehicleId) {
        logger.log(Level.INFO, "Delete Vehicle details ");
        inventoryReqImpl.deleteVehicleEntry(vehicleId);
        VehicleInventoryResposeModel respModel = generateResponseObject("Vehicle Details Deleted Successfully", true,
                false);
        return new ResponseEntity<>(respModel, HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<VehicleInventoryResposeModel> searchVehicleDetails(
            @RequestParam(value = "org_id", required = true) long org_id,
            @RequestParam(value = "branch_id", required = true) long branch_id,
            @RequestParam(value = "model", required = true) String model,
            @RequestParam(value = "allotedDate", required = false) String allotedDate,
            @RequestParam(value = "deAllotedDate", required = false) String deAllotedDate) {
        logger.log(Level.INFO, "Serach Inventory for org id" + org_id + " branch id" + branch_id + " model " + model
                + " Alloted Date " + allotedDate + " de-Alloted Date " + deAllotedDate);
        VehicleInventoryResposeModel respModel = null;
        List<VehicleInventoryModel> searchList = inventoryReqImpl.searchInventory(org_id, branch_id, model, allotedDate,
                deAllotedDate);
        if (searchList.isEmpty() || Objects.isNull(searchList)) {
            respModel = generateResponseObject("No Vehicle Details found", false, true);
        } else {
            respModel = generateResponseObject("Search is successful", true, false);
            respModel.setInventoryList(searchList);
            respModel.setVehicleInventory(null);
        }

        return new ResponseEntity<>(respModel, HttpStatus.OK);
    }

    private VehicleInventoryResposeModel generateResponseObject(String message, boolean success, boolean error) {
        VehicleInventoryResposeModel respModel = new VehicleInventoryResposeModel();
        if (success) {
            respModel.setSuccess(success);
            respModel.setSuccessMessage(message);
        } else {
            respModel.setError(error);
            respModel.setInventoryList(null);
            respModel.setVehicleInventory(null);
            respModel.setErrorMessage(message);
        }
        return respModel;
    }

    @GetMapping("/vehicle/{model}/{varient}/{fuel}/{color}/{transmission_type}/{alloted}")
    public ResponseEntity<VehicleInventoryResposeModel> getvehicleinventory(@PathVariable("model") String model,
                                                                            @PathVariable("varient") String varient,
                                                                            @PathVariable("fuel") String fuel,
                                                                            @PathVariable("color") String color,
                                                                            @PathVariable("transmission_type") String transmission_type,
                                                                            @PathVariable("alloted") int alloted) {
        logger.info("Inventory data by model, varient, etc.." + model);
        VehicleInventoryResposeModel respModel = null;
        VehicleInventoryModel inventory = inventoryReqImpl.getVehicleinventory(model, varient, fuel, color,
                transmission_type, alloted);
        if (Objects.nonNull(inventory)) {
            logger.info("Inventory object found");
            respModel = generateResponseObject("Search is successful", true, false);
            respModel.setVehicleInventory(inventory);
            respModel.setInventoryList(null);
        } else {
            logger.info("No Vehicle Details found");
            respModel = generateResponseObject("No Vehicle Details found", false, true);
        }

        return new ResponseEntity<>(respModel, HttpStatus.OK);
    }

    @GetMapping("/vehicleageing/{model}/{varient}/{fuel}/{color}/{transmission_type}/{alloted}")
    public ResponseEntity<VehicleInventoryResposeModel> getvehicleinventoryByAgeing(@PathVariable("model") String model,
                                                                            @PathVariable("varient") String varient,
                                                                            @PathVariable("fuel") String fuel,
                                                                            @PathVariable("color") String color,
                                                                            @PathVariable("transmission_type") String transmission_type,
                                                                            @PathVariable("alloted") int alloted) {
        logger.info("Inventory data by model, varient, etc.." + model);
        VehicleInventoryResposeModel respModel = null;
        VehicleInventoryModel inventory = inventoryReqImpl.getVehicleinventory(model, varient, fuel, color,
                transmission_type, alloted);
        if (Objects.nonNull(inventory)) {
            logger.info("Inventory object found");
            respModel = generateResponseObject("Search is successful", true, false);
            respModel.setVehicleInventory(inventory);
            respModel.setInventoryList(null);
        } else {
            logger.info("No Vehicle Details found");
            respModel = generateResponseObject("No Vehicle Details found", false, true);
        }

        return new ResponseEntity<>(respModel, HttpStatus.OK);
    }

    @GetMapping("/vehicleinvvin/{model}/{varient}/{vin}/{engineno}/{chassisno}/{alloted}")
    public ResponseEntity<VehicleInventoryResposeModel> getvehicleinventoryByvinNo(@PathVariable("model") String model,
                                                                            @PathVariable("varient") String varient,
                                                                            @PathVariable("vin") String vin,
                                                                            @PathVariable("engineno") String engineno,
                                                                            @PathVariable("chassisno") String chassisno,
                                                                            @PathVariable("alloted") int alloted) {
        logger.info("Inventory data by model, varient, etc.." + model);
        VehicleInventoryResposeModel respModel = null;
        VehicleInventoryModel inventory = inventoryReqImpl.getVehicleinventoryByVin(model, varient, vin, engineno,
        		chassisno, alloted);
        if (Objects.nonNull(inventory)) {
            logger.info("Inventory object found");
            respModel = generateResponseObject("Search is successful", true, false);
            respModel.setVehicleInventory(inventory);
            respModel.setInventoryList(null);
        } else {
            logger.info("No Vehicle Details found");
            respModel = generateResponseObject("No Vehicle Details found", false, true);
        }

        return new ResponseEntity<>(respModel, HttpStatus.OK);
    }
    
    @PutMapping("/updateInventory/{id}")
    public ResponseEntity<VehicleInventoryResposeModel> updatevehicleinventory(@PathVariable("id") int id,
                                                                               @RequestBody() VehicleInventoryModel vehicleDetails) {
        logger.log(Level.INFO, "update Vehicle Details");
        VehicleInventoryResposeModel respModel = null;
        VehicleInventoryModel vehicleinventroy = inventoryReqImpl.getVehiclebyId(id);
        if (Objects.nonNull(vehicleinventroy)) {
            vehicleinventroy.setAlloted(vehicleDetails.getAlloted());
            vehicleinventroy.setLead_id(vehicleDetails.getLead_id());
            inventoryReqImpl.updateVehicleEntry(vehicleinventroy);
            respModel = generateResponseObject("Vehicle details updated", true, false);
            respModel.setVehicleInventory(vehicleinventroy);
            respModel.setInventoryList(null);
        } else {
            respModel = generateResponseObject("Vehicle ID should not be a null", false, true);
        }
        return new ResponseEntity<>(respModel, HttpStatus.OK);
    }

}

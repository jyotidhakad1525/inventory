package com.dms.inventory.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Objects;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dms.inventory.entities.VehicleSoldInfo;
import com.dms.inventory.exception.EvaluationServiceException;
import com.dms.inventory.model.InventoryUsedCarDto;
import com.dms.inventory.model.VehicleSoldInforDto;
import com.dms.inventory.requestImpl.UsedCarServiceImpl;
import com.dms.inventory.response.ResponseJson;
import com.google.gson.Gson;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/buyUsedCar")
public class UsedCarServiceRequest {

    Logger logger = LoggerFactory.getLogger(UsedCarServiceRequest.class);

    private final UsedCarServiceImpl service;

    public UsedCarServiceRequest(UsedCarServiceImpl service) {
        this.service = service;
    }
	
	@GetMapping("/fetchBuyUsedCar")
	@Operation(summary = "API to fetch document details")
	public ResponseEntity<?> fetchBuyUsedCars(@RequestParam(value = "dealerCode", required = true) String dealerCode,

			@RequestParam(required = false) String make, @RequestParam(required = false) String model,

			@RequestParam(required = false) BigDecimal price1,

			@RequestParam(required = false) BigDecimal price2,

			@RequestParam(name = "offset") Integer offset, @RequestParam(name = "limit") Integer limit)
			throws ParseException {
		logger.info("fetch the vahan portal api start");
		ResponseJson responseJson = new ResponseJson();
		try {
			responseJson.setStatusCode(400);
			responseJson.setStatus("fail");
			responseJson.setResult(null);
			responseJson.setResult(service.getByDealerCode(dealerCode, make, model, price1, price2, offset, limit));
			responseJson.setStatusCode(200);
			responseJson.setStatus("success");
			responseJson.setShowMessage("Success");
			return new ResponseEntity<>(responseJson, HttpStatus.OK);
		} catch (Exception e) {
			if (e instanceof NestedRuntimeException) {
				logger.error("create()..........NestedRuntimeException:" + e.getMessage());
				responseJson.setShowMessage(e.getMessage());
			} else if (e instanceof RuntimeException) {
				logger.error("create()..........RuntimeException:" + e.getMessage());
				responseJson.setShowMessage(e.getMessage());
			} else {
				logger.error("create()..........Exception:" + e.getMessage());
				responseJson.setShowMessage(e.getMessage());
			}
			e.printStackTrace();
			return new ResponseEntity<>(responseJson, HttpStatus.OK);
		}

	}
	 

    @GetMapping("/fetchBuyUsedCar/{id}")
    @Operation(summary = "API to fetch document details")
    public ResponseEntity<?> fetchBuyUsedCar(@PathVariable("id") long id) throws ParseException {
        logger.info("fetch the vahan portal api start");
        ResponseJson responseJson = new ResponseJson();
        try {
            responseJson.setStatusCode(400);
            responseJson.setStatus("fail");
            responseJson.setResult(null);
            responseJson.setResult(service.getByCarId(id));
            responseJson.setStatusCode(200);
            responseJson.setStatus("success");
            responseJson.setShowMessage("Success");
            return new ResponseEntity<>(responseJson, HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof NestedRuntimeException) {
                logger.error("create()..........NestedRuntimeException:" + e.getMessage());
                responseJson.setShowMessage(e.getMessage());
            } else if (e instanceof RuntimeException) {
                logger.error("create()..........RuntimeException:" + e.getMessage());
                responseJson.setShowMessage(e.getMessage());
            } else {
                logger.error("create()..........Exception:" + e.getMessage());
                responseJson.setShowMessage(e.getMessage());
            }
            e.printStackTrace();
            return new ResponseEntity<>(responseJson, HttpStatus.OK);
        }

    }
    
    @PostMapping("/saveBuyUsedCar")
    @Operation(summary = "API to save Documents Details")
    
    public ResponseEntity<?> saveVehicle(@RequestParam String data,@RequestParam MultipartFile[] images) throws ParseException {
        ResponseJson responseJson = new ResponseJson();
        Gson gson = new Gson();
        InventoryUsedCarDto inventory =gson.fromJson(data, InventoryUsedCarDto.class);
        try {
            responseJson.setStatusCode(400);
            responseJson.setStatus("fail");
            responseJson.setResult(null);
            if(inventory.getImagefiles()==null) {
            	inventory.setImagefiles(images);
            }
            if (Objects.isNull(inventory.getDealerCode())) {
                throw new EvaluationServiceException();
            } else {
                logger.info("*SaveBuyUsedCar start");
                responseJson.setResult(service.createInventory(inventory));
                responseJson.setStatusCode(200);
                responseJson.setStatus("success");
                responseJson.setShowMessage("Success");
                return new ResponseEntity<>(responseJson, HttpStatus.OK);
            }
        } catch (Exception e) {
            if (e instanceof NestedRuntimeException) {
                logger.error("create()..........NestedRuntimeException:" + e.getMessage());
                responseJson.setShowMessage(e.getMessage());
            } else if (e instanceof RuntimeException) {
                logger.error("create()..........RuntimeException:" + e.getMessage());
                responseJson.setShowMessage(e.getMessage());
            } else {
                logger.error("create()..........Exception:" + e.getMessage());
                responseJson.setShowMessage(e.getMessage());
            }
            e.printStackTrace();
            return new ResponseEntity<>(responseJson, HttpStatus.OK);
        }
    }

    @PostMapping("/updateBuyUsedCar")
    @Operation(summary = "API to update Documents  details ")
    public ResponseEntity<?> updateVehicle(@RequestParam String data,@RequestParam MultipartFile[] images) {
        ResponseJson responseJson = new ResponseJson();
        try {
            responseJson.setStatusCode(400);
            responseJson.setStatus("fail");
            responseJson.setResult(null);
            Gson gson = new Gson();
            InventoryUsedCarDto inventory =gson.fromJson(data, InventoryUsedCarDto.class);
            if(images.length>0) {
            	inventory.setImagefiles(images);
            }
            if (Objects.isNull(Objects.isNull(inventory.getId()))) {
                throw new EvaluationServiceException();
            } else {
                logger.info("*Update Stated  start");
                if (inventory.getId() != null) {
                    InventoryUsedCarDto inventoryUsedCarDto = service.updateInventory(inventory);
                    responseJson.setStatusCode(200);
                    responseJson.setStatus("success");
                    responseJson.setShowMessage("Success");

                    if (Objects.nonNull(inventoryUsedCarDto)) {
                        responseJson.setResult(inventoryUsedCarDto);
                        return new ResponseEntity<>(responseJson, HttpStatus.OK);
                    } else {
                        responseJson.setResult(inventoryUsedCarDto);
                        return new ResponseEntity<>(responseJson, HttpStatus.NOT_FOUND);
                    }
                }
                return null;
            }
        } catch (Exception e) {
            if (e instanceof NestedRuntimeException) {
                logger.error("create()..........NestedRuntimeException:" + e.getMessage());
                responseJson.setShowMessage(e.getMessage());
            } else if (e instanceof RuntimeException) {
                logger.error("create()..........RuntimeException:" + e.getMessage());
                responseJson.setShowMessage(e.getMessage());
            } else {
                logger.error("create()..........Exception:" + e.getMessage());
                responseJson.setShowMessage(e.getMessage());
            }
            e.printStackTrace();
            return new ResponseEntity<>(responseJson, HttpStatus.OK);
        }
    }
    
    
    @PostMapping("/vehicleSoldInfomation")
    @Operation(summary = "API to update Documents  details ")
    public ResponseEntity<?> vehicleSoldInfomation(@RequestParam String data,@RequestParam MultipartFile panCardFile,@RequestParam MultipartFile addressProofFile) {
        ResponseJson responseJson = new ResponseJson();
       
        try {
            responseJson.setStatusCode(400);
            responseJson.setStatus("fail");
            responseJson.setResult(null);
            Gson gson = new Gson();
            VehicleSoldInforDto vehicleSoldInforDto =gson.fromJson(data, VehicleSoldInforDto.class);
            if(panCardFile!=null) {
            	vehicleSoldInforDto.setPanCardDocFile(panCardFile);
            }
            if(addressProofFile!=null) {
            	vehicleSoldInforDto.setAddressProofFile(addressProofFile);
            }
            if (Objects.isNull(vehicleSoldInforDto.getCustomerName())) {
                throw new EvaluationServiceException();
            } else {
               
                logger.info("*Saveing Vehicle Sold info");

                if (vehicleSoldInforDto.getCustomerName() != null) {
                	VehicleSoldInfo vehicleSoldInforDtoResponse = service.createVehcileSoldInfo(vehicleSoldInforDto);
                    responseJson.setStatusCode(200);
                    responseJson.setStatus("success");
                    responseJson.setShowMessage("Success");

                    if (Objects.nonNull(vehicleSoldInforDtoResponse)) {
                        responseJson.setResult(vehicleSoldInforDtoResponse);
                        return new ResponseEntity<>(responseJson, HttpStatus.OK);
                    } else {
                        responseJson.setResult(vehicleSoldInforDtoResponse);
                        return new ResponseEntity<>(responseJson, HttpStatus.NOT_FOUND);
                    }
                }
                return null;
            }
        } catch (Exception e) {
            if (e instanceof NestedRuntimeException) {
                logger.error("create()..........NestedRuntimeException:" + e.getMessage());
                responseJson.setShowMessage(e.getMessage());
            } else if (e instanceof RuntimeException) {
                logger.error("create()..........RuntimeException:" + e.getMessage());
                responseJson.setShowMessage(e.getMessage());
            } else {
                logger.error("create()..........Exception:" + e.getMessage());
                responseJson.setShowMessage(e.getMessage());
            }
            e.printStackTrace();
            return new ResponseEntity<>(responseJson, HttpStatus.OK);
        }
    }

    @DeleteMapping("/deleteBuyUsedCarDetails/{id}")
    @Operation(summary = "API to delete Documents details ")
    @Transactional
    public ResponseEntity<?> deleteEvalutionOldCar(@PathVariable Integer id) {
        ResponseJson responseJson = new ResponseJson();
        try {
            responseJson.setStatusCode(400);
            responseJson.setStatus("fail");
            responseJson.setResult(null);
            logger.info("*delete Stated  start");
            responseJson.setResult(service.deleteDocument(id));
            responseJson.setStatusCode(200);
            responseJson.setStatus("success");
            responseJson.setShowMessage("Success");
            return new ResponseEntity<>(responseJson, HttpStatus.OK);

        } catch (Exception e) {
            if (e instanceof NestedRuntimeException) {
                logger.error("create()..........NestedRuntimeException:" + e.getMessage());
                responseJson.setShowMessage(e.getMessage());
            } else if (e instanceof RuntimeException) {
                logger.error("create()..........RuntimeException:" + e.getMessage());
                responseJson.setShowMessage(e.getMessage());
            } else {
                logger.error("create()..........Exception:" + e.getMessage());
                responseJson.setShowMessage(e.getMessage());
            }
            e.printStackTrace();
            return new ResponseEntity<>(responseJson, HttpStatus.OK);
        }
    }
}

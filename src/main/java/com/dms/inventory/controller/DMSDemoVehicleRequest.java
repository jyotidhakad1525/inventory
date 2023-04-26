package com.dms.inventory.controller;

import com.dms.inventory.common.BaseResponse;
import com.dms.inventory.common.Utils;
import com.dms.inventory.filters.BaseFilter;
import com.dms.inventory.model.DemoVehicleRequest;
import com.dms.inventory.model.DemoVehicleResponse;
import com.dms.inventory.repository.DemoTestDriveVehicleRepository;
import com.dms.inventory.requestImpl.DMSDemoVehicleRequestImpl;
import com.dms.inventory.service.AddVehicleInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"demoVehicle"})
public class DMSDemoVehicleRequest {

    private final DMSDemoVehicleRequestImpl helper;

    @Autowired
    private DemoTestDriveVehicleRepository demoTestDriveVehicleRepository;

    public DMSDemoVehicleRequest(DMSDemoVehicleRequestImpl helper) {
        this.helper = helper;
    }
    @CrossOrigin
    @RequestMapping(value = "create", method = {RequestMethod.POST})
    public ResponseEntity<BaseResponse> create(@RequestBody DemoVehicleRequest request) {
        BaseResponse baseResponse;
        List<String> rcNumber = demoTestDriveVehicleRepository.getRcNumber(request.getVehicle().getRcNo());
        List<String> chassisNumber = demoTestDriveVehicleRepository.getChassisNumber(request.getVehicle().getChassisNo());

        if(rcNumber!=null && rcNumber.size()>0){
            baseResponse =  Utils.failureResponse("Rc number must be unique");
            return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.BAD_REQUEST);
        } else if (chassisNumber!=null && chassisNumber.size()>0) {
            baseResponse =  Utils.failureResponse("Chassis number must be unique");
            return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.BAD_REQUEST);
        }else {
            baseResponse = helper.create(request);
        }
        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }
    @CrossOrigin
    @RequestMapping(value = "update", method = {RequestMethod.PUT})
    public ResponseEntity<BaseResponse> update(@RequestBody DemoVehicleRequest request) {
    	BaseResponse baseResponse;
    	List<Integer> rcNumber = demoTestDriveVehicleRepository.getRcNumberRecordId(request.getVehicle().getRcNo());
        List<Integer> chassisNumber = demoTestDriveVehicleRepository.getChassisNumberRecordId(request.getVehicle().getChassisNo());

        if(rcNumber!=null && rcNumber.size()>0){
        	if(rcNumber.size()==1 && rcNumber.get(0) == request.getVehicle().getId()) {
        		baseResponse = helper.update(request);
        	}else {
        		baseResponse =  Utils.failureResponse("Rc number must be unique");
                return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.BAD_REQUEST);
        	}
            
        } else if (chassisNumber!=null && chassisNumber.size()>0) {
        	if(chassisNumber.size()==1 && chassisNumber.get(0) == request.getVehicle().getId()) {
        		baseResponse = helper.update(request);
        	}else {
        		baseResponse =  Utils.failureResponse("Chassis number must be unique");
                return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.BAD_REQUEST);
        	}
        }else {
        	baseResponse = helper.update(request);
        }
    	
    	
    	
//        BaseResponse baseResponse = helper.update(request);
        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }
    @CrossOrigin
    @RequestMapping(value = {"vehicles"}, method = {RequestMethod.GET})
    public ResponseEntity<DemoVehicleResponse> search(BaseFilter request) {
    	System.out.println("Request "+request);
        DemoVehicleResponse response = helper.search(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @CrossOrigin
    @RequestMapping(value = "delete/{id}", method = {RequestMethod.DELETE})
    public ResponseEntity<BaseResponse> delete(@PathVariable int id) {
        BaseResponse baseResponse = helper.deleteDemovehicles(id);
        return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
    }

}

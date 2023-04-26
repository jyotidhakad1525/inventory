package com.dms.inventory.exception;

import com.dms.inventory.common.BaseResponse;
import com.dms.inventory.common.ErrorMessages;
import com.dms.inventory.model.VehicleInventoryResposeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class DMSInventoryExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(DMSInventoryExceptionHandler.class);

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<VehicleInventoryResposeModel> handelException(Exception ex) {
        logger.error(ex.getMessage(), ex);
        VehicleInventoryResposeModel respModel = new VehicleInventoryResposeModel();
        respModel.setError(true);
        respModel.setSuccess(false);
        respModel.setErrorMessage(ex.getMessage());
        respModel.setSuccessMessage("");
        respModel.setVehicleInventory(null);
        respModel.setInventoryList(null);
        return new ResponseEntity<VehicleInventoryResposeModel>(respModel, HttpStatus.OK);
    }

    @ExceptionHandler(value = {HttpServerErrorException.class})
    public ResponseEntity<BaseResponse> defaultErrorHandler(HttpServerErrorException exception) {
        logger.error(exception.getMessage(), exception);
        BaseResponse response = failureResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {HttpClientErrorException.class})
    public ResponseEntity<BaseResponse> defaultErrorHandler(HttpClientErrorException exception) {
        logger.error(exception.getMessage(), exception);
        BaseResponse response = failureResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public BaseResponse failureResponse(String code, String desc) {
        BaseResponse response = new BaseResponse();
        response.setStatusCode(code);
        response.setStatus(ErrorMessages.FAILURE.message());
        response.setStatusDescription(desc);
        return response;
    }
}

package com.dms.inventory.common;

public enum ErrorMessages {

    SUCCESS("200", "SUCCESS"),
    FAILURE("200", "FAILURE"),
    DATA_NOT_FOUND("5000", "Data not found "),
    SOURCE_INPUT_REQUIRE("5002", "input Fields %s  is required "),
    INVALID_DATA("5003", "Data not found "),
    ALREADY_VEHICLE_DELETED("5004", "Alredy vehicle deleted "),
    VEHICLE_BOOKING_AVAILABLE("200", "Selected Vehicle(s) %s on requested date %s are available!");


    private final String code;
    private final String message;

    ErrorMessages(String errorCode, String errorMessage) {
        this.code = errorCode;
        this.message = errorMessage;
    }

    public String code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}

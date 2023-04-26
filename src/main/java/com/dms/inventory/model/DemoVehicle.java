package com.dms.inventory.model;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.dms.inventory.common.DmsBase;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DemoVehicle extends DmsBase {
    private int id;
    private String chassisNo;
    private BigInteger colorId;
    private String createdBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdDatetime;
    private List<Document> documents;
    private String modifiedBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date modifiedDatetime;
    private String rcNo;
    private String remarks;
    private String status;
    private String type;
    private BigInteger varientId;
    private BigInteger vehicleId;
    private String engineno;
    private String insurenceCompany;
    private String insurenceNo;
    private BigInteger kmsReading;
    private AdditionalVehicleInfo vehicleInfo;

}

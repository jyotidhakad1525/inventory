package com.dms.inventory.model;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VehicleSoldInforDto {
	
    private Integer InventoryUsedCarId;
    private String customerName;
    private String customerMobileNumber;
    private String panCardNumber;
    private String gstNumber;
    private String pincode;
    private String houseNo;
    private String villageOrTown;
    private String district;
    private String isUrban;
    private String isRural;
    private String street;
    private String city;
    private String state;
    private String vehicleSellingPrice;
    private String salesConsultantName;
    private Date SellingDate;
    private MultipartFile panCardDocFile;
    private MultipartFile addressProofFile;
}

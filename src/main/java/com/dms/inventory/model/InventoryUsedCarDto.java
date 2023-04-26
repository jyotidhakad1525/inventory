package com.dms.inventory.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InventoryUsedCarDto {
	private Integer id;
	private long organizationId;
	private String location;
	private String dealerCode;
	private String make;
	private String model;
	private String variant;
	private String color;
	private String fuel;
	private String transmission;
	private String makingMonth;
	private String makingYear;
	private String rcNumber;
	private Date registrationDate;
	private Date registrationValidUpto;
	private String vinNumber;
	private String engineNumber;
	private String chassisNumber;
	private String noOfOwners;
	private Date vehiclePurchaseDate;
	private BigDecimal vehiclePurchasePrice;
	private String insuranceType;
	private Date insuranceValidUpto;
	private BigDecimal drivenKms;
	private BigDecimal vehicleSellingPrice;
	private String evalutorName;
	
	private String createdBy;
	private Date createdDatetime;
	private String modifiedBy;
	private Date modifiedDate;
	private MultipartFile[] imagefiles;
	private String extraField;
	private List<String> documentList;

}

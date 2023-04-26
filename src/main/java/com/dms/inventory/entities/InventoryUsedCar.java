package com.dms.inventory.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "inventory_used_car2")
public class InventoryUsedCar {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "organization_id")
    private Long organizationId;
    @Column(name = "location")
	private String location;
    @Column(name = "dealer_code")
	private String dealerCode;
    @Column(name = "make")
	private String make;
    @Column(name = "model")
	private String model;
    @Column(name = "variant")
	private String variant;
    @Column(name = "color")
	private String color;
    @Column(name = "fuel")
	private String fuel;
    @Column(name = "transmission")
	private String transmission;
    @Column(name = "making_month")
	private String makingMonth;
    @Column(name = "making_year")
	private String makingYear;
    @Column(name = "rc_number")
    private String rcNumber;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "registration_date")
	private Date registrationDate;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "registration_valid_upto")
	private Date registrationValidUpto;
    @Column(name = "vin_number")
	private String vinNumber;
    @Column(name = "engine_number")
	private String engineNumber;
    @Column(name = "chassis_number")
	private String chassisNumber;
    @Column(name = "no_of_owners")
	private String noOfOwners;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "vehicle_purchase_date")
	private Date vehiclePurchaseDate;
    @Column(name = "vehicle_purchase_price")
	private BigDecimal vehiclePurchasePrice;
    @Column(name = "Insurance_type")
	private String InsuranceType; 
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "insurance_valid_upto")
    private Date insuranceValidUpto;
    @Column(name = "driven_kms")
	private BigDecimal drivenKms;
    @Column(name = "vehicle_selling_price")
	private BigDecimal vehicleSellingPrice;
    @Column(name = "evalutor_name")
	private String evalutorName;
    @Column(name = "images")
    private String images;  
    @Column(name = "created_by")
	private String createdBy;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "created_date_time")
	private Date createdDatetime;
    @Column(name = "modified_by")
	private String modifiedBy;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "modified_date")
	private Date modifiedDate;
}


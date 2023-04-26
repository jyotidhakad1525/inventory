package com.dms.inventory.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "vehicle_sold_info")
public class VehicleSoldInfo {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "inventory_used_car_id")
	private Integer InventoryUsedCarId;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "customer_mobile_number")
	private String customerMobileNumber;

	@Column(name = "pan_card_number")
	private String panCardNumber;

	@Column(name = "gst_number")
	private String gstNumber;

	@Column(name = "pincode")
	private String pincode;

	@Column(name = "house_no")
	private String houseNo;

	@Column(name = "village_or_town")
	private String villageOrTown;

	@Column(name = "district")
	private String district;

	@Column(name = "is_urban")
	private String isUrban;

	@Column(name = "is_rural")
	private String isRural;

	@Column(name = "street")
	private String street;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "vehicle_selling_price")
	private String vehicleSellingPrice;

	@Column(name = "sales_consultant_name")
	private String salesConsultantName;

	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "selling_date")
	private Date SellingDate;

	@Column(name = "panCard_doc_file")
	private String panCardDocFile;

	@Column(name = "address_proof_doc_file")
	private String addressProofDocFile;

	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "created_datetime")
	private Date createdDatetime;

}

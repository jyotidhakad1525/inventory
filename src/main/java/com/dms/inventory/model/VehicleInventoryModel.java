package com.dms.inventory.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class VehicleInventoryModel {

	 private int id;
	    private String org_id;
	    private long branch_id;
	    private String rc_no;
	    private String chassis_no;
	    private String engineno;
	    private String vin_number;
	    private String key_no;
	    private String model;
	    private String variant;
	    private String fuel;
	    private String transmission;
	    private String colour;
	    private int alloted;
	    private String universel_id;
	    private Date alloted_date;
	    private Date deallocation_date;
	    private Date created_datetime;
	    private Date modified_datetime;
	    private int lead_id;
	    private String status;
	    private Date purchase_date;
	    private String ageing;
	    private String make;
	    private String stage;

}

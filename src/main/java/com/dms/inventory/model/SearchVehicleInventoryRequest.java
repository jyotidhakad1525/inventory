/**
 * 
 */
package com.dms.inventory.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @author bhara
 *
 */
@Getter
@Setter
public class SearchVehicleInventoryRequest {
	
	private String orgId;
	private String limit;
	private String offset;
	private Date purchaseFromDate;
	private Date purchaseToDate;
	private String vinNum;
	private String engineNum;
	private String purchaseInvoiceNum;
	private String purchasedFrom;
	private String location;
	private String dealerCode;
	private String vehicleFinancedBy;
	private String status;


}

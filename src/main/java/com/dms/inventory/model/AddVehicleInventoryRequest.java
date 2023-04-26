/**
 * 
 */
package com.dms.inventory.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * @author bhara
 *
 */
@Getter
@Setter
public class AddVehicleInventoryRequest {

	public BigInteger id;
	public int org_id;
	// Dealer Details
	// OEM or Co dealer
	private String purchasedFrom;
	private String location;
	private String dealerCode;
	// dealerName is orgnization name
	private String dealerName;

	// TransactionDetails
	
	private Date purchaseDate;
	private String purchaseInvoiceNum;
	private String vehicleFinancedBy;
	
	private Date vehicleReceivedDate;

	// Vehecle Details
	private String modelId;
	// variant id will give information about fuelType, transmission type, enginecc
	private String variantId;
	// color
	private String color;
	private String fuel;
	private String transmissionType;
	private String engineCC;
	private String vinNum;
	private String engineNum;
	private String hsnSac;
	private String keyNum;

	// price and tax details

	private String stateType;
	private BigDecimal purchasePrice;
	private BigDecimal basicPrice;
	private BigDecimal cessAmount;
	private float cessPercentage;
	private float cgst;
	private float sgst;
	private float igst;
	private float utgst;

	private String status;

	@Override
	public String toString() {
		return "AddVehicleInventoryRequest [id=" + id + ", purchasedFrom=" + purchasedFrom + ", location=" + location
				+ ", dealerCode=" + dealerCode + ", dealerName=" + dealerName + ", purchaseDate=" + purchaseDate
				+ ", purchaseInvoiceNum=" + purchaseInvoiceNum + ", vehicleFinancedBy=" + vehicleFinancedBy
				+ ", vehicleReceivedDate=" + vehicleReceivedDate + ", modelId=" + modelId + ", variantId=" + variantId
				+ ", color=" + color + ", fuel=" + fuel + ", transmissionType=" + transmissionType + ", engineCC="
				+ engineCC + ", vinNum=" + vinNum + ", engineNum=" + engineNum + ", hsnSac=" + hsnSac + ", keyNum="
				+ keyNum + ", stateType=" + stateType + ", purchasePrice=" + purchasePrice + ", basicPrice="
				+ basicPrice + ", cessAmount=" + cessAmount + ", cessPercentage=" + cessPercentage + ", cgst=" + cgst
				+ ", sgst=" + sgst + ", igst=" + igst + ", utgst=" + utgst + ", status=" + status + "]";
	}

	
	

}

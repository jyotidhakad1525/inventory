/**
 * 
 */
package com.dms.inventory.entities;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

/**
 * @author bhara
 *
 */

@Setter
@Getter
@Entity
@Table(name = "vehicle_stock_inventory", schema = "inventory")
public class VehicleStockInventoryEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger id;
	// Dealer Details
	// OEM or Co dealer
	@Column(name = "purchased_from")
	private String purchasedFrom;
	@Column(name = "location")
	private String location;
	@Column(name = "dealer_code")
	private String dealerCode;
	// dealerName is orgnization name
	@Column(name = "dealer_name")
	private String dealerName;
	@Column(name = "org_id")
	private int org_id;
	// TransactionDetails
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "purchase_date")
	private Date purchaseDate;
	@Column(name = "purchase_invoice_num")
	private String purchaseInvoiceNum;
	@Column(name = "vehicle_financed_by")
	private String vehicleFinancedBy;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "vehicle_received_date")
	private Date vehicleReceivedDate;

	// Vehecle Details
	@Column(name = "model_id")
	private String modelId;
	@Column(name = "color")
	private String color;
	// variant id will give information about fuelType, transmission type, enginecc
	@Column(name = "variant_id")
	private String variantId;
	@Column(name = "fuel")
	private String fuel;
	@Column(name = "transmission_type")
	private String transmissionType;
	@Column(name = "engineCC")
	private String engineCC;
	@Column(name = "vin_num")
	private String vinNum;
	@Column(name = "engine_num")
	private String engineNum;
	@Column(name = "hsn_sac")
	private String hsnSac;
	@Column(name = "key_num")
	private String keyNum;

	// price and tax details
	@Column(name = "state_type")
	private String stateType;
	@Column(name = "purchase_price")
	private BigDecimal purchasePrice;
	@Column(name = "basic_price")
	private BigDecimal basicPrice;
	@Column(name = "cess_amount")
	private BigDecimal cessAmount;
	@Column(name = "cess_percentage")
	private float cessPercentage;
	@Column(name = "cgst")
	private float cgst;
	@Column(name = "sgst")
	private float sgst;
	@Column(name = "igst")
	private float igst;
	@Column(name = "utgst")
	private float utgst;
	@Column(name = "status")
	private String status;
	@Column(name = "created_date")
	private Date created_date;
	@Override
	public String toString() {
		return "VehicleStockInventoryEntity [id=" + id + ", purchasedFrom=" + purchasedFrom + ", location=" + location
				+ ", dealerCode=" + dealerCode + ", dealerName=" + dealerName + ", org_id=" + org_id + ", purchaseDate="
				+ purchaseDate + ", purchaseInvoiceNum=" + purchaseInvoiceNum + ", vehicleFinancedBy="
				+ vehicleFinancedBy + ", vehicleReceivedDate=" + vehicleReceivedDate + ", modelId=" + modelId
				+ ", color=" + color + ", variantId=" + variantId + ", fuel=" + fuel + ", transmissionType="
				+ transmissionType + ", engineCC=" + engineCC + ", vinNum=" + vinNum + ", engineNum=" + engineNum
				+ ", hsnSac=" + hsnSac + ", keyNum=" + keyNum + ", stateType=" + stateType + ", purchasePrice="
				+ purchasePrice + ", basicPrice=" + basicPrice + ", cessAmount=" + cessAmount + ", cessPercentage="
				+ cessPercentage + ", cgst=" + cgst + ", sgst=" + sgst + ", igst=" + igst + ", utgst=" + utgst
				+ ", status=" + status + ", created_date=" + created_date + "]";
	}
	
		
	
	

}

/**
 * 
 */
package com.dms.inventory.model;

import java.math.BigInteger;

import lombok.Getter;
import lombok.Setter;

/**
 * @author bhara
 *
 */
@Getter
@Setter
public class UpdateVehicleInventoryrequest {
	

	private BigInteger id;
	
	private String toLocation;
	private String dealerCode;
	@Override
	public String toString() {
		return "UpdateVehicleInventoryrequest [id=" + id + ", fromLocation="  + ", toLocation="
				+ toLocation + ", dealerCode=" + dealerCode + "]";
	}
	
	
	
	

}

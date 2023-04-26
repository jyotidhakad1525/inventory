/**
 * 
 */
package com.dms.inventory.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author bhara
 *
 */
@Getter
@Setter
public class SearchVehicleInventoryResponse {
	private List<AddVehicleInventoryRequest> stockInventoryList;
}

package com.dms.inventory.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VehicleInventoryRequest {
	
	String org_id;
	String model;
	String variant;
	String colour;
	String fuel;
	String transmission;

}

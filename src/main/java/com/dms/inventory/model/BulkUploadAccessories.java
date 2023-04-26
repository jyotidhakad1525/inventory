package com.dms.inventory.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BulkUploadAccessories {
    private Integer vehicleId;
    private Long origanistionId;
    private Integer empId;
   
}

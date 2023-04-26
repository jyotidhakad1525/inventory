package com.dms.inventory.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdditionalVehicleInfo {

    private Integer vehicleId;
    private Integer organizationId;
    private String type;
    private String brand;
    private String model;
    private Integer varientId;
    private String varientName;
    private String fuelType;
    private String transmission_type;
    private String mileage;
    private Integer colorId;
    private String color;
    private String bhp;

}

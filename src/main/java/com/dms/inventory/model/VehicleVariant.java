package com.dms.inventory.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class VehicleVariant {

    private Integer id;
    private String name;
    private Integer vehicleId;
    private String fuelType;
    private String transmission_type;
    private String mileage;
    private String status;
    private String bhp;
    private Set<VehicleImage> vehicleImages;

}

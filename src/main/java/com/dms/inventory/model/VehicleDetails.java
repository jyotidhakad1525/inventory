package com.dms.inventory.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class VehicleDetails {

    private Integer vehicleId;
    private Integer organizationId;
    private String type;
    private String brand;
    private String model;
    private String status;
    private Integer booking_amount;
    private Set<VehicleVariant> varients;

}

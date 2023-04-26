package com.dms.inventory.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VehicleImage {


    private Integer vehicleImageId;
    private Integer vehicleId;
    private Integer varient_id;
    private String color_top;
    private String color_top_code;
    private String color;
    private String color_body_code;
    private Integer priority;
    private String url;
    private Boolean is_dual_color;


}

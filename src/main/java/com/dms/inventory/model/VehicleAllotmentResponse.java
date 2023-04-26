package com.dms.inventory.model;

import com.dms.inventory.common.BaseResponse;
import com.dms.inventory.entities.DemoTestdriveVehicleAllotment;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class VehicleAllotmentResponse extends BaseResponse {
    @JsonInclude(Include.NON_NULL)
    List<DemoTestdriveVehicleAllotment> vehicleAllotments;

    @JsonInclude(Include.NON_NULL)
    DemoTestdriveVehicleAllotment allotment;

}

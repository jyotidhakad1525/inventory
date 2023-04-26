package com.dms.inventory.model;

import com.dms.inventory.common.BaseResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DemoVehicleResponse extends BaseResponse {
    private List<DemoVehicle> vehicles;

}

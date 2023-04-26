package com.dms.inventory.mapper;

import com.dms.inventory.entities.Accessory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AccessoryMappingDto {


    private int id;
    private String kitName;
    private String accessoriesList;
    private int organisationId;
    private int vehicleId;
    private Double cost;
    private List<Accessory> accessory;

}

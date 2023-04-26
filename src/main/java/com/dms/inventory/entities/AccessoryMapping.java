package com.dms.inventory.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Setter
@Getter
@Entity
@Table(name = "kit_accessories_mapping")
public class AccessoryMapping {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "kit_name")
    private String kitName;
    @Column(name = "accessories_list")
    private String accessoriesList;
    @Column(name = "origanistion_id")
    private int organisationId;
    @Column(name = "vehicle_id")
    private int vehicleId;
    @Column(name = "cost")
    private Double cost;

}

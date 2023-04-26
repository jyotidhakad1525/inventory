package com.dms.inventory.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "vehicle_details_new")
public class VehicleModelDetails implements Serializable {
    private static final long serialVersionUID = 4895908114629386216L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vehicleId;
    @Column(name = "organization_id")
    private Integer organizationId;
    @Column(name = "model")
    private String model;
   

}

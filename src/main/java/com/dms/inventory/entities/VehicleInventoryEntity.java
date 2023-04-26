package com.dms.inventory.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "vehicles_inventory", schema = "inventory")
public class VehicleInventoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "org_id")
    private String org_id;

    @Column(name = "branch_id")
    private long branch_id;

    @Column(name = "rc_no")
    private String rc_no;

    @Column(name = "chassis_no")
    private String chassis_no;

    @Column(name = "engineno")
    private String engineno;

    @Column(name = "vin_number")
    private String vin_number;

    @Column(name = "key_no")
    private String key_no;

    @Column(name = "model")
    private String model;

    @Column(name = "variant")
    private String variant;

    @Column(name = "fuel")
    private String fuel;

    @Column(name = "transmission")
    private String transmission;

    @Column(name = "colour")
    private String colour;

    @Column(name = "alloted")
    private int alloted;

    @Column(name = "universel_id")
    private String universel_id;

    @Temporal(TemporalType.DATE)
    @Column(name = "alloted_date")
    private Date alloted_date;

    @Temporal(TemporalType.DATE)
    @Column(name = "deallocation_date")
    private Date deallocation_date;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_datetime")
    private Date created_datetime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_datetime")
    private Date modified_datetime;

    @Column(name = "lead_id")
    private int lead_id;

    
    @Column(name = "purchase_date")
    private Date purchase_date;
    
    @Column(name = "ageing")
    private String ageing;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "make")
    private String make;
    
    @Column(name = "stage")
    private String stage;
}

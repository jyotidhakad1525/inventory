package com.dms.inventory.entities;

import com.dms.inventory.common.JpaJsonConverter;
import com.dms.inventory.model.Document;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "demo_test_drive_vehicles")
@NamedQuery(name = "DemoTestDriveVehicle.findAll", query = "SELECT d FROM DemoTestDriveVehicle d")
public class DemoTestDriveVehicle implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "branch_id")
    private BigInteger branchId;

    @Column(name = "chassis_no")
    private String chassisNo;

    @Column(name = "color_id")
    private BigInteger colorId;

    @Column(name = "created_by")
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_datetime")
    private Date createdDatetime;

    @Convert(converter = JpaJsonConverter.class)
    private List<Document> documents;

    private String engineno;

    @Column(name = "insurence_company")
    private String insurenceCompany;

    @Column(name = "insurence_no")
    private String insurenceNo;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_datetime")
    private Date modifiedDatetime;

    @Column(name = "org_id")
    private BigInteger orgId;

    @Column(name = "rc_no")
    private String rcNo;

    private String remarks;

    private String status;

    private String type;

    @Column(name = "varient_id")
    private BigInteger varientId;

    @Column(name = "vehicle_id")
    private BigInteger vehicleId;

    @Column(name = "kms_reading")
    private BigInteger kmsReading;


}
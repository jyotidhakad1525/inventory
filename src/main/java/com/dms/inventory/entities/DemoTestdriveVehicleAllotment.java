package com.dms.inventory.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Setter
@Getter
@Entity
@Table(name = "demo_testdrive_vehicle_allotment")
@NamedQuery(name = "DemoTestdriveVehicleAllotment.findAll", query = "SELECT d FROM DemoTestdriveVehicleAllotment d")
public class DemoTestdriveVehicleAllotment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "cancel_status")
    private boolean cancelStatus;

    @Column(name = "created_by")
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_datetime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdDatetime;

    @Column(name = "demo_vehicle_id")
    private Integer demoVehicleId;

    @Column(name = "event_type")
    private String eventType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "in_datetime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date inDatetime;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_datetime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date modifiedDatetime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "out_datetime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date outDatetime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "planned_end_datetime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private Date plannedEndDatetime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "planned_start_datetime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private Date plannedStartDatetime;

}
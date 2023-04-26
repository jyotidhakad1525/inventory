package com.dms.inventory.entities;

import com.dms.inventory.enums.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "accessories",schema = "inventory")
public class Accessory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "vehicle_id")
    private Integer vehicleId;
    @Column(name = "origanistion_id")
    private Long origanistionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @Column(name = "item")
    private String item;
    @Column(name = "part_name")
    private String partName;
    @Column(name = "cost")
    private BigDecimal cost;
    @Column(name = "created_by")
    private Integer createdBy;
    @Column(name = "part_no")
    private String partNo;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "modified_by")
    private Integer modifiedBy;
    @Column(name = "modified_date")
    private Date modifiedDate;

}

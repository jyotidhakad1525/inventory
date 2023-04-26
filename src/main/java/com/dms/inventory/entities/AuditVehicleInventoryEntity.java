/**
 * 
 */
package com.dms.inventory.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * @author bhara
 *
 */
@Setter
@Getter
@Entity
@Table(name = "audit_vehicle_inventory", schema = "inventory")
public class AuditVehicleInventoryEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")	
	private BigInteger id;
    @Column(name = "previous_location")
	private String previousLocation;
    @Column(name = "previous_dealerCode")
	private String previousDealerCode;
    @Column(name = "updated_location")
	private String currentLocation;
    @Column(name = "updated_dealerCode")
	private String currentDealercode;
    @Column(name = "vehicle_stock_inventory_id")
	private BigInteger vehicleStockInventoryId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_datetime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	private Date creationDate;

}

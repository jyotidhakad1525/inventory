/**
 * 
 */
package com.dms.inventory.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dms.inventory.entities.AuditVehicleInventoryEntity;

/**
 * @author bhara
 *
 */
@Repository
public interface AuditVehicleInventoryRepo   extends JpaRepository<AuditVehicleInventoryEntity, BigInteger> {

}

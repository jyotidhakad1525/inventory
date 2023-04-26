package com.dms.inventory.repository;

import com.dms.inventory.entities.InventoryUsedCar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface BuyUsedCarServiceRepository extends JpaRepository<InventoryUsedCar, Integer> {

    @Query(value = "select * from inventory_used_car2 where organization_id=?1 and brand_id=?2 and model_id=?3 and  " +
            "price between ?4 and ?5", nativeQuery = true)
    Page<InventoryUsedCar> findAllByBrandAndModelAndBudget(Long organizationId, Long brandId, Long modelId,
                                                           BigDecimal price1,
                                                           BigDecimal price2, Pageable paging);

//    @Query(value = "select * from inventory_used_car where customer_id=?1 and branch_id=?2 and  organization_id =?3",
//            nativeQuery = true)
//    Optional<InventoryUsedCar> findByCustomerIdBranchIdOrgId(String customerId, long branchId, long organizationId);
    
    @Query(value = "select * from inventory_used_car2 where chassis_number=?1 and organization_id =?2",nativeQuery = true)
    Optional<InventoryUsedCar> findByChassisNumberOrganizationId(String chassisNumber,long organizationId);
    
   // void deleteByCustomerId(String customerId);

}

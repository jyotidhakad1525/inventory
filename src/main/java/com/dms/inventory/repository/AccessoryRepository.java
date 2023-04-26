package com.dms.inventory.repository;

import com.dms.inventory.entities.Accessory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessoryRepository extends PagingAndSortingRepository<Accessory, Integer> {

    @Query(value = "select * from accessories where origanistion_id=?2 and  vehicle_id=?1", nativeQuery = true)
    List<Accessory> findAllByVehicleIdAndOriganistionId(Integer vehicleId, Long orgId);

    @Query(value = "select * from accessories where origanistion_id=?2 and  category=?1", nativeQuery = true)
    List<Accessory> findAllByCategoryAndOriganistionId(String category, Long orgId);

    List<Accessory> findAllByIdAndOriganistionId(Integer id, Long orgId);

    List<Accessory> findAllByOriganistionId(Long id);

    @Query(value = "SELECT u FROM Accessory u WHERE u.id IN :id ")
    List<Accessory> findAccessoryByIdList(@Param("id") List<Integer> id);

}

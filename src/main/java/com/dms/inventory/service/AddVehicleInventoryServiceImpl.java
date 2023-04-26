package com.dms.inventory.service;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dms.inventory.common.BaseResponse;
import com.dms.inventory.common.Utils;
import com.dms.inventory.entities.AuditVehicleInventoryEntity;
import com.dms.inventory.entities.VehicleStockInventoryEntity;
import com.dms.inventory.mapper.VehicleStockInventoryMapper;
import com.dms.inventory.model.AddVehicleInventoryRequest;
import com.dms.inventory.model.UpdateVehicleInventoryrequest;
import com.dms.inventory.repository.AuditVehicleInventoryRepo;
import com.dms.inventory.repository.VehicleStockInventoryRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AddVehicleInventoryServiceImpl implements AddVehicleInventoryService {

	@Autowired
	private VehicleStockInventoryMapper mapper;

	@Autowired
	private VehicleStockInventoryRepo repo;
	
	@Autowired
	private AuditVehicleInventoryRepo auditRepo;

	@Override
	public BaseResponse addVehicleToInventory(AddVehicleInventoryRequest addVehicleInventoryRequest) throws Exception {
		log.debug("Entry : addVehicleToInventory");
		//Check if the vinNum exists in the systom or not
		BaseResponse response = Utils.SuccessResponse();
		if( null != repo.findByVinNum(addVehicleInventoryRequest.getVinNum())) {
			throw new Exception("Vin number exists");
		}
		
		VehicleStockInventoryEntity entity = mapper.mapModelToEntity(addVehicleInventoryRequest);
		entity = repo.save(entity);
		log.info("Save completed : addVehicleTadding vehicle to inventory table" + entity.getId());
		
		response.setConfirmationId(entity.getId() + "");
		log.debug("Completed : addVehicleTadding vehicle to inventory table" + entity.getId());
		return response;

	}

	@Override
	@Transactional
	public BaseResponse transferVehicleInventory(UpdateVehicleInventoryrequest updateVehicleInventoryrequest) {
		log.debug("Entry : transferVehicleInventory");
		BaseResponse successResponse;
		Optional<VehicleStockInventoryEntity> dbObject = repo.findById(updateVehicleInventoryrequest.getId());
		log.debug("Entity Exists ::"+ dbObject.isPresent());
		if (dbObject.isPresent()) {
			VehicleStockInventoryEntity  entity= dbObject.get();
			entity.setLocation(updateVehicleInventoryrequest.getToLocation());
			entity.setDealerCode(updateVehicleInventoryrequest.getDealerCode());
			repo.save(entity);
			AuditVehicleInventoryEntity auditEntity = new AuditVehicleInventoryEntity();
			auditEntity.setCreationDate(new Date());
			auditEntity.setPreviousLocation(entity.getLocation());
			auditEntity.setCurrentLocation(updateVehicleInventoryrequest.getToLocation());
			auditEntity.setPreviousDealerCode(entity.getDealerCode());
			auditEntity.setCurrentDealercode(updateVehicleInventoryrequest.getDealerCode());
			auditEntity.setVehicleStockInventoryId(updateVehicleInventoryrequest.getId());
			auditEntity = auditRepo.save(auditEntity);
			successResponse = Utils.SuccessResponse();
			successResponse.setConfirmationId("Success Audit entry"+auditEntity.getId());			
			log.debug("Exit : transferVehicleInventory");
			return successResponse;
		}
		
		return null;
		
	}

}

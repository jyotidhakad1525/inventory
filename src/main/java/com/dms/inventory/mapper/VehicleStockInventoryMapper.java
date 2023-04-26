package com.dms.inventory.mapper;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.dms.inventory.entities.VehicleStockInventoryEntity;
import com.dms.inventory.model.AddVehicleInventoryRequest;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class VehicleStockInventoryMapper {

	public VehicleStockInventoryEntity mapModelToEntity(AddVehicleInventoryRequest model) {
		log.debug("Entry:: mapModelToEntity");
		VehicleStockInventoryEntity entity = new VehicleStockInventoryEntity();
		entity.setOrg_id(model.getOrg_id());
		entity.setBasicPrice(model.getBasicPrice());
		entity.setCessAmount(model.getCessAmount());
		entity.setCessPercentage(model.getCessPercentage());
		entity.setCgst(model.getCgst());
		entity.setDealerCode(model.getDealerCode());
		entity.setDealerName(model.getDealerName());
		entity.setEngineNum(model.getEngineNum());
		entity.setHsnSac(model.getHsnSac());
		entity.setIgst(model.getIgst());
		entity.setKeyNum(model.getKeyNum());
		entity.setLocation(model.getLocation());
		entity.setModelId(model.getModelId());
		entity.setPurchaseDate(model.getPurchaseDate());
		entity.setPurchasedFrom(model.getPurchasedFrom());
		entity.setPurchaseInvoiceNum(model.getPurchaseInvoiceNum());
		entity.setPurchasePrice(model.getPurchasePrice());
		entity.setSgst(model.getSgst());
		entity.setStateType(model.getStateType());
		entity.setStatus("INSTOCK");
		entity.setColor(model.getColor());
		entity.setFuel(model.getFuel());
		entity.setTransmissionType(model.getTransmissionType());
		entity.setEngineCC(model.getEngineCC());
		entity.setUtgst(model.getUtgst());
		entity.setVariantId(model.getVariantId());
		entity.setVehicleFinancedBy(model.getVehicleFinancedBy());
		entity.setVehicleReceivedDate(model.getVehicleReceivedDate());
		entity.setVinNum(model.getVinNum());
		entity.setCreated_date(new Date());
		log.debug("Exit mapModelToEntity " + entity.toString());
		return entity;
	}

	
	public AddVehicleInventoryRequest mapEntityToModel( VehicleStockInventoryEntity entity ) {
		log.debug("Entry:: mapModelToEntity");
		AddVehicleInventoryRequest model = new AddVehicleInventoryRequest();
		model.setOrg_id(entity.getOrg_id());
		model.setBasicPrice(entity.getBasicPrice());
		model.setCessAmount(entity.getCessAmount());
		model.setCessPercentage(entity.getCessPercentage());
		model.setCgst(entity.getCgst());
		model.setDealerCode(entity.getDealerCode());
		model.setDealerName(entity.getDealerName());
		model.setEngineNum(entity.getEngineNum());
		model.setHsnSac(entity.getHsnSac());
		model.setIgst(entity.getIgst());
		model.setKeyNum(entity.getKeyNum());
		model.setLocation(entity.getLocation());
		model.setModelId(entity.getModelId());
		model.setPurchaseDate(entity.getPurchaseDate());
		model.setPurchasedFrom(entity.getPurchasedFrom());
		model.setPurchaseInvoiceNum(entity.getPurchaseInvoiceNum());
		model.setPurchasePrice(entity.getPurchasePrice());
		model.setSgst(entity.getSgst());
		model.setStateType(entity.getStateType());
		model.setStatus(entity.getStatus());
		model.setColor(entity.getColor());
		model.setFuel(entity.getFuel());
		model.setTransmissionType(entity.getTransmissionType());
		model.setEngineCC(entity.getEngineCC());
		model.setUtgst(entity.getUtgst());
		model.setVariantId(entity.getVariantId());
		model.setVehicleFinancedBy(entity.getVehicleFinancedBy());
		model.setVehicleReceivedDate(entity.getVehicleReceivedDate());
		model.setVinNum(entity.getVinNum());
		model.setId(entity.getId());
		log.debug("Exit mapModelToEntity " + model.toString());
		return model;
	}
}

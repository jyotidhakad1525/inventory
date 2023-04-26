package com.dms.inventory.mapper;

import com.dms.inventory.entities.InventoryUsedCar;
import com.dms.inventory.model.InventoryUsedCarDto;
import org.springframework.stereotype.Component;

@Component
public class InventoryUsedCarMapper {

    public InventoryUsedCarDto mapEntityToModel(InventoryUsedCar model) {
        InventoryUsedCarDto entity = new InventoryUsedCarDto();
        entity.setId(model.getId());
        entity.setOrganizationId(model.getOrganizationId());
        entity.setLocation(model.getLocation());
        entity.setDealerCode(model.getDealerCode());
        entity.setMake(model.getMake());
        entity.setModel(model.getModel());
        entity.setVariant(model.getVariant());
        entity.setColor(model.getColor());
        entity.setFuel(model.getFuel());
        entity.setTransmission(model.getTransmission());
        entity.setMakingMonth(model.getMakingMonth());
        entity.setMakingYear(model.getMakingYear());
        entity.setRcNumber(model.getRcNumber());
        entity.setRegistrationDate(model.getRegistrationDate());
        entity.setRegistrationValidUpto(model.getRegistrationValidUpto());
        entity.setVinNumber(model.getVinNumber());
        entity.setEngineNumber(model.getEngineNumber());
        entity.setChassisNumber(model.getChassisNumber());
        entity.setNoOfOwners(model.getNoOfOwners());
        entity.setVehiclePurchaseDate(model.getVehiclePurchaseDate());
		entity.setVehiclePurchasePrice(model.getVehiclePurchasePrice());
		entity.setInsuranceType(model.getInsuranceType());
		entity.setInsuranceValidUpto(model.getInsuranceValidUpto());
		entity.setDrivenKms(model.getDrivenKms());
		entity.setVehicleSellingPrice(model.getVehicleSellingPrice());
		entity.setEvalutorName(model.getEvalutorName());
		entity.setExtraField(model.getImages());
		entity.setCreatedDatetime(model.getCreatedDatetime());
		entity.setCreatedBy(model.getCreatedBy());
		entity.setModifiedBy(model.getModifiedBy());
		entity.setModifiedDate(model.getModifiedDate());
		return entity;
    }

    public InventoryUsedCar mapModelToEntity(InventoryUsedCarDto model) {
        InventoryUsedCar entity = new InventoryUsedCar();
        entity.setId(model.getId());
        entity.setOrganizationId(model.getOrganizationId());
        entity.setLocation(model.getLocation());
        entity.setDealerCode(model.getDealerCode());
        entity.setMake(model.getMake());
        entity.setModel(model.getModel());
        entity.setVariant(model.getVariant());
        entity.setColor(model.getColor());
        entity.setFuel(model.getFuel());
        entity.setTransmission(model.getTransmission());
        entity.setMakingMonth(model.getMakingMonth());
        entity.setMakingYear(model.getMakingYear());
        entity.setRcNumber(model.getRcNumber());
        entity.setRegistrationDate(model.getRegistrationDate());
        entity.setRegistrationValidUpto(model.getRegistrationValidUpto());
        entity.setVinNumber(model.getVinNumber());
        entity.setEngineNumber(model.getEngineNumber());
        entity.setChassisNumber(model.getChassisNumber());
        entity.setNoOfOwners(model.getNoOfOwners());
        entity.setVehiclePurchaseDate(model.getVehiclePurchaseDate());
		entity.setVehiclePurchasePrice(model.getVehiclePurchasePrice());
		entity.setInsuranceType(model.getInsuranceType());
		entity.setInsuranceValidUpto(model.getInsuranceValidUpto());
		entity.setDrivenKms(model.getDrivenKms());
		entity.setVehicleSellingPrice(model.getVehicleSellingPrice());
		entity.setEvalutorName(model.getEvalutorName());
		//entity.setImages(model.getImagefiles());
		entity.setCreatedDatetime(model.getCreatedDatetime());
		entity.setCreatedBy(model.getCreatedBy());
		entity.setModifiedBy(model.getModifiedBy());
		entity.setModifiedDate(model.getModifiedDate());
        return entity;
    }

}

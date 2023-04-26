/**
 * created by ${user}
 */
package com.dms.inventory.mapper;

import com.dms.inventory.entities.VehicleInventoryEntity;
import com.dms.inventory.model.VehicleInventoryModel;
import org.springframework.stereotype.Component;

@Component
public class VehicleInventoryMapper {

    public VehicleInventoryModel mapEntityToModel(VehicleInventoryEntity entity) {
        VehicleInventoryModel model = new VehicleInventoryModel();
        model.setId(entity.getId());
        model.setOrg_id(entity.getOrg_id());
        model.setBranch_id(entity.getBranch_id());
        model.setRc_no(entity.getRc_no());
        model.setChassis_no(entity.getChassis_no());
        model.setEngineno(entity.getEngineno());
        model.setVin_number(entity.getVin_number());
        model.setKey_no(entity.getKey_no());
        model.setModel(entity.getModel());
        model.setVariant(entity.getVariant());
        model.setFuel(entity.getFuel());
        model.setTransmission(entity.getTransmission());
        model.setColour(entity.getColour());
        model.setAlloted(entity.getAlloted());
        model.setUniversel_id(entity.getUniversel_id());
        model.setAlloted_date(entity.getAlloted_date());
        model.setPurchase_date(entity.getPurchase_date());
        model.setDeallocation_date(entity.getDeallocation_date());
        model.setCreated_datetime(entity.getCreated_datetime());
        model.setModified_datetime(entity.getModified_datetime());
        model.setLead_id(entity.getLead_id());
        return model;
    }

    public VehicleInventoryEntity mapModelToEntity(VehicleInventoryModel model) {
        VehicleInventoryEntity entity = new VehicleInventoryEntity();
        entity.setId(model.getId());
        entity.setOrg_id(model.getOrg_id());
        entity.setBranch_id(model.getBranch_id());
        entity.setRc_no(model.getRc_no());
        entity.setChassis_no(model.getChassis_no());
        entity.setEngineno(model.getEngineno());
        entity.setVin_number(model.getVin_number());
        entity.setKey_no(model.getKey_no());
        entity.setModel(model.getModel());
        entity.setVariant(model.getVariant());
        entity.setFuel(model.getFuel());
        entity.setTransmission(model.getTransmission());
        entity.setColour(model.getColour());
        entity.setAlloted(model.getAlloted());
        entity.setUniversel_id(model.getUniversel_id());
        entity.setAlloted_date(model.getAlloted_date());
        entity.setDeallocation_date(model.getDeallocation_date());
        entity.setCreated_datetime(model.getCreated_datetime());
        entity.setModified_datetime(model.getModified_datetime());
        entity.setLead_id(model.getLead_id());
        return entity;
    }
}

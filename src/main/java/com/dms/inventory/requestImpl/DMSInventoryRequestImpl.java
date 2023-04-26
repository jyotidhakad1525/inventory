package com.dms.inventory.requestImpl;

import com.dms.inventory.entities.VehicleInventoryEntity;
import com.dms.inventory.mapper.VehicleInventoryMapper;
import com.dms.inventory.model.VehicleInventoryModel;
import com.dms.inventory.repository.VehicleInventoryRepository;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DMSInventoryRequestImpl {
    private final static Logger logger = Logger.getLogger(DMSInventoryRequestImpl.class.getName());

    private final
    VehicleInventoryRepository inventoryRepo;

    private final
    VehicleInventoryMapper inventoryMapper;

    public DMSInventoryRequestImpl(VehicleInventoryRepository inventoryRepo, VehicleInventoryMapper inventoryMapper) {
        this.inventoryRepo = inventoryRepo;
        this.inventoryMapper = inventoryMapper;
    }

    public VehicleInventoryModel getVehiclebyId(int id) {
        logger.log(Level.INFO, "Get Vehicle details by id " + id);
        VehicleInventoryModel vehicleDetails = null;
        vehicleDetails = inventoryMapper.mapEntityToModel(inventoryRepo.findById(id).get());
        return vehicleDetails;
    }

    public List<VehicleInventoryModel> getAllVehicleDetails() {
        logger.log(Level.INFO, "Get all Vehicle details");
        List<VehicleInventoryModel> vehicleList = new ArrayList<>();
        List<VehicleInventoryEntity> entities = inventoryRepo.findAll();
        entities.stream().forEach(entity -> {
            VehicleInventoryModel model = new VehicleInventoryModel();
            model = inventoryMapper.mapEntityToModel(entity);
            vehicleList.add(model);
        });
        return vehicleList;
    }

    public VehicleInventoryModel createVehicleEntry(VehicleInventoryModel vehicleModel) {
        logger.log(Level.INFO, "Create Vehicle details for " + vehicleModel.toString());
        VehicleInventoryEntity entity = inventoryMapper.mapModelToEntity(vehicleModel);
        entity.setCreated_datetime(new Date());
        entity.setModified_datetime(new Date());
        VehicleInventoryModel savedEntity = inventoryMapper.mapEntityToModel(inventoryRepo.save(entity));
        return savedEntity;
    }

    public VehicleInventoryModel updateVehicleEntry(VehicleInventoryModel vehicleModel) {
        logger.log(Level.INFO, "Update Vehicle details for " + vehicleModel.toString());
        VehicleInventoryEntity entity = inventoryRepo.findById(vehicleModel.getId()).get();
        entity.setOrg_id(vehicleModel.getOrg_id());
        entity.setBranch_id(vehicleModel.getBranch_id());
        entity.setRc_no(vehicleModel.getRc_no());
        entity.setChassis_no(vehicleModel.getChassis_no());
        entity.setEngineno(vehicleModel.getEngineno());
        entity.setVin_number(vehicleModel.getVin_number());
        entity.setKey_no(vehicleModel.getKey_no());
        entity.setModel(vehicleModel.getModel());
        entity.setVariant(vehicleModel.getVariant());
        entity.setFuel(vehicleModel.getFuel());
        entity.setTransmission(vehicleModel.getTransmission());
        entity.setColour(vehicleModel.getColour());
        entity.setAlloted(vehicleModel.getAlloted());
        entity.setUniversel_id(vehicleModel.getUniversel_id());
        entity.setAlloted_date(vehicleModel.getAlloted_date());
        entity.setDeallocation_date(vehicleModel.getDeallocation_date());
        entity.setCreated_datetime(vehicleModel.getCreated_datetime());
        entity.setModified_datetime(new Date());
        entity.setLead_id(vehicleModel.getLead_id());
        VehicleInventoryModel savedEntity = inventoryMapper.mapEntityToModel(inventoryRepo.save(entity));
        return savedEntity;
    }

    public void deleteVehicleEntry(int id) {
        logger.log(Level.INFO, "Delete Vehicle details by id " + id);
        inventoryRepo.deleteById(id);
    }

    public List<VehicleInventoryModel> searchInventory(long org_id, long branch_id, String model, String allotedDate,
                                                       String deAllotedDate) {
        logger.log(Level.INFO, "Search Inventory for Vehicle Details");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        Date alloted = null;
        Date deAllocationDate = null;
        try {
            alloted = formatter.parse(allotedDate);
            deAllocationDate = formatter.parse(deAllotedDate);
            System.out.println(alloted + "\t" + deAllocationDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<VehicleInventoryEntity> searchList = inventoryRepo.searchInventoryBy(org_id, branch_id, model, allotedDate,
                deAllotedDate);
        List<VehicleInventoryModel> vehicleList = new ArrayList<>();
        searchList.stream().forEach(entity -> {
            VehicleInventoryModel invModel = new VehicleInventoryModel();
            invModel = inventoryMapper.mapEntityToModel(entity);
            vehicleList.add(invModel);
        });
        logger.log(Level.INFO, "Search Inventory for Vehicle Details count " + vehicleList.size());
        return vehicleList;
    }

    public VehicleInventoryModel getVehicleinventory(String model, String varient, String fuel, String color,
                                                     String transmission_type, int alloted) {
        List<VehicleInventoryEntity> inventorylist = inventoryRepo.getInventoryBy(model, varient, fuel, color,
                transmission_type, alloted);
        VehicleInventoryEntity inventoryEntity = inventorylist.get(0);
        VehicleInventoryModel invModel = null;
        if (Objects.nonNull(inventoryEntity)) {
            logger.log(Level.INFO, "Alloted: " + inventoryEntity.getAlloted());
            invModel = inventoryMapper.mapEntityToModel(inventoryEntity);
        } else {
            logger.log(Level.INFO, "not found");
        }
        return invModel;
    }
    
	public VehicleInventoryModel getVehicleinventoryByVin(String model, String varient, String vin, String engineno,
			String chassisno, int alloted) {
		List<VehicleInventoryEntity> inventorylist = inventoryRepo.getInventoryByVinNo(model, varient, vin, engineno,
				chassisno, alloted);
		VehicleInventoryEntity inventoryEntity = inventorylist.get(0);
		VehicleInventoryModel invModel = null;
		if (Objects.nonNull(inventoryEntity)) {
			logger.log(Level.INFO, "Alloted: " + inventoryEntity.getAlloted());
			invModel = inventoryMapper.mapEntityToModel(inventoryEntity);
		} else {
			logger.log(Level.INFO, "not found");
		}
		return invModel;
	}
    
    
	public VehicleInventoryModel getVehicleinventoryByAgeing(String model, String varient, String fuel, String color,
			String transmission_type, int alloted) {
		List<VehicleInventoryEntity> inventorylist = inventoryRepo.getInventoryByAgeing(model, varient, fuel, color,
				transmission_type, alloted);
		VehicleInventoryEntity inventoryEntity = inventorylist.get(0);
		VehicleInventoryModel invModel = null;
		if (Objects.nonNull(inventoryEntity)) {
			logger.log(Level.INFO, "Alloted: " + inventoryEntity.getAlloted());
			invModel = inventoryMapper.mapEntityToModel(inventoryEntity);
		} else {
			logger.log(Level.INFO, "not found");
		}
		return invModel;
	}

}

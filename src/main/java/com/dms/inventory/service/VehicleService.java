package com.dms.inventory.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dms.inventory.entities.VehicleInventoryEntity;
import com.dms.inventory.model.VehicleInventoryRequest;
import com.dms.inventory.model.VehicleInventoryResponse;

import com.dms.inventory.repository.VehicleInventoryRepository;

@Service
public class VehicleService {

	@Autowired(required = true)
	VehicleInventoryRepository vehiclerepo;

	public List<VehicleInventoryEntity> getAllVehicles(String org_id) {
		return vehiclerepo.findAllById(org_id);

	}

	public List<VehicleInventoryEntity> getAllVariants(String org_id, String model) {
		return vehiclerepo.findAllvariant(org_id, model);

	}

	public List<VehicleInventoryEntity> getAllVehicleColour(String org_id ,String variant) {
		return vehiclerepo.findAllVehicleColour(org_id,variant);

	}

	public List<VehicleInventoryEntity> getAllVehicleFuel(String org_id, String variant) {
		return vehiclerepo.findAllVehicleFuel(org_id, variant);

	}

	public List<VehicleInventoryResponse> getAllVehicleInvetoryDetails(VehicleInventoryRequest request) {

		List<VehicleInventoryResponse> list = new ArrayList<>();

		List<VehicleInventoryEntity> dbList = new ArrayList<>();

		dbList = vehiclerepo.findAllVehicleInventoryDetails(request.getOrg_id(),request.getModel(), request.getVariant(),
				request.getColour(), request.getFuel());

		for (VehicleInventoryEntity vehicle : dbList) {
			VehicleInventoryResponse vehres = new VehicleInventoryResponse();
			vehres.setVin_number(vehicle.getVin_number());
			vehres.setChassis_no(vehicle.getChassis_no());
			vehres.setEngineno(vehicle.getEngineno());
			vehres.setKey_no(vehicle.getKey_no());
			vehres.setRc_no(vehicle.getRc_no());

			list.add(vehres);
		}
		return list;
	}

}

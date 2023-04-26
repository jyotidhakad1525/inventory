package com.dms.inventory.interfaces;

import java.math.BigDecimal;
import java.text.ParseException;

import org.springframework.data.domain.Page;

import com.dms.inventory.entities.VehicleSoldInfo;
import com.dms.inventory.model.InventoryUsedCarDto;
import com.dms.inventory.model.SearchResponseInventoryUsedCar;
import com.dms.inventory.model.VehicleSoldInforDto;

public interface BuyusedCarNService {
    
    VehicleSoldInfo createVehcileSoldInfo(VehicleSoldInforDto vehicleSoldInforDto);

    boolean deleteDocument(Integer id);

    InventoryUsedCarDto createInventory(InventoryUsedCarDto inventory) throws ParseException;

    InventoryUsedCarDto getByCarId(long id);

	InventoryUsedCarDto updateInventory(InventoryUsedCarDto inventory);

	Page<SearchResponseInventoryUsedCar> getByDealerCode(String dealerCode, String make, String model, BigDecimal price1,
			BigDecimal price2, Integer offset, Integer limit);

}

package com.dms.inventory.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.dms.inventory.entities.InventoryUsedCar;
import com.dms.inventory.entities.VehicleSoldInfo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchResponseInventoryUsedCar {

	private String dealerCode;
	private String rcNumber;
	private String model;
	private String variant;
	private String color;
	private String fuel;
	private String makingYear;
	private Date purchaseDate;
	private BigDecimal purchasePrice;
	private int aging;
	private String mfg;
	private Date sellingDate;
	private String customerName;
	private String sellingPrice;
	private String consultantName;
	public SearchResponseInventoryUsedCar(InventoryUsedCar iuc,Optional<VehicleSoldInfo> vsi) {
		super();
		this.dealerCode = iuc.getDealerCode();
		this.rcNumber = iuc.getRcNumber();
		this.model = iuc.getModel();
		this.variant = iuc.getVariant();
		this.color = iuc.getColor();
		this.fuel = iuc.getFuel();
		this.makingYear = iuc.getMakingYear();
		this.purchaseDate = iuc.getVehiclePurchaseDate();
		this.purchasePrice = iuc.getVehiclePurchasePrice();
		this.mfg = iuc.getMakingMonth()+"/"+iuc.getMakingYear();
		
		if(iuc.getVehiclePurchaseDate()!=null) {
			long diffInMillies = Math.abs(new Date(System.currentTimeMillis()).getTime() - iuc.getVehiclePurchaseDate().getTime());
			long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			this.aging = (int)diff;
		}
		if(vsi.isPresent()) {
			if(vsi.get()!=null) {
				this.sellingDate = vsi.get().getSellingDate();
				this.customerName = vsi.get().getCustomerName();
				this.sellingPrice = vsi.get().getVehicleSellingPrice();
				this.consultantName = vsi.get().getSalesConsultantName();
			}	
		}
		
	}
}

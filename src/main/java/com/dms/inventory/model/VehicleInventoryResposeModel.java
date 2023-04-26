package com.dms.inventory.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class VehicleInventoryResposeModel {

    private VehicleInventoryModel vehicleInventory;

    private List<VehicleInventoryModel> inventoryList;

    private String successMessage;

    private String errorMessage;

    private boolean isSuccess;

    private boolean isError;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean isError) {
        this.isError = isError;
    }
}

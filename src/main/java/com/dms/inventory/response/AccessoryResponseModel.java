package com.dms.inventory.response;

import com.dms.inventory.entities.Accessory;
import com.dms.inventory.mapper.AccessoryMappingDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AccessoryResponseModel {

    private Accessory accessory;
    private List<Accessory> accessorylist;
    private List<AccessoryMappingDto> accessoryMappingDto;
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

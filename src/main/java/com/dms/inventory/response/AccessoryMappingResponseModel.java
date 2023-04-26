package com.dms.inventory.response;

import com.dms.inventory.entities.AccessoryMapping;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AccessoryMappingResponseModel {

    private AccessoryMapping accessoryMapping;
    private List<AccessoryMapping> accessoryMappinglist;
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

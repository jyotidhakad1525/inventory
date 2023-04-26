package com.dms.inventory.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {
    private String confirmationId;
    private String status;
    private String statusDescription;
    private String statusCode;
    private int count;
    private int totalCount;

}

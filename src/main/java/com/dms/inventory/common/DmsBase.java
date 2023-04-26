package com.dms.inventory.common;

import lombok.Data;

import java.math.BigInteger;

@Data
public class DmsBase {

    private BigInteger branchId;
    private BigInteger orgId;
    private CodeValue userInfo;
    private String userId;

}

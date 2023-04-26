package com.dms.inventory.filters;

import com.dms.inventory.enums.StatusType;
import com.dms.inventory.enums.Type;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Setter
@Getter
public class BaseFilter {

    private BigInteger orgId;
    private BigInteger branchId;
    private StatusType status;
    private Integer id;
    private Type type;
    private Integer offSet;
    private Integer limit;

    public BigInteger getBranch() {
        return branchId;
    }

    public Integer getOffset() {
        return offSet;
    }

}

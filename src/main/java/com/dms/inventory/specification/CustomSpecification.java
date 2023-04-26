package com.dms.inventory.specification;

import com.dms.inventory.enums.SearchCriteriaType;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigInteger;
import java.util.Date;

public class CustomSpecification<T> {
    public static <T> Specification<T> hasOrgId(BigInteger val) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("orgId"), val);
        };
    }

    public static <T> Specification<T> hasBranch(BigInteger val) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("branchId"), val);
        };
    }

    public static <T> Specification<T> isStatus(String val) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("status"), val);
        };
    }

    public static <T> Specification<T> hasCustId(BigInteger val) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("customerId"), val);
        };
    }

    public static <T, V> Specification<T> attribute(String attrName, V val) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(attrName), val);
        };
    }

    public static <T, V> Specification<T> attributeLike(String attrName, V val) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get(attrName), "%" + val + "%");
        };
    }

    public static <T, V> Specification<T> attribute(String attrName, Date val, String operation) {
        return (root, query, criteriaBuilder) -> {
            if (SearchCriteriaType.LESSTHAN.name().equals(operation)) {
                return criteriaBuilder.lessThan(root.get(attrName), val);
            } else if (SearchCriteriaType.LESSTHAN_OR_EQUALTO.name().equals(operation)) {
                return criteriaBuilder.lessThanOrEqualTo(root.get(attrName), val);
            } else if (SearchCriteriaType.GREATERTHAN.name().equals(operation)) {
                return criteriaBuilder.greaterThan(root.get(attrName), val);
            } else if (SearchCriteriaType.GREATERTHAN.name().equals(operation)) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(attrName), val);
            } else {
                return criteriaBuilder.equal(root.get(attrName), val);
            }

        };
    }

    public static <T, V> Specification<T> between(String attrName, Date from, Date to) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.between(root.get(attrName), from, to);
        };
    }

}

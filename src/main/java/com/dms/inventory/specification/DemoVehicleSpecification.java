package com.dms.inventory.specification;

import com.dms.inventory.common.Utils;
import com.dms.inventory.filters.BaseFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class DemoVehicleSpecification<T> {

    public static <T> Specification<T> filter(BaseFilter val) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("orgId"), val);
        };
    }

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

    public static <T> Specification<T> hasId(Integer val) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("id"), val);
        };
    }

    public static <T> Specification<T> isStatus(String val) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("status"), val);
        };
    }

    public static <T> Specification<T> hasType(List<String> val) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (Utils.isNotEmpty(val)) {
                predicates.add(root.get("type").in(val));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}

package com.dms.inventory.common;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.List;

public class JpaJsonConverter<T> implements AttributeConverter<List<T>, String> {

    @Override
    public String convertToDatabaseColumn(List<T> attribute) {
        if (Utils.isEmpty(attribute)) return null;
        return Utils.ObjectToJson(attribute);
    }

    @Override
    public List<T> convertToEntityAttribute(String dbData) {
        List<T> documents = new ArrayList<>();
        if (Utils.isEmpty(dbData)) return documents;
        return Utils.jsonToObject(dbData, documents);
    }


}
package com.couchbase.client.java.repository.mapping;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectionBasedEntityMetadata implements EntityMetadata {

    private final List<PropertyMetadata> properties;
    private final PropertyMetadata idProperty;

    public ReflectionBasedEntityMetadata(Class<?> sourceEntity) {
        properties = new ArrayList<PropertyMetadata>();

        PropertyMetadata idProperty = null;
        for (Field field : sourceEntity.getDeclaredFields()) {
            PropertyMetadata property = new ReflectionBasedPropertyMetadata(field);
            properties.add(property);
            if (property.isId()) {
                idProperty = property;
            }
        }

        this.idProperty = idProperty;
    }

    @Override
    public List<PropertyMetadata> properties() {
        return properties;
    }

    @Override
    public boolean hasIdProperty() {
        return idProperty != null;
    }

    @Override
    public PropertyMetadata idProperty() {
        return idProperty;
    }
}

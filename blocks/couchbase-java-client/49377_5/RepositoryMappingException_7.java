package com.couchbase.client.java.repository.mapping;

import com.couchbase.client.java.repository.mapping.annotation.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectionBasedEntityProperties implements EntityProperties {

    private Field idField;
    private final List<Field> fields;

    public ReflectionBasedEntityProperties(Class<?> source) {
        fields = new ArrayList<Field>();
        for (Field field : source.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                idField = field;
                field.setAccessible(true);
            } else if (field.isAnnotationPresent(com.couchbase.client.java.repository.mapping.annotation.Field.class)) {
                fields.add(field);
                field.setAccessible(true);
            }
        }
    }

    @Override
    public List<Field> fieldProperties() {
        return fields;
    }

    @Override
    public boolean hasIdProperty() {
        return idProperty() != null;
    }

    @Override
    public Field idProperty() {
        return idField;
    }

    @Override
    public <T> T get(Field field, Object source, Class<T> target) {
        try {
            return (T) field.get(source);
        } catch (IllegalAccessException e) {
            throw new RepositoryMappingException("Could not get field from entity.", e);
        }
    }
}

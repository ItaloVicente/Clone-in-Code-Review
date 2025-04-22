package com.couchbase.client.java.repository.mapping;

import java.lang.reflect.Field;
import java.util.List;

public interface EntityProperties {

    boolean hasIdProperty();

    Field idProperty();

    List<Field> fieldProperties();

    <T> T get(Field field, Object source, Class<T> target);

}

package com.couchbase.client.java.repository.mapping;

public interface PropertyMetadata {

    boolean isId();

    boolean isField();

    String name();

    String realName();

    Object get(Object source);

    void set(Object value, Object source);

    Class<?> type();
}

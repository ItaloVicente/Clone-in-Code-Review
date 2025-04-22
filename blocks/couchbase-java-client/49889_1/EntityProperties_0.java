package com.couchbase.client.java.repository.mapping;

import java.util.List;

public interface EntityMetadata {

    List<PropertyMetadata> properties();

    boolean hasIdProperty();

    PropertyMetadata idProperty();
}

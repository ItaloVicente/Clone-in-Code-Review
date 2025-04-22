package com.couchbase.client.java.repository.mapping;

import com.couchbase.client.java.document.Document;

public interface EntityConverter<D extends Document<?>> {

    D fromEntity(Object source);

    <T> T toEntity(D source, Class<T> clazz);



}

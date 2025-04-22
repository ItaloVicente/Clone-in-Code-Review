package com.couchbase.client.java.query;

import com.couchbase.client.java.document.Document;
import com.couchbase.client.java.document.JsonDocument;
import rx.Observable;

public interface ViewRow {

    String id();

    Object key();

    Object value();

    Observable<JsonDocument> document();
    <D extends Document<?>> Observable<D> document(final Class<D> target);

}

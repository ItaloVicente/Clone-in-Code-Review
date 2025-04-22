package com.couchbase.client.java.view;

import com.couchbase.client.java.document.Document;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import rx.Observable;

public interface AsyncViewRow {

    String id();

    Object key();

    Object value();

    Observable<JsonDocument> document();

    <D extends Document<?>> Observable<D> document(final Class<D> target);

}

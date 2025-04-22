package com.couchbase.client.java.view;

import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.document.Document;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import rx.Observable;

public class DefaultAsyncSpatialViewRow implements AsyncSpatialViewRow {

    private final String id;
    private final JsonArray key;
    private final Object value;
    private final AsyncBucket bucket;
    private final JsonObject geometry;

    public DefaultAsyncSpatialViewRow(AsyncBucket bucket, String id, JsonArray key, Object value, JsonObject geometry) {
        this.bucket = bucket;
        this.id = id;
        this.key = key;
        this.value = value;
        this.geometry = geometry;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public JsonArray key() {
        return key;
    }

    @Override
    public Object value() {
        return value;
    }

    @Override
    public JsonObject geometry() {
        return geometry;
    }

    @Override
    public Observable<JsonDocument> document() {
        if (id == null) {
            return Observable.error(new UnsupportedOperationException("Document cannot be loaded, id is null."));
        }
        return bucket.get(id);
    }

    @Override
    public <D extends Document<?>> Observable<D> document(Class<D> target) {
        if (id == null) {
            return Observable.error(new UnsupportedOperationException("Document cannot be loaded, id is null."));
        }
        return bucket.get(id, target);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AsyncSpatialViewRow{");
        sb.append("id='").append(id).append('\'');
        sb.append(", key=").append(key);
        sb.append(", value=").append(value);
        sb.append(", geometry=").append(geometry);
        sb.append('}');
        return sb.toString();
    }
}

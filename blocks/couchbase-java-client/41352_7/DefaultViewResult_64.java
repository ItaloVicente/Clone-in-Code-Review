package com.couchbase.client.java.view;

import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.document.Document;
import com.couchbase.client.java.document.JsonDocument;
import rx.Observable;

public class DefaultAsyncViewRow implements AsyncViewRow {

    private final String id;
    private final Object key;
    private final Object value;
    private final AsyncBucket bucket;

    public DefaultAsyncViewRow(AsyncBucket bucket, String id, Object key, Object value) {
        this.bucket = bucket;
        this.id = id;
        this.key = key;
        this.value = value;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public Object key() {
        return key;
    }

    @Override
    public Object value() {
        return value;
    }

    @Override
    public Observable<JsonDocument> document() {
        if (id == null) {
            Observable.error(new IllegalStateException("ID is null."));
        }
        return bucket.get(id);
    }

    @Override
    public <D extends Document<?>> Observable<D> document(Class<D> target) {
        if (id == null) {
            Observable.error(new IllegalStateException("ID is null."));
        }
        return bucket.get(id, target);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DefaultViewRow{");
        sb.append("id='").append(id).append('\'');
        sb.append(", key=").append(key);
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}

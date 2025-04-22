package com.couchbase.client.java;

import com.couchbase.client.core.utils.Rx2;
import com.couchbase.client.java.document.JsonDocument;
import io.reactivex.Observable;

public class CouchbaseRx2Bucket implements Rx2Bucket {

    private final AsyncBucket asyncBucket;

    public CouchbaseRx2Bucket(AsyncBucket asyncBucket) {
        this.asyncBucket = asyncBucket;
    }

    @Override
    public Observable<JsonDocument> get(String id) {
        return Rx2.toV2Observable(asyncBucket.get(id));
    }
}

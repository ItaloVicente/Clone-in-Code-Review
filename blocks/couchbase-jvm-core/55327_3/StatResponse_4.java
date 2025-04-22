package com.couchbase.client.core.message.kv;

import com.couchbase.client.core.message.CouchbaseResponse;
import rx.subjects.ReplaySubject;

public class StatRequest extends AbstractKeyValueRequest {

    public StatRequest(String key, String bucket) {
        super(key, bucket, null, ReplaySubject.<CouchbaseResponse>create());
    }

    @Override
    public short partition() {
        return DEFAULT_PARTITION;
    }
}

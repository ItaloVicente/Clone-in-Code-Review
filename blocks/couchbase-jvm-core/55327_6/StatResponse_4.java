package com.couchbase.client.core.message.kv;

import com.couchbase.client.core.message.CouchbaseResponse;
import rx.subjects.ReplaySubject;

import java.util.concurrent.atomic.AtomicLong;

public class StatRequest extends AbstractKeyValueRequest {

    private volatile AtomicLong endpointCnt = new AtomicLong(0);

    public StatRequest(String key, String bucket) {
        super(key, bucket, null, ReplaySubject.<CouchbaseResponse>create(1024).toSerialized());
    }

    public StatRequest start() {
        endpointCnt.incrementAndGet();
        return this;
    }

    public final boolean finish() {
        return endpointCnt.decrementAndGet() == 0;
    }

    @Override
    public short partition() {
        return DEFAULT_PARTITION;
    }
}

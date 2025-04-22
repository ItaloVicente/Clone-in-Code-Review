package com.couchbase.client.core.message.kv;

import com.couchbase.client.core.message.CouchbaseResponse;
import rx.subjects.ReplaySubject;

import java.net.InetAddress;

public class StatRequest extends AbstractKeyValueRequest {
    private final InetAddress hostname;

    public StatRequest(final String key, final InetAddress hostname, final String bucket) {
        super(key, bucket, null, ReplaySubject.<CouchbaseResponse>create().toSerialized());
        this.hostname = hostname;
    }

    public InetAddress hostname() {
        return hostname;
    }

    @Override
    public short partition() {
        return DEFAULT_PARTITION;
    }
}

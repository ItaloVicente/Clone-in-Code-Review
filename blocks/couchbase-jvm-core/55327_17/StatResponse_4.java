package com.couchbase.client.core.message.kv;

import com.couchbase.client.core.message.CouchbaseResponse;
import rx.subjects.PublishSubject;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatRequest extends AbstractKeyValueRequest {
    private final InetAddress hostname;

    public StatRequest(final String key, final InetAddress hostname, final String bucket) {
        super(key, bucket, null, PublishSubject.<CouchbaseResponse>create());
        this.hostname = hostname;
    }

    public void add(final StatResponse response) {
        if (response.key() == null) {
            if (!response.status().isSuccess()) {
                observable().onNext(response);
            }
            observable().onCompleted();
        } else {
            observable().onNext(response);
        }
    }

    public InetAddress hostname() {
        return hostname;
    }

    @Override
    public short partition() {
        return DEFAULT_PARTITION;
    }
}

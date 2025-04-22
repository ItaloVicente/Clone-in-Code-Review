package com.couchbase.client.core.message.kv;

import com.couchbase.client.core.message.CouchbaseResponse;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.internal.PlatformDependent;
import rx.subjects.ReplaySubject;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class StatRequest extends AbstractKeyValueRequest {
    private static final AtomicIntegerFieldUpdater<StatRequest> endpointCntUpdater;

    static {
        AtomicIntegerFieldUpdater<StatRequest> updater =
                PlatformDependent.newAtomicIntegerFieldUpdater(StatRequest.class, "endpointCnt");
        if (updater == null) {
            updater = AtomicIntegerFieldUpdater.newUpdater(StatRequest.class, "endpointCnt");
        }
        endpointCntUpdater = updater;
    }

    private volatile int endpointCnt = 0;

    public StatRequest(String key, String bucket) {
        super(key, bucket, null, ReplaySubject.<CouchbaseResponse>create(1024).toSerialized());
    }

    public StatRequest start() {
        for (; ; ) {
            int endpointCnt = this.endpointCnt;
            if (endpointCnt < 0) {
                throw new IllegalReferenceCountException(0, 1);
            }
            if (endpointCnt == Integer.MAX_VALUE) {
                throw new IllegalReferenceCountException(Integer.MAX_VALUE, 1);
            }
            if (endpointCntUpdater.compareAndSet(this, endpointCnt, endpointCnt + 1)) {
                break;
            }
        }
        return this;
    }

    public final boolean finish() {
        for (;;) {
            int endpointCnt = this.endpointCnt;
            if (endpointCnt < 0) {
                throw new IllegalReferenceCountException(0, -1);
            }

            if (endpointCntUpdater.compareAndSet(this, endpointCnt, endpointCnt - 1)) {
                return endpointCnt == 1;
            }
        }
    }


    @Override
    public short partition() {
        return DEFAULT_PARTITION;
    }
}

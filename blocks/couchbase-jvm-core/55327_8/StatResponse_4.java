package com.couchbase.client.core.message.kv;

import com.couchbase.client.core.message.CouchbaseResponse;
import rx.Notification;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class StatRequest extends AbstractKeyValueRequest {

    private volatile AtomicLong endpointCnt = new AtomicLong(0);
    private volatile int completed = 0;
    List<StatResponse> responses = Collections.synchronizedList(new ArrayList<StatResponse>());

    public StatRequest(String key, String bucket) {
        super(key, bucket, null, ReplaySubject.<CouchbaseResponse>create().toSerialized());
    }

    public void add(StatResponse response) {
        responses.add(response);
    }

    public List<StatResponse> responses() {
        return responses;
    }


    public void onCompleted() {
        completed ++;
        if (completed == endpointCnt.get()) {
            observable().onCompleted();
            System.out.println("Observable has been completed from StatRequest");

        }
    }

    public StatRequest start() {
        long x = endpointCnt.incrementAndGet();
        System.out.println("INC endpointCnt = " + x);
        return this;
    }

    public final boolean finish() {
        long x = endpointCnt.decrementAndGet();
        System.out.println("DEC endpointCnt = " + x);
        return x == 0;
    }

    @Override
    public short partition() {
        return DEFAULT_PARTITION;
    }
}

package com.couchbase.client.core.event;

import rx.Observable;

public interface EventBus {

    Observable<CouchbaseEvent> get();

    void publish(CouchbaseEvent event);

}

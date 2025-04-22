package com.couchbase.client.core.env.resources;

import rx.Observable;

public class NoOpShutdownHook implements ShutdownHook {
    @Override
    public Observable<Boolean> shutdown() {
        return Observable.just(true);
    }

    @Override
    public boolean isShutdown() {
        return true;
    }
}

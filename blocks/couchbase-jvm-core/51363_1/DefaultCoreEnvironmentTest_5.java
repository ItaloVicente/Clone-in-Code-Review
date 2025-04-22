package com.couchbase.client.core.env.resources;

import rx.Observable;

public interface Shutdownable {

    public Observable<Boolean> shutdown();

    public boolean isShutdown();
}

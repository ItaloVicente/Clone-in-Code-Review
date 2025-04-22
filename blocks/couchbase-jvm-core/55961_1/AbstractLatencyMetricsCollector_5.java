package com.couchbase.client.core.env.resources;

import rx.Observable;
import rx.internal.schedulers.SchedulerLifecycle;

public class SchedulerLifecycleShutdownHook implements ShutdownHook {

    private SchedulerLifecycle managed;
    private volatile boolean isShutdown;

    public SchedulerLifecycleShutdownHook(SchedulerLifecycle managed) {
        this.managed = managed;
    }

    @Override
    public Observable<Boolean> shutdown() {
        managed.shutdown();
        isShutdown = true;
        return Observable.just(true);
    }

    @Override
    public boolean isShutdown() {
        return isShutdown;
    }
}

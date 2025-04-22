
package com.couchbase.client.core.event;

import rx.Observable;
import rx.Scheduler;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

public class DefaultEventBus implements EventBus {

    private final Subject<CouchbaseEvent, CouchbaseEvent> bus;
    private final Scheduler scheduler;

    public DefaultEventBus(Scheduler scheduler) {
        bus = PublishSubject.create();
        this.scheduler = scheduler;
    }

    @Override
    public Observable<CouchbaseEvent> get() {
        return bus.onBackpressureDrop().observeOn(scheduler);
    }

    @Override
    public void publish(CouchbaseEvent event) {
        if (bus.hasObservers()) {
            bus.onNext(event);
        }
    }

}

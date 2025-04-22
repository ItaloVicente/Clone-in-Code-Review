
package com.couchbase.client.core.utils;

import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.core.env.CoreScheduler;
import com.couchbase.client.core.message.CouchbaseResponse;
import org.junit.Test;
import rx.Scheduler;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.subjects.AsyncSubject;
import rx.subjects.Subject;

import static org.junit.Assert.*;

public class ObservablesTest {

    @Test
    public void shouldFailSafeOnCoreScheduler() {
        testFailSafe(new CoreScheduler(1), "cb-computations");
    }

    @Test
    public void shouldFailSafeOnCustomScheduler() {
        testFailSafe(Schedulers.computation(), "RxComputationScheduler");
    }

    @Test
    public void shouldFailSafeDirectly() {
        testFailSafe(null, "main");
    }

    private static void testFailSafe(final Scheduler scheduler, final String threadName) {
        Subject<CouchbaseResponse, CouchbaseResponse> subject = AsyncSubject.create();
        TestSubscriber<CouchbaseResponse> subscriber = TestSubscriber.create();
        subject.subscribe(subscriber);
        Exception failure = new CouchbaseException("Some Error");

        Observables.failSafe(scheduler, scheduler != null, subject, failure);

        subscriber.awaitTerminalEvent();
        subscriber.assertError(failure);
        assertTrue(subscriber.getLastSeenThread().getName().contains(threadName));
    }

}

package com.couchbase.client.core.utils;

import com.couchbase.client.core.env.CoreScheduler;
import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import com.couchbase.client.core.message.CouchbaseResponse;
import rx.Scheduler;
import rx.functions.Action0;
import rx.subjects.Subject;

public class Observable {

    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(Observable.class);

    public static void failSafe(final Scheduler scheduler, final boolean moveOut,
        final Subject<CouchbaseResponse, CouchbaseResponse> observable, final Throwable err) {
        if (moveOut) {
            if (scheduler instanceof CoreScheduler) {
                ((CoreScheduler) scheduler).scheduleDirect(new Action0() {
                    @Override
                    public void call() {
                        try {
                            observable.onError(err);
                        } catch (Exception ex) {
                            LOGGER.warn("Caught exception while onError on observable", ex);
                        }
                    }
                });
            } else {
                final Scheduler.Worker worker = scheduler.createWorker();
                worker.schedule(new Action0() {
                    @Override
                    public void call() {
                        try {
                            observable.onError(err);
                        } catch (Exception ex) {
                            LOGGER.warn("Caught exception while onError on observable", ex);
                        } finally {
                            worker.unsubscribe();
                        }
                    }
                });
            }
        } else {
            observable.onError(err);
        }
    }

}

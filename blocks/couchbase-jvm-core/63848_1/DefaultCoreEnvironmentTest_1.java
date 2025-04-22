
package com.couchbase.client.core.utils;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import rx.Observable;
import rx.Subscriber;
import rx.observables.BlockingObservable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class Blocking {

    public static <T> T blockForSingle(final Observable<? extends T> observable, final long timeout,
                                       final TimeUnit tu) {
        final CountDownLatch latch = new CountDownLatch(1);
        TrackingSubscriber<T> subscriber = new TrackingSubscriber<T>(latch);

        observable.subscribe(subscriber);

        try {
            if (!latch.await(timeout, tu)) {
                throw new RuntimeException(new TimeoutException());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while waiting for subscription to complete.", e);
        }

        if (subscriber.returnException() != null) {
            if (subscriber.returnException() instanceof RuntimeException) {
                throw (RuntimeException) subscriber.returnException();
            } else {
                throw new RuntimeException(subscriber.returnException());
            }
        }

        return subscriber.returnItem();
    }


    private final static class TrackingSubscriber<T> extends Subscriber<T> {

        private final CountDownLatch latch;
        private volatile T returnItem = null;
        private volatile Throwable returnException = null;

        public TrackingSubscriber(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void onCompleted() {
            latch.countDown();
        }

        @Override
        public void onError(final Throwable e) {
            returnException = e;
            latch.countDown();
        }

        @Override
        public void onNext(final T item) {
            returnItem = item;
        }

        public Throwable returnException() {
            return returnException;
        }

        public T returnItem() {
            return returnItem;
        }
    }

}

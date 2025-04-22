package com.couchbase.client.java.util;

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
        final AtomicReference<T> returnItem = new AtomicReference<T>();
        final AtomicReference<Throwable> returnException = new AtomicReference<Throwable>();
        final CountDownLatch latch = new CountDownLatch(1);

        observable
            .subscribe(new Subscriber<T>() {
                @Override
                public void onCompleted() {
                    latch.countDown();
                }

                @Override
                public void onError(final Throwable e) {
                    returnException.set(e);
                    latch.countDown();
                }

                @Override
                public void onNext(final T item) {
                    returnItem.set(item);
                }
            });

        try {
            if (!latch.await(timeout, tu)) {
                throw new RuntimeException(new TimeoutException());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while waiting for subscription to complete.", e);
        }

        if (returnException.get() != null) {
            if (returnException.get() instanceof RuntimeException) {
                throw (RuntimeException) returnException.get();
            } else {
                throw new RuntimeException(returnException.get());
            }
        }

        return returnItem.get();
    }

}

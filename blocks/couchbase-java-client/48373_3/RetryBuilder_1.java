package com.couchbase.client.java.util.retry;

import java.util.concurrent.TimeUnit;

import com.couchbase.client.core.lang.Tuple;
import com.couchbase.client.core.lang.Tuple2;
import com.couchbase.client.core.time.Delay;
import rx.Observable;
import rx.functions.Func2;

public class Retry {

    public static final Delay DEFAULT_DELAY = Delay.fixed(1, TimeUnit.MILLISECONDS);

    public static <T> Observable<T> wrapForRetry(Observable<T> source, int maxAttempts) {
        return wrapForRetry(source, new RetryWithDelayHandler(maxAttempts, DEFAULT_DELAY));
    }

    public static <T> Observable<T> wrapForRetry(Observable<T> source, int maxAttempts, Delay retryDelay) {
        return wrapForRetry(source, new RetryWithDelayHandler(maxAttempts, retryDelay));
    }

    public static <T> Observable<T> wrapForRetry(Observable<T> source, final RetryWithDelayHandler handler) {
        return source.retryWhen(new RetryWhenFunction(handler));
    }

    protected static Observable<Tuple2<Integer, Throwable>> errorsWithAttempts(Observable<? extends Throwable> errors,
            final int expectedAttempts) {
        return errors.zipWith(
                Observable.range(1, expectedAttempts),
                new Func2<Throwable, Integer, Tuple2<Integer, Throwable>>() {
                    @Override
                    public Tuple2<Integer, Throwable> call(Throwable error, Integer attempt) {
                        return Tuple.create(attempt, error);
                    }
                }
        );
    }

}

package com.couchbase.client.java.util.retry;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import rx.Observable;
import rx.functions.Func1;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class RetryWhenFunction implements Func1<Observable<? extends Throwable>, Observable<?>> {

    protected RetryWithDelayHandler handler;

    public RetryWhenFunction(RetryWithDelayHandler handler) {
        this.handler = handler;
    }

    public Observable<?> call(Observable<? extends Throwable> errors) {
        return Retry.errorsWithAttempts(errors, handler.maxAttempts + 1)
                    .flatMap(handler);
    }
}

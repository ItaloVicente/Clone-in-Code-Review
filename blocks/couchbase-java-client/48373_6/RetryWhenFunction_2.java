package com.couchbase.client.java.util.retry;

import java.util.Arrays;
import java.util.List;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.time.Delay;
import rx.Scheduler;
import rx.functions.Func1;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class RetryBuilder {

    private int maxAttempts;

    private Delay delay;

    private List<Class<? extends Throwable>> errorsStoppingRetry;

    private boolean inverse;

    private Scheduler scheduler;

    private RetryBuilder() {
        this.maxAttempts = 1; //one attempt
        this.delay = Retry.DEFAULT_DELAY; //constant 1ms
        this.errorsStoppingRetry = null; //retry on any error
        this.inverse = false; //list above is indeed list of errors that can stop retry (none)
        this.scheduler = null; //operate on default Scheduler for timer delay
    }

    public static RetryBuilder anyOf(Class<? extends Throwable>... types) {
        RetryBuilder retryBuilder = new RetryBuilder();
        retryBuilder.maxAttempts = 1;

        retryBuilder.errorsStoppingRetry = Arrays.asList(types);
        retryBuilder.inverse = true;

        return retryBuilder;
    }

    public static RetryBuilder allBut(Class<? extends Throwable>... types) {
        RetryBuilder retryBuilder = new RetryBuilder();
        retryBuilder.maxAttempts = 1;

        retryBuilder.errorsStoppingRetry = Arrays.asList(types);
        retryBuilder.inverse = false;

        return retryBuilder;
    }

    public static RetryBuilder any() {
        RetryBuilder retryBuilder = new RetryBuilder();
        retryBuilder.maxAttempts = 1;
        return retryBuilder;
    }

    public RetryBuilder once() {
        this.maxAttempts = 1;
        return this;
    }

    public RetryBuilder max(int maxAttempts) {
        this.maxAttempts = maxAttempts;
        return this;
    }

    public RetryBuilder delay(Delay delay) {
        this.delay = delay;
        return this;
    }

    public RetryBuilder delayOn(Scheduler scheduler) {
        this.scheduler = scheduler;
        return this;
    }

    public RetryWhenFunction build() {
        RetryWithDelayHandler handler;
        ShouldStopOnError filter;
        if (errorsStoppingRetry == null || errorsStoppingRetry.isEmpty()) {
            filter = null;
        } else {
            filter = new ShouldStopOnError(errorsStoppingRetry, inverse);
        }

        if (scheduler == null) {
            handler = new RetryWithDelayHandler(maxAttempts, delay, filter);
        } else {
            handler = new RetryWithDelayHandler(maxAttempts, delay, filter, scheduler);
        }
        return new RetryWhenFunction(handler);
    }

    protected static class ShouldStopOnError implements Func1<Throwable, Boolean> {

        private final List<Class<? extends Throwable>> errorsStoppingRetry;
        private final boolean inverse;

        public ShouldStopOnError(List<Class<? extends Throwable>> filterErrorList, boolean inverse) {
            this.errorsStoppingRetry = filterErrorList;
            this.inverse = inverse;
        }

        @Override
        public Boolean call(Throwable o) {
            for (Class<? extends Throwable> aClass : errorsStoppingRetry) {
                if (aClass.isInstance(o)) {
                    return !inverse;
                }
            }
            return inverse;
        }
    }
}

package com.couchbase.client.java.util.retry;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.couchbase.client.core.time.Delay;
import rx.functions.Func1;

public class RetryBuilder {

    private int maxAttempts;

    private Delay delay;

    private List<Class<? extends Throwable>> errorsStoppingRetry;

    private boolean inverse;

    private RetryBuilder() {
        this.maxAttempts = 1; //one attempt
        this.delay = Retry.DEFAULT_DELAY; //constant 1ms
        this.errorsStoppingRetry = Collections.emptyList(); //retry on any error
        this.inverse = false; //list above is indeed list of errors that can stop retry (none)
    }

    public static RetryBuilder retryOnce() {
        return new RetryBuilder();
    }

    public static RetryBuilder retryMax(int maxAttempts) {
        RetryBuilder builder = new RetryBuilder();
        builder.maxAttempts = maxAttempts;
        return builder;
    }

    public RetryBuilder onlyWhenNot(Class<? extends Throwable>... types) {
        this.errorsStoppingRetry = Arrays.asList(types);
        this.inverse = false;
        return this;
    }

    public RetryBuilder onlyWhen(Class<? extends Throwable>... types) {
        this.errorsStoppingRetry = Arrays.asList(types);
        this.inverse = true;
        return this;
    }

    public RetryBuilder withDelay(Delay delay) {
        this.delay = delay;
        return this;
    }

    public RetryWhenFunction build() {
        RetryWithDelayHandler handler;
        if (errorsStoppingRetry == null || errorsStoppingRetry.isEmpty()) {
            handler = new RetryWithDelayHandler(maxAttempts, delay);
        } else {
            ShouldStopOnError filter = new ShouldStopOnError(errorsStoppingRetry, inverse);
            handler = new RetryWithDelayHandler(maxAttempts, delay, filter);
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

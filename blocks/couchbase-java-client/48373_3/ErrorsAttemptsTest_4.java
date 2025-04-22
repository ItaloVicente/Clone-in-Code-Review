package com.couchbase.client.java.util.retry;

import java.util.concurrent.TimeUnit;

import com.couchbase.client.core.lang.Tuple2;
import com.couchbase.client.core.time.Delay;
import com.couchbase.client.core.time.ExponentialDelay;
import com.couchbase.client.java.error.CannotRetryException;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;

public class RetryWithDelayHandler implements Func1<Tuple2<Integer, Throwable>, Observable<?>> {

    protected final int maxAttempts;
    protected final Delay retryDelay;
    protected final Func1<Throwable, Boolean> stoppingErrorFilter;
    protected final Scheduler optionalScheduler;

    public RetryWithDelayHandler(int maxAttempts, Delay retryDelay) {
        this(maxAttempts, retryDelay, null);
    }

    public RetryWithDelayHandler(int maxAttempts, Delay retryDelay, Func1<Throwable, Boolean> stoppingErrorFilter) {
        this(maxAttempts, retryDelay, stoppingErrorFilter, null);
    }

    public RetryWithDelayHandler(int maxAttempts, Delay retryDelay, Func1<Throwable, Boolean> stoppingErrorFilter,
        Scheduler scheduler) {
        this.maxAttempts = maxAttempts;
        this.retryDelay = retryDelay;
        this.stoppingErrorFilter = stoppingErrorFilter;
        this.optionalScheduler = scheduler;
    }

    protected static String messageForMaxAttempts(long reachedAfterNRetries) {
        return "maximum number of attempts reached after " + reachedAfterNRetries + " retries";
    }

    protected static String messageForNonRetryable(long retryThatFailedNumber) {
        return "encountered non-retryable error on retry number " + retryThatFailedNumber;
    }

    @Override
    public Observable<?> call(Tuple2<Integer, Throwable> attemptError) {
        long errorNumber = attemptError.value1();
        Throwable error = attemptError.value2();

        if (errorNumber > maxAttempts) {
            return Observable.error(new CannotRetryException(messageForMaxAttempts(errorNumber - 1), error));
        } else if (stoppingErrorFilter != null && stoppingErrorFilter.call(error) == Boolean.TRUE) {
            return Observable.error(new CannotRetryException(messageForNonRetryable(errorNumber), error));
        } else {
            long delay = retryDelay.calculate(errorNumber);
            TimeUnit unit = retryDelay.unit();
            if (this.optionalScheduler != null) {
                return Observable.timer(delay, unit, optionalScheduler);
            } else {
                return Observable.timer(delay, unit);
            }
        }
    }
}

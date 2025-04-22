
package com.couchbase.client.java.util.retry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.couchbase.client.core.time.Delay;
import com.couchbase.client.java.error.CannotRetryException;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.TestScheduler;

public class RetryWhenFunctionTest {

    private TestScheduler testScheduler;
    private AtomicInteger tryCount;
    private List<Throwable> errors;
    private List<String> emissions;

    @Before
    public void init() {
        testScheduler = new TestScheduler();
        tryCount = new AtomicInteger();
        emissions = new ArrayList<String>();
        errors = new ArrayList<Throwable>();
    }

    private Observable<String> createAndSubscribe(final int numberOfArgErrors, final int numberOfStateErrors,
            RetryWhenFunction function) {
        Observable<String> obs = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                int retry = tryCount.getAndIncrement();
                if (retry == 0) {
                    System.out.print("   " + testScheduler.now() + " Initial");
                } else {
                    System.out.print(testScheduler.now() + " Retry " + retry);
                }
                if (retry < numberOfArgErrors) {
                    System.out.println(" -> IllegalArg");
                    subscriber.onError(new IllegalArgumentException());
                } else if (retry < numberOfArgErrors + numberOfStateErrors) {
                    System.out.println(" -> IllegalState");
                    subscriber.onError(new IllegalStateException());
                } else {
                    subscriber.onNext("DoneAtRetry" + retry);
                    System.out.println(" -> Emitted");
                    subscriber.onCompleted();
                }
            }
        });

        Observable<String> retryObs = obs.retryWhen(function);

        retryObs.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                emissions.add(s);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                errors.add(throwable);
            }
        });

        return retryObs;
    }

    private String msgIfError() {
        if (errors.isEmpty()) {
            return "";
        } else {
            return errors.get(0).toString();
        }
    }

    @Test
    public void testWholeRetryCycleSucceeding() {
        Observable<String> obs = createAndSubscribe(2, 1, RetryBuilder
                .anyOf(IllegalArgumentException.class, IllegalStateException.class)
                .max(3)
                .delay(Delay.linear(TimeUnit.SECONDS), testScheduler)
                .build());

        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);
        assertEquals(1, tryCount.get());
        assertEquals(0, emissions.size());
        assertEquals(msgIfError(), 0, errors.size());

        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS);
        assertEquals(2, tryCount.get());
        assertEquals(0, emissions.size());
        assertEquals(msgIfError(), 0, errors.size());

        testScheduler.advanceTimeBy(2, TimeUnit.SECONDS);
        assertEquals(3, tryCount.get());
        assertEquals(0, emissions.size());
        assertEquals(msgIfError(), 0, errors.size());

        testScheduler.advanceTimeBy(3, TimeUnit.SECONDS);
        assertEquals(4, tryCount.get());
        assertEquals(msgIfError(), 0, errors.size());
        assertEquals(1, emissions.size());
        assertEquals("DoneAtRetry3", emissions.get(0));
    }

    @Test
    public void testSuccessfulRetryCycleHasCorrectDelay() {
        Observable<String> obs = createAndSubscribe(2, 1, RetryBuilder
                .anyOf(IllegalArgumentException.class, IllegalStateException.class)
                .max(3)
                .delay(Delay.linear(TimeUnit.SECONDS), testScheduler)
                .build());

        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);
        assertEquals(1, tryCount.get());

        testScheduler.advanceTimeTo(900, TimeUnit.MILLISECONDS);
        assertEquals(1, tryCount.get());
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS);
        assertEquals(2, tryCount.get());

        testScheduler.advanceTimeTo(2900, TimeUnit.MILLISECONDS);
        assertEquals(2, tryCount.get());
        testScheduler.advanceTimeTo(3, TimeUnit.SECONDS);
        assertEquals(3, tryCount.get());

        testScheduler.advanceTimeTo(5900, TimeUnit.MILLISECONDS);
        assertEquals(3, tryCount.get());
        assertEquals(0, emissions.size());
        assertEquals(msgIfError(), 0, errors.size());
        testScheduler.advanceTimeTo(6, TimeUnit.SECONDS);
        assertEquals(4, tryCount.get());
        assertEquals(msgIfError(), 0, errors.size());
        assertEquals(1, emissions.size());
        assertEquals("DoneAtRetry3", emissions.get(0));
    }

    @Test
    public void testNoRetryIfSucceedImmediately() {
        Observable<String> obs = createAndSubscribe(0, 0, RetryBuilder
                .anyOf(IllegalArgumentException.class)
                .max(3)
                .delay(Delay.linear(TimeUnit.SECONDS), testScheduler)
                .build());

        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);
        assertEquals(1, tryCount.get());
        assertEquals(msgIfError(), 0, errors.size());
        assertEquals(1, emissions.size());
        assertEquals("DoneAtRetry0", emissions.get(0));

        testScheduler.advanceTimeBy(10, TimeUnit.HOURS);
        assertEquals(1, tryCount.get());
    }

    @Test
    public void testZeroRetrySucceedsWhenNoException() {
        createAndSubscribe(0, 0, RetryBuilder
                .any()
                .max(0)
                .delay(Delay.linear(TimeUnit.SECONDS), testScheduler)
                .build()
        );

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);
        assertEquals(1, tryCount.get());
        assertEquals(1, emissions.size());
        assertEquals("DoneAtRetry0", emissions.get(0));
    }

    @Test
    public void testZeroRetryFailsWhenException() {
        createAndSubscribe(1, 0, RetryBuilder
                .any()
                .max(0)
                .delay(Delay.linear(TimeUnit.SECONDS), testScheduler)
                .build()
        );

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);
        assertEquals(1, tryCount.get());
        assertEquals(0, emissions.size());
        assertEquals(1, errors.size());
        Throwable e = errors.get(0);
        assertEquals(e.getMessage(), RetryWithDelayHandler.messageForMaxAttempts(0), e.getMessage());
    }

    @Test
    public void testRetryOnceSucceedsWhenOneException() {
        createAndSubscribe(0, 1, RetryBuilder
                .any()
                .once()
                .delay(Delay.fixed(1, TimeUnit.SECONDS), testScheduler)
                .build()
        );

        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);
        assertEquals(1, tryCount.get());
        assertEquals(0, emissions.size());
        assertEquals(0, errors.size());

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);
        assertEquals(2, tryCount.get());
        assertEquals(1, emissions.size());
        assertEquals(msgIfError(), 0, errors.size());
        assertEquals("DoneAtRetry1", emissions.get(0));
    }

    @Test
    public void testRetryOnceFailsWhenTwoExceptions() {
        createAndSubscribe(1, 1, RetryBuilder
                .any()
                .once()
                .delay(Delay.fixed(1, TimeUnit.SECONDS), testScheduler)
                .build()
        );

        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);
        assertEquals(1, tryCount.get());
        assertEquals(0, emissions.size());
        assertEquals(0, errors.size());

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);
        assertEquals(2, tryCount.get());
        assertEquals(0, emissions.size());
        assertEquals(1, errors.size());
        Throwable e = errors.get(0);
        assertEquals(e.getMessage(), RetryWithDelayHandler.messageForMaxAttempts(1), e.getMessage());
    }

    @Test
    public void testRetryCycleFailsIfMaxAttemptsReached() {
        Observable<String> obs = createAndSubscribe(4, 0, RetryBuilder
                .anyOf(IllegalArgumentException.class, IllegalStateException.class)
                .max(3)
                .delay(Delay.linear(TimeUnit.SECONDS), testScheduler)
                .build());

        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);
        assertEquals(1, tryCount.get());
        assertEquals(0, emissions.size());
        assertEquals(msgIfError(), 0, errors.size());

        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS);
        assertEquals(2, tryCount.get());
        assertEquals(0, emissions.size());
        assertEquals(msgIfError(), 0, errors.size());

        testScheduler.advanceTimeBy(2, TimeUnit.SECONDS);
        assertEquals(3, tryCount.get());
        assertEquals(0, emissions.size());
        assertEquals(msgIfError(), 0, errors.size());

        testScheduler.advanceTimeBy(3, TimeUnit.SECONDS);
        assertEquals(4, tryCount.get());
        assertEquals(0, emissions.size());
        assertEquals(1, errors.size());

        Throwable e = errors.get(0);
        assertTrue(e instanceof CannotRetryException);
        assertTrue(e.getCause() instanceof IllegalArgumentException);
        assertEquals(e.getMessage(), RetryWithDelayHandler.messageForMaxAttempts(3), e.getMessage());
    }

    @Test
    public void testRetryCycleInterruptsIfBadException() {
        Observable<String> obs = createAndSubscribe(2, 1, RetryBuilder
                .anyOf(OutOfMemoryError.class, IllegalArgumentException.class)
                .max(3)
                .delay(Delay.linear(TimeUnit.SECONDS), testScheduler)
                .build());

        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);
        assertEquals(1, tryCount.get());
        assertEquals(0, emissions.size());
        assertEquals(msgIfError(), 0, errors.size());

        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS);
        assertEquals(2, tryCount.get());
        assertEquals(0, emissions.size());
        assertEquals(msgIfError(), 0, errors.size());

        testScheduler.advanceTimeBy(2, TimeUnit.SECONDS);
        assertEquals(3, tryCount.get());
        assertEquals(0, emissions.size());
        assertEquals(1, errors.size());

        Throwable e = errors.get(0);
        assertTrue(e instanceof IllegalStateException);
    }

    @Test
    public void testRetryOnlyWhenNot() {
        Observable<String> obs = createAndSubscribe(2, 1, RetryBuilder
                .allBut(IllegalStateException.class)
                .max(3)
                .delay(Delay.linear(TimeUnit.SECONDS), testScheduler)
                .build());

        testScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS);
        assertEquals(1, tryCount.get());
        assertEquals(0, emissions.size());
        assertEquals(msgIfError(), 0, errors.size());

        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS);
        assertEquals(2, tryCount.get());
        assertEquals(0, emissions.size());
        assertEquals(msgIfError(), 0, errors.size());

        testScheduler.advanceTimeBy(2, TimeUnit.SECONDS);
        assertEquals(3, tryCount.get());
        assertEquals(0, emissions.size());
        assertEquals(1, errors.size());

        Throwable e = errors.get(0);
        assertTrue(e instanceof IllegalStateException);
    }
}


package com.couchbase.client.java.util.retry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import com.couchbase.client.core.lang.Tuple;
import com.couchbase.client.core.time.Delay;
import com.couchbase.client.java.error.CannotRetryException;
import org.junit.BeforeClass;
import org.junit.Test;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.TestScheduler;

public class RetryWithDelayHandlerTest {

    private static final int MAX_ATTEMPTS = 10;

    private static RetryWithDelayHandler handler;

    @BeforeClass
    public static void setUp() throws Exception {
        handler = new RetryWithDelayHandler(MAX_ATTEMPTS, Delay.linear(TimeUnit.SECONDS), new Func1<Throwable, Boolean>() {
            @Override
            public Boolean call(Throwable throwable) {
                return throwable instanceof OutOfMemoryError;
            }
        });
    }

    @Test
    public void shouldThrowWhenMaxAttemptReached() {
        try {
            handler.call(Tuple.create(MAX_ATTEMPTS, (Throwable) new IllegalStateException())).toBlocking().last();
            fail("expected to throw");
        } catch (CannotRetryException e) {
            assertNotNull(e.getCause());
            assertTrue(e.getCause() instanceof IllegalStateException);
            assertTrue(e.getMessage(), e.getMessage().contains("Max attempt"));
        }
    }

    @Test
    public void shouldThrowWhenErrorBlockingRetry() {
        try {
            handler.call(Tuple.create(MAX_ATTEMPTS - 2, (Throwable) new OutOfMemoryError())).toBlocking().last();
            fail("expected to throw");
        } catch (CannotRetryException e) {
            assertTrue(e.getMessage(), e.getMessage().contains("#" + (MAX_ATTEMPTS - 2)));
            assertNotNull(e.getCause());
            assertTrue(e.getCause() instanceof OutOfMemoryError);
        }
    }

    @Test
    public void shouldDelayLinearlyWhenErrorCanBeRetried() {
        TestScheduler testScheduler = new TestScheduler();
        RetryWithDelayHandler timeHandler = new RetryWithDelayHandler(
                MAX_ATTEMPTS,
                Delay.linear(TimeUnit.SECONDS),
                new Func1<Throwable, Boolean>() {
                    @Override
                    public Boolean call(Throwable throwable) {
                        return throwable instanceof OutOfMemoryError;
                    }
                },
                testScheduler);

        final AtomicLong atomicLong = new AtomicLong(-1L);
        timeHandler.call(Tuple.create(MAX_ATTEMPTS - 2, (Throwable) new IllegalStateException()))
            .subscribe(new Action1<Object>() {
                @Override
                public void call(Object o) {
                    if (o instanceof Long) {
                        atomicLong.compareAndSet(-1L, (Long) o);
                    }
                }
            });

        testScheduler.advanceTimeBy(7, TimeUnit.SECONDS);
        assertEquals(-1L, atomicLong.longValue());

        testScheduler.advanceTimeBy(8, TimeUnit.SECONDS);
        assertEquals(0L, atomicLong.longValue()); //0L is the value emitted by Observable.timer
    }
}

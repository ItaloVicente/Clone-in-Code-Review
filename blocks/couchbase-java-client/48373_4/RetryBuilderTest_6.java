
package com.couchbase.client.java.util.retry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import com.couchbase.client.core.time.Delay;
import com.couchbase.client.java.error.CannotRetryException;
import org.junit.Test;

public class RetryBuilderTest {

    @Test
    public void testRetryOnce() throws Exception {
        RetryWhenFunction result = RetryBuilder.retryOnce().build();

        assertEquals(1, result.handler.maxAttempts);
        assertSame(Retry.DEFAULT_DELAY, result.handler.retryDelay);
        assertNull(result.handler.stoppingErrorFilter);
    }

    @Test
    public void testRetryMax() throws Exception {
        RetryWhenFunction result = RetryBuilder.retryMax(10).build();

        assertEquals(10, result.handler.maxAttempts);
        assertSame(Retry.DEFAULT_DELAY, result.handler.retryDelay);
        assertNull(result.handler.stoppingErrorFilter);
    }

    @Test
    public void testEmptyErrorsListMakesNullStoppingErrorFilter() {
        RetryWhenFunction neverSet = RetryBuilder.retryOnce().build();
        RetryWhenFunction emptyInclusion = RetryBuilder.retryOnce().onlyWhen().build();
        RetryWhenFunction emptyExclusion = RetryBuilder.retryOnce().onlyWhenNot().build();

        assertEquals(null, neverSet.handler.stoppingErrorFilter);
        assertEquals(null, emptyInclusion.handler.stoppingErrorFilter);
        assertEquals(null, emptyExclusion.handler.stoppingErrorFilter);
    }

    @Test
    public void testOnlyWhenNot() throws Exception {
        RetryWhenFunction exclusive = RetryBuilder.retryOnce().onlyWhenNot(CannotRetryException.class).build();

        assertTrue(exclusive.handler.stoppingErrorFilter instanceof RetryBuilder.ShouldStopOnError);
        assertTrue(exclusive.handler.stoppingErrorFilter.call(new CannotRetryException("")));
        assertFalse(exclusive.handler.stoppingErrorFilter.call(new IllegalStateException()));
    }

    @Test
    public void testOnlyWhen() throws Exception {
        RetryWhenFunction inclusive = RetryBuilder.retryOnce().onlyWhen(CannotRetryException.class).build();

        assertTrue(inclusive.handler.stoppingErrorFilter instanceof RetryBuilder.ShouldStopOnError);
        assertFalse(inclusive.handler.stoppingErrorFilter.call(new CannotRetryException("")));
        assertTrue(inclusive.handler.stoppingErrorFilter.call(new IllegalStateException()));
    }

    @Test
    public void testWithDelay() throws Exception {
        Delay linear = Delay.linear(TimeUnit.MINUTES, 8, 2, 2);
        RetryWhenFunction result = RetryBuilder.retryOnce().withDelay(linear).build();

        assertNotSame(Retry.DEFAULT_DELAY, result.handler.retryDelay);
        assertEquals(linear, result.handler.retryDelay);
    }
}

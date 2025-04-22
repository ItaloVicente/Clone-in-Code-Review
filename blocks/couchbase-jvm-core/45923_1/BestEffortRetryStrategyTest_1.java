package com.couchbase.client.core.retry;

import com.couchbase.client.core.env.CoreEnvironment;
import com.couchbase.client.core.message.CouchbaseRequest;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BestEffortRetryStrategyTest {

    @Test
    public void shouldRetryWhileUnderMaxTime() {
        BestEffortRetryStrategy strategy = BestEffortRetryStrategy.INSTANCE;

        CouchbaseRequest request = mock(CouchbaseRequest.class);
        when(request.creationTime()).thenReturn(System.nanoTime());
        CoreEnvironment env = mock(CoreEnvironment.class);
        when(env.maxRequestLifetime()).thenReturn(10000L);

        assertTrue(strategy.shouldRetry(request, env));
    }

    @Test
    public void shouldCancelWhenOverMaxTime() {
        BestEffortRetryStrategy strategy = BestEffortRetryStrategy.INSTANCE;

        CouchbaseRequest request = mock(CouchbaseRequest.class);
        long backInTime = TimeUnit.SECONDS.toNanos(3);
        when(request.creationTime()).thenReturn(System.nanoTime() - backInTime);
        CoreEnvironment env = mock(CoreEnvironment.class);
        when(env.maxRequestLifetime()).thenReturn(1000L);

        assertFalse(strategy.shouldRetry(request, env));
    }
}

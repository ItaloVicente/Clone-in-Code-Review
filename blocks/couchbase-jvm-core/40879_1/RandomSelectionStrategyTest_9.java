package com.couchbase.client.core.service.strategies;

import com.couchbase.client.core.endpoint.Endpoint;
import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.state.LifecycleState;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RandomSelectionStrategyTest {

    @Test
    public void shouldSelectEndpoint() {
        SelectionStrategy strategy = new RandomSelectionStrategy();

        Endpoint endpoint1 = mock(Endpoint.class);
        when(endpoint1.isState(LifecycleState.CONNECTED)).thenReturn(true);
        Endpoint endpoint2 = mock(Endpoint.class);
        when(endpoint2.isState(LifecycleState.CONNECTED)).thenReturn(false);
        Endpoint endpoint3 = mock(Endpoint.class);
        when(endpoint3.isState(LifecycleState.CONNECTED)).thenReturn(true);
        Endpoint[] endpoints = new Endpoint[] {endpoint1, endpoint2, endpoint3};

        for (int i = 0; i < 1000; i++) {
            Endpoint selected = strategy.select(mock(CouchbaseRequest.class), endpoints);
            assertNotNull(selected);
            assertFalse(selected.equals(endpoint2));
            assertTrue(selected.equals(endpoint1) || selected.equals(endpoint3));
        }
    }

    @Test
    public void shouldReturnIfEmptyArrayPassedIn() {
        SelectionStrategy strategy = new RandomSelectionStrategy();

        Endpoint selected = strategy.select(mock(CouchbaseRequest.class),  new Endpoint[] {});
        assertNull(selected);
    }
}

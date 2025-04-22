package com.couchbase.client.core.service.strategies;

import com.couchbase.client.core.endpoint.Endpoint;
import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.binary.GetBucketConfigRequest;
import com.couchbase.client.core.message.binary.GetRequest;
import com.couchbase.client.core.state.LifecycleState;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PartitionSelectionStrategyTest {

    @Test
    public void shouldSelectFirstConnectedForBucketConfigRequest() throws Exception {
        SelectionStrategy strategy = new PartitionSelectionStrategy();

        Endpoint endpoint1 = mock(Endpoint.class);
        when(endpoint1.isState(LifecycleState.CONNECTED)).thenReturn(false);
        Endpoint endpoint2 = mock(Endpoint.class);
        when(endpoint2.isState(LifecycleState.CONNECTED)).thenReturn(true);
        Endpoint endpoint3 = mock(Endpoint.class);
        when(endpoint3.isState(LifecycleState.CONNECTED)).thenReturn(true);
        Endpoint[] endpoints = new Endpoint[] {endpoint1, endpoint2, endpoint3};

        GetBucketConfigRequest request = mock(GetBucketConfigRequest.class);
        Endpoint selected = strategy.select(request, endpoints);

        assertNotNull(selected);
        assertTrue(selected.equals(endpoint2));
    }

    @Test
    public void shouldSelectFirstConnectedForRequestWithoutPartition() throws Exception {
        SelectionStrategy strategy = new PartitionSelectionStrategy();

        Endpoint endpoint1 = mock(Endpoint.class);
        when(endpoint1.isState(LifecycleState.CONNECTED)).thenReturn(false);
        Endpoint endpoint2 = mock(Endpoint.class);
        when(endpoint2.isState(LifecycleState.CONNECTED)).thenReturn(false);
        Endpoint endpoint3 = mock(Endpoint.class);
        when(endpoint3.isState(LifecycleState.CONNECTED)).thenReturn(true);
        Endpoint[] endpoints = new Endpoint[] {endpoint1, endpoint2, endpoint3};

        GetRequest request = mock(GetRequest.class);
        when(request.partition()).thenReturn((short) -1);
        Endpoint selected = strategy.select(request, endpoints);

        assertNotNull(selected);
        assertTrue(selected.equals(endpoint3));
    }

    @Test
    public void shouldSelectPinnedForBinaryWithKey() throws Exception {
        SelectionStrategy strategy = new PartitionSelectionStrategy();

        Endpoint endpoint1 = mock(Endpoint.class);
        when(endpoint1.isState(LifecycleState.CONNECTED)).thenReturn(true);
        Endpoint endpoint2 = mock(Endpoint.class);
        when(endpoint2.isState(LifecycleState.CONNECTED)).thenReturn(true);
        Endpoint endpoint3 = mock(Endpoint.class);
        when(endpoint3.isState(LifecycleState.CONNECTED)).thenReturn(true);
        Endpoint[] endpoints = new Endpoint[] {endpoint1, endpoint2, endpoint3};

        GetRequest request = mock(GetRequest.class);
        when(request.partition()).thenReturn((short) 12);
        Endpoint selected = strategy.select(request, endpoints);

        for (int i = 0; i < 1000; i++) {
            assertNotNull(selected);
            assertTrue(selected.equals(endpoint1));
        }
    }

    @Test
    public void shouldSelectNullIfPinedIsNotConnected() throws Exception {
        SelectionStrategy strategy = new PartitionSelectionStrategy();

        Endpoint endpoint1 = mock(Endpoint.class);
        when(endpoint1.isState(LifecycleState.CONNECTED)).thenReturn(false);
        Endpoint endpoint2 = mock(Endpoint.class);
        when(endpoint2.isState(LifecycleState.CONNECTED)).thenReturn(true);
        Endpoint endpoint3 = mock(Endpoint.class);
        when(endpoint3.isState(LifecycleState.CONNECTED)).thenReturn(true);
        Endpoint[] endpoints = new Endpoint[] {endpoint1, endpoint2, endpoint3};

        GetRequest request = mock(GetRequest.class);
        when(request.partition()).thenReturn((short) 12);
        Endpoint selected = strategy.select(request, endpoints);

        for (int i = 0; i < 1000; i++) {
            assertNull(selected);
        }
    }

    @Test
    public void shouldReturnIfEmptyArrayPassedIn() {
        SelectionStrategy strategy = new PartitionSelectionStrategy();

        Endpoint selected = strategy.select(mock(CouchbaseRequest.class),  new Endpoint[] {});
        assertNull(selected);
    }
}

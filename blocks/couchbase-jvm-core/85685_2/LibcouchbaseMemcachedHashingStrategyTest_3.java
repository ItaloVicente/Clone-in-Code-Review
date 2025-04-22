
package com.couchbase.client.core.node;

import com.couchbase.client.core.config.NodeInfo;
import com.couchbase.client.core.service.ServiceType;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LibcouchbaseMemcachedHashingStrategyTest {

    private static final MemcachedHashingStrategy STRATEGY
        = LibcouchbaseMemcachedHashingStrategy.INSTANCE;

    @Test
    public void shouldHashCorrectly() throws Exception {
        Map<ServiceType, Integer> services = new HashMap<ServiceType, Integer>();
        services.put(ServiceType.BINARY, 11210);

        NodeInfo nodeInfo = mock(NodeInfo.class);
        when(nodeInfo.rawHostname()).thenReturn("hostname");
        when(nodeInfo.services()).thenReturn(services);

        assertEquals("hostname:11210-0", STRATEGY.hash(nodeInfo, 0));

        nodeInfo = mock(NodeInfo.class);
        when(nodeInfo.rawHostname()).thenReturn("1.2.3.4");
        when(nodeInfo.services()).thenReturn(services);

        assertEquals("1.2.3.4:11210-99", STRATEGY.hash(nodeInfo, 99));
    }

}

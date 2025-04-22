package com.couchbase.client.core.node;

import com.couchbase.client.core.config.NodeInfo;
import com.couchbase.client.core.service.ServiceType;
import org.junit.Test;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LegacyMemcachedNodeHashingStrategyTest {

    @Test
    public void shouldHashNode() throws Exception {
        MemcachedNodeHashingStrategy strategy = new LegacyMemcachedNodeHashingStrategy();

        NodeInfo infoMock = mock(NodeInfo.class);
        when(infoMock.hostname()).thenReturn(InetAddress.getByName("localhost"));
        Map<ServiceType, Integer> serviceMap = new HashMap<ServiceType, Integer>();
        serviceMap.put(ServiceType.BINARY, 11210);
        when(infoMock.services()).thenReturn(serviceMap);
        assertEquals("127.0.0.1:11210-0", strategy.nodeHash(infoMock,0));
    }


}

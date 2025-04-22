package com.couchbase.client.core.node;

import com.couchbase.client.core.config.NodeInfo;
import org.junit.Test;

import java.net.InetAddress;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultMemcachedHashingStrategyTest {

    @Test
    public void shouldHashNode() throws Exception {
        MemcachedHashingStrategy strategy = DefaultMemcachedHashingStrategy.INSTANCE;

        NodeInfo infoMock = mock(NodeInfo.class);
        when(infoMock.hostname()).thenReturn(InetAddress.getByName("localhost"));
        assertEquals("localhost-0", strategy.hash(infoMock,0));
    }

}

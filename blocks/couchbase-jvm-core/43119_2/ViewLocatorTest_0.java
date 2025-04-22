package com.couchbase.client.core.node.locate;

import com.couchbase.client.core.config.ClusterConfig;
import com.couchbase.client.core.message.query.GenericQueryRequest;
import com.couchbase.client.core.message.query.QueryRequest;
import com.couchbase.client.core.node.Node;
import org.junit.Test;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QueryLocatorTest {

    @Test
    public void shouldSelectNextNode() throws Exception {
        Locator locator = new QueryLocator();

        QueryRequest request = mock(GenericQueryRequest.class);
        ClusterConfig configMock = mock(ClusterConfig.class);
        Set<Node> nodes = new HashSet<Node>();
        Node node1Mock = mock(Node.class);
        when(node1Mock.hostname()).thenReturn(InetAddress.getByName("192.168.56.101"));
        Node node2Mock = mock(Node.class);
        when(node2Mock.hostname()).thenReturn(InetAddress.getByName("192.168.56.102"));
        nodes.addAll(Arrays.asList(node1Mock, node2Mock));

        Node[] located = locator.locate(request, nodes, configMock);
        assertEquals(1, located.length);
        InetAddress foundFirst = located[0].hostname();

        located = locator.locate(request, nodes, configMock);
        assertEquals(1, located.length);
        InetAddress foundSecond = located[0].hostname();

        located = locator.locate(request, nodes, configMock);
        assertEquals(1, located.length);
        InetAddress foundLast = located[0].hostname();

        assertEquals(foundFirst, foundLast);
        assertNotEquals(foundFirst, foundSecond);
    }

}

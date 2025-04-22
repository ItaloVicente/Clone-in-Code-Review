package com.couchbase.client.core.message.cluster;

import org.junit.Test;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SeedNodesRequestTest {

    @Test
    public void shouldConstructWithDefaultHostname() throws Exception {
        SeedNodesRequest request = new SeedNodesRequest();
        assertEquals(1, request.nodes().size());
        assertTrue(request.nodes().contains(InetAddress.getByName("localhost")));
    }

    @Test
    public void shouldConstructWithCustomHostname() throws Exception {
        SeedNodesRequest request = new SeedNodesRequest("127.0.0.1");
        assertEquals(1, request.nodes().size());
        assertTrue(request.nodes().contains(InetAddress.getByName("127.0.0.1")));
    }

    @Test
    public void shouldDeduplicateHosts() {
        SeedNodesRequest request = new SeedNodesRequest("127.0.0.1", "localhost");
        assertEquals(1, request.nodes().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailOnNullHostname() {
        List<String> nodes = null;
        new SeedNodesRequest(nodes);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailOnEmptyHostname() {
        new SeedNodesRequest(new ArrayList<String>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailOnEmptyHostInVarargs() {
        new SeedNodesRequest("127.0.0.1", "");
    }

}

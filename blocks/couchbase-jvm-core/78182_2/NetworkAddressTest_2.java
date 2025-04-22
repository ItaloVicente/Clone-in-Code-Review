package com.couchbase.client.core.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class NetworkAddressTest {

    @Test
    public void shouldCreateForLocalhost() {
        NetworkAddress na = NetworkAddress.localhost();
        assertEquals("localhost", na.hostname());
        assertEquals("127.0.0.1", na.address());
        assertEquals("NetworkAddress{localhost/127.0.0.1, " +
            "fromHostname=false, reverseDns=true}", na.toString());
        assertEquals("localhost", na.nameOrAddress());
        assertEquals("127.0.0.1/localhost", na.nameAndAddress());
    }

    @Test
    public void shouldCreateFromHostname() {
        NetworkAddress na = NetworkAddress.create("localhost");
        assertEquals("localhost", na.hostname());
        assertEquals("127.0.0.1", na.address());
        assertEquals("NetworkAddress{localhost/127.0.0.1, " +
            "fromHostname=true, reverseDns=true}", na.toString());
        assertEquals("localhost", na.nameOrAddress());
        assertEquals("127.0.0.1/localhost", na.nameAndAddress());
    }

    @Test
    public void shouldCreateFromAddress() {
        NetworkAddress na = NetworkAddress.create("127.0.0.1");
        assertEquals("localhost", na.hostname());
        assertEquals("127.0.0.1", na.address());
        assertEquals("NetworkAddress{localhost/127.0.0.1, " +
            "fromHostname=false, reverseDns=true}", na.toString());
        assertEquals("localhost", na.nameOrAddress());
        assertEquals("127.0.0.1/localhost", na.nameAndAddress());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldAvoidReverseDnsLookup() {
        NetworkAddress na = new NetworkAddress("127.0.0.1", false);
        assertEquals("127.0.0.1", na.address());
        assertEquals("NetworkAddress{/127.0.0.1, " +
                "fromHostname=false, reverseDns=false}", na.toString());
        assertEquals("127.0.0.1", na.nameOrAddress());
        assertEquals("127.0.0.1", na.nameAndAddress());
        na.hostname(); // this will fail :-)
    }

    @Test
    public void shouldWorkIfHostnameAndDnsDisabled() {
        NetworkAddress na = new NetworkAddress("localhost", false);
        assertEquals("127.0.0.1", na.address());
        assertEquals("NetworkAddress{localhost/127.0.0.1, " +
                "fromHostname=true, reverseDns=false}", na.toString());
        assertEquals("localhost", na.hostname());
        assertEquals("localhost", na.nameOrAddress());
        assertEquals("127.0.0.1/localhost", na.nameAndAddress());
    }
}

package com.couchbase.client.java.util;

import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NodeLocatorHelperTest extends ClusterDependentTest {

    private NodeLocatorHelper helper;

    @Before
    public void setup() {
        helper = NodeLocatorHelper.create(bucket());
    }

    @Test
    public void shouldListAllNodes() {
        List<InetAddress> expected = bucketManager().info().nodeList();

        assertFalse(helper.nodes().isEmpty());
        assertEquals(expected, helper.nodes());
    }

    @Test
    public void shouldLocateActive() {
        InetAddress node = helper.activeNodeForId("foobar");
        assertTrue(helper.nodes().contains(node));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAcceptHigherReplicaNum() {
        helper.replicaNodeForId("foo", 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAcceptLowerReplicaNum() {
        helper.replicaNodeForId("foo", 0);
    }

}

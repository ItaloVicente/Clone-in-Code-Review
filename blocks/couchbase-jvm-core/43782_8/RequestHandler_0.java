package com.couchbase.client.core.cluster;

import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.dcp.DCPRequest;
import com.couchbase.client.core.message.dcp.MutationRequest;
import com.couchbase.client.core.message.dcp.OpenConnectionRequest;
import com.couchbase.client.core.message.dcp.OpenConnectionResponse;
import com.couchbase.client.core.message.dcp.RemoveRequest;
import com.couchbase.client.core.message.dcp.SnapshotMarkerRequest;
import com.couchbase.client.core.message.dcp.StreamRequestRequest;
import com.couchbase.client.core.message.dcp.StreamRequestResponse;
import com.couchbase.client.core.message.kv.UpsertRequest;
import com.couchbase.client.core.message.kv.UpsertResponse;
import com.couchbase.client.core.util.ClusterDependentTest;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DCPMessageTest extends ClusterDependentTest {

    @Test
    public void shouldOpenConnection() throws Exception {
        OpenConnectionResponse single = cluster()
                .<OpenConnectionResponse>send(new OpenConnectionRequest("hello", bucket()))
                .toBlocking()
                .single();
        assertEquals(ResponseStatus.SUCCESS, single.status());
    }

    @Test
    public void shouldRequestStream() throws Exception {
        OpenConnectionResponse open = cluster()
                .<OpenConnectionResponse>send(new OpenConnectionRequest("hello", bucket()))
                .toBlocking()
                .single();
        assertEquals(ResponseStatus.SUCCESS, open.status());
        StreamRequestResponse addStream = cluster()
                .<StreamRequestResponse>send(new StreamRequestRequest((short) 115, bucket()))
                .toBlocking()
                .single();
        assertEquals(ResponseStatus.SUCCESS, addStream.status());

        UpsertResponse foo = cluster()
                .<UpsertResponse>send(new UpsertRequest("foo", Unpooled.copiedBuffer("bar", CharsetUtil.UTF_8), 1, 0, bucket()))
                .toBlocking()
                .single();
        assertEquals(ResponseStatus.SUCCESS, foo.status());

        Thread.sleep(1100);

        List<DCPRequest> items = addStream.stream().take(4).toList().toBlocking().single();
        assertEquals(4, items.size());
        assertTrue(items.get(0) instanceof SnapshotMarkerRequest);
        MutationRequest mutation = (MutationRequest) items.get(1);
        assertEquals("foo", mutation.key());
        assertTrue(items.get(2) instanceof SnapshotMarkerRequest);
        RemoveRequest remove = (RemoveRequest) items.get(3);
        assertEquals("foo", remove.key());
    }
}
